/**
 * 
 */
package com.openthinks.libs.utilities;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author dailey.yet@outlook.com
 * 
 */
public final class ClassUtils {

	private final static WeakHashMap<Class<?>, Map<String, PropertyDescriptor>> caches = new WeakHashMap<>();

	/**
	 * get write method for property
	 * @param beanClass
	 * @param propertyName
	 * @return write {@link Method}
	 * @throws IntrospectionException
	 */
	public static Method getWriteMethod(Class<?> beanClass, String propertyName) throws IntrospectionException {
		Map<String, PropertyDescriptor> beanMap = caches.get(beanClass);
		PropertyDescriptor pd = null;
		if (beanMap == null || beanMap.get(propertyName) == null) {
			pd = new PropertyDescriptor(propertyName, beanClass);
			if (beanMap == null) {
				beanMap = new WeakHashMap<>();
				caches.put(beanClass, beanMap);
			}
			beanMap.put(propertyName, pd);
		} else {
			pd = beanMap.get(propertyName);
		}
		return pd.getWriteMethod();
	}

	/**
	 * invoke write method for property
	 * @param bean Object bean instance
	 * @param propertyName
	 * @param propertyValue
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void invokeWriteMethod(Object bean, String propertyName, Object propertyValue)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getWriteMethod(bean.getClass(), propertyName);
		method.invoke(bean, propertyValue);
	}
	
	public static PropertyDescriptor[] getAllPropertyDescriptor(Class<?> beanClass) throws IntrospectionException {
		
		BeanInfo beanInfo=Introspector.getBeanInfo(beanClass);
		
		return beanInfo.getPropertyDescriptors();
	}
	
	///////////////////////////////////////////////////////////////
}
