/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.libs.utilities.StringUtils;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONConverters {

	static final Map<Class<?>, JSONObjectConverter> cacheMap = new ConcurrentHashMap<>();
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	// configure properties
	static final String CONFIGURATION_FILE = "/converterConfig.properties";
	static final String CONFIG_VAL_ITEM_TOKEN = ",";
	static final String CONFIG_VAL_ITEM_MAP_TOKEN = ":";
	// condition output token
	static final String CONFIG_VAL_ITEM_CONDITION_TOKEN = "?";

	static {
		try {
			ConfigureLoader.load(CONFIGURATION_FILE);
		} catch (IOException e) {
			ProcessLogger.fatal(e);
		}
	}// end of register

	public static void reload(String classPathResourceName) {
		try {
			ConfigureLoader.load(classPathResourceName);
		} catch (IOException e) {
			ProcessLogger.fatal(e);
		}
	}

	public static boolean isSupport(final Object bizObj) {
		return bizObj != null && cacheMap.containsKey(bizObj.getClass());
	}

	public static JSONObject perparedAndGet(final Object bizObj) {
		if (bizObj == null)
			return null;
		JSONObjectConverter conveter = cacheMap.getOrDefault(bizObj.getClass(), DEFAULT_CONVETER);
		return conveter.convert(bizObj);
	}

	public static JSONObject perparedAndGet(final Object bizObj, JSONObjectConverter defaultConverter) {
		if (bizObj == null)
			return null;
		JSONObjectConverter conveter = cacheMap.getOrDefault(bizObj.getClass(), defaultConverter);
		return conveter.convert(bizObj);
	}

	public static void register(Class<?> bizObjClazz, JSONObjectConverter jsonObjectConvert) {
		cacheMap.put(bizObjClazz, jsonObjectConvert);
	}

	//////////////////////////////////////////////////////////////////////////////////
	// default converter
	public static final JSONObjectConverter DEFAULT = (obj) -> {
		Class<?> bizModelClazz = obj.getClass();
		JSONObject jsonObj = JSON.object();
		Field[] fields = bizModelClazz.getDeclaredFields();
		for (int i = 0, j = fields.length; i < j; i++) {
			String fieldName = fields[i].getName();
			try {
				PropertyDescriptor pd = ConfigureLoader.find(bizModelClazz, fieldName);
				Object propertyVal = pd.getReadMethod().invoke(obj);
				jsonObj.addProperty(fieldName, propertyVal);
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				ProcessLogger.warn("Faile to load property {0} value for {1}:", obj, e);
			}
		}
		return jsonObj;
	};

	public static final JSONObjectConverter DEFAULT_UNDER_LINE = (obj) -> {
		Class<?> bizModelClazz = obj.getClass();
		JSONObject jsonObj = JSON.object();
		Field[] fields = bizModelClazz.getDeclaredFields();
		for (int i = 0, j = fields.length; i < j; i++) {
			String fieldName = fields[i].getName();
			String jsonKey = Underline2Camel.camel2Underline(fieldName).toLowerCase();
			try {
				PropertyDescriptor pd = ConfigureLoader.find(bizModelClazz, fieldName);
				jsonObj.addProperty(jsonKey, pd.getReadMethod().invoke(obj));
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				ProcessLogger.warn("Faile to load property {0} value for {1}:", obj, e);
			}
		}
		return jsonObj;
	};

	volatile static JSONObjectConverter DEFAULT_CONVETER = DEFAULT;

	public synchronized static final void ativeDefaultConveterAsUnderline() {
		DEFAULT_CONVETER = DEFAULT_UNDER_LINE;
	}

	public synchronized static final void ativeDefaultConveterAsCamel() {
		DEFAULT_CONVETER = DEFAULT;
	}

	/**
	 * 
	 * 配置文件加载类，负责加载配置文件并初始化配置<BR>
	 * 
	 * 配置文件格式(converterConfig.properties):<BR>
	 * <BR>
	 * <code>com.iboitech.core.model.DepartmentInquiryVw=id,title,type:typeName</code>
	 * <BR>
	 * 配置项左边为需要转换的实体类的全名称；右边为转换具体规则，规则如下
	 * <li><json-key-name>[:model-property-name]
	 * <li>json键名称与实体类对应的属性名之间用冒号:隔开
	 * <li>多个项之间用逗号,隔开
	 * <li>若属性名不写，则默认和json键名称同名
	 * <li>json键名称前若有?则表示条件输出，当其值不为空时输出
	 * 
	 */
	static class ConfigureLoader {
		final Properties props;

		ConfigureLoader(Properties configProp) {
			this.props = configProp;
			this.config();
		}

		private void config() {
			props.keySet().forEach((key) -> {
				String bizModelClazz = String.valueOf(key);
				String cofigVal = props.getProperty(bizModelClazz);
				try {
					ConverterConfig cc = ConverterConfig.build(bizModelClazz);
					cc.config(cofigVal);
				} catch (ClassNotFoundException e) {
					ProcessLogger.warn("Faile to config JSONObjectConverter {0} for: {1}", bizModelClazz, e);
				}
			});
		}

		static ConfigureLoader load(String resourceName) throws IOException {
			final Properties configProp = new Properties();
			configProp.load(JSONConverters.class.getResourceAsStream(resourceName));
			return new ConfigureLoader(configProp);
		}

		final static Map<String, PropertyDescriptor> cachedPropDesc = new HashMap<>();

		static synchronized PropertyDescriptor find(Class<?> targetClazz, String propertyName)
				throws IntrospectionException {
			String key = targetClazz.getName() + "#" + propertyName;
			PropertyDescriptor pd = cachedPropDesc.get(key);
			if (pd == null) {
				pd = new PropertyDescriptor(propertyName, targetClazz);
				cachedPropDesc.put(key, pd);
			}
			return pd;
		}
	}

	/**
	 * 
	 * 配置文件的每一行，对应一个{@link JSONObjectConverter}实现的配置,该类负责实例化{@link JSONObjectConverter}并注册到缓存中
	 *
	 */
	static class ConverterConfig {
		final String modelClazz;
		final List<ConfigItem> configItemList = new ArrayList<>();

		ConverterConfig(String bizModelClazz) {
			this.modelClazz = bizModelClazz;
		}

		static ConverterConfig build(String bizModelClazz) {
			ConverterConfig cc = new ConverterConfig(bizModelClazz);
			return cc;
		}

		void config(String cofigVal) throws ClassNotFoundException {
			String[] configItemArray = cofigVal.split(CONFIG_VAL_ITEM_TOKEN);
			Arrays.asList(configItemArray).forEach((item) -> {
				ConfigItem ci = parse(item);
				configItemList.add(ci);

			});
			Class<?> bizModelType = Class.forName(modelClazz);
			register(bizModelType, (bizObj) -> {
				JSONObject jsonObj = JSON.object();
				configItemList.forEach((ci) -> {
					Object propertyVal = getPropertyVal(bizObj, ci.propertyName);
					if (ci.isCond) {
						if (propertyVal != null)
							jsonObj.addProperty(ci.jsonKey, propertyVal);
					} else
						jsonObj.addProperty(ci.jsonKey, propertyVal);
				});
				return jsonObj;
			});

		}

		private Object getPropertyVal(Object bizObj, String propertyName) {
			try {
				PropertyDescriptor pd = ConfigureLoader.find(bizObj.getClass(), propertyName);
				return pd.getReadMethod().invoke(bizObj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| IntrospectionException e) {
				ProcessLogger.warn("Faile to load property {0} value for {1}:", bizObj, e);
			}
			return null;
		}

		private ConfigItem parse(String item) {
			ConfigItem ci = new ConfigItem();
			String itemTemp = item == null ? item : item.trim();
			if (itemTemp.startsWith(CONFIG_VAL_ITEM_CONDITION_TOKEN)) {
				ci.isCond = true;
				itemTemp = itemTemp.substring(1);
			}
			ci.setJsonKey(itemTemp);
			ci.setPropertyName(itemTemp);
			if (itemTemp.contains(CONFIG_VAL_ITEM_MAP_TOKEN)) {
				String[] maps = itemTemp.split(CONFIG_VAL_ITEM_MAP_TOKEN);
				ci.setJsonKey(maps[0]);
				if (maps.length > 1)
					ci.setPropertyName(maps[1]);
				else
					ci.setPropertyName(ci.jsonKey);
			}
			return ci;
		}

		class ConfigItem {
			String jsonKey;
			String propertyName;
			boolean isCond = false;

			public void setJsonKey(String jsonKey) {
				this.jsonKey = StringUtils.trim(jsonKey);
			}

			public void setPropertyName(String propertyName) {
				this.propertyName = StringUtils.trim(propertyName);
			}

			public boolean isCond() {
				return isCond;
			}
		}
	}
}
