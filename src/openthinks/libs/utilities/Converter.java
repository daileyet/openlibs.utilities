package openthinks.libs.utilities;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * convert help
 * 
 * @author dailey_dai
 * 
 */
public final class Converter {
	private final Object source;
	private static Map<Class<?>, ConvertHandler> handlerMap = new HashMap<Class<?>, ConvertHandler>();

	static {
		// initial ConvertHandler
	}

	public static void register(Class<?> sourceType, ConvertHandler handler) {
		handlerMap.put(sourceType, handler);
	}

	private Converter(Object source) {
		this.source = source;
	}

	public static Converter source(Object source) {
		return new Converter(source);
	}

	Object convertTo(Object source, Class<?> targetType) {
		Object _source = source;
		ConvertHandler handler = handlerMap.get(_source.getClass());
		if (handler != null) {
			handler.handSource(_source);
			return handler.handTarget(targetType);
		}
		String value = source.toString();
		if (targetType == int.class || targetType == Integer.class) {
			return Integer.valueOf(value).intValue();
		} else if (targetType == long.class || targetType == Long.class) {
			return Long.valueOf(value).longValue();
		} else if (targetType == float.class || targetType == Float.class) {
			return Float.valueOf(value).floatValue();
		} else if (targetType == double.class || targetType == Double.class) {
			return Double.valueOf(value).doubleValue();
		} else if (targetType == boolean.class || targetType == Boolean.class) {
			return Boolean.valueOf(value).booleanValue();
		} else if (targetType == byte.class || targetType == Byte.class) {
			return Byte.valueOf(value).byteValue();
		} else if (targetType == short.class || targetType == Short.class) {
			return Short.valueOf(value).shortValue();
		} else if (targetType == char.class || targetType == Character.class) {
			return value.charAt(0);
		} else if (targetType == String.class) {
			return String.valueOf(source);
		} else {
			throw new IllegalArgumentException(targetType
					+ " is not primitive type.");
		}
	}

	/**
	 * convert source to target type, <BR>
	 * if source is array, try to convert the first element of the array to
	 * target type.
	 * 
	 * @param <T>
	 * @param clazz
	 *            target type
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T> T convertToSingle(Class<T> clazz) {
		Object _source = source;
		ConvertHandler handler = handlerMap.get(_source.getClass());
		if (handler != null) {
			handler.handSource(_source);
			return handler.handTarget(clazz);
		}
		if (isArray()) {
			if (Array.getLength(source) <= 0)
				throw new IllegalArgumentException(
						"The origial converted array must not be empty.");
			_source = Array.get(source, 0);
		}
		return (T) convertTo(_source.toString(), clazz);
	}

	public <T> T convertToSingle(Class<T> clazz, T defaultValue) {

		T t = defaultValue;
		try {
			t = convertToSingle(clazz);
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * convert source array to target type array
	 * 
	 * @param <T>
	 * @param clazz
	 *            target array element type
	 * @return ConvertArray<T>
	 */
	public <T> ConvertArray<T> convertToArray(Class<T> clazz) {
		Object target = _convertToArray(clazz);
		return new Converter.ConvertArray<T>(target, clazz);
	}

	/**
	 * example: <br>
	 * source:int[] array<br>
	 * target:Integer[] array
	 * 
	 * @param clazz
	 * @return
	 */
	protected <T> Object _convertToArray(Class<T> clazz) {
		if (!isArray()) {
			throw new IllegalArgumentException(
					"The origial converted is not array.");
		}

		int length = Array.getLength(source);
		Object target = Array.newInstance(clazz, length);
		for (int i = 0; i < length; i++) {
			Object origianlElement = Array.get(source, i);
			Array.set(target, i, convertTo(origianlElement, clazz));
		}
		return target;
	}

	boolean isArray() {
		return source.getClass().isArray();
	}

	/**
	 * convert to pure array
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] convert2Array(Class<T> clazz) {
		int length = 0;
		if (source instanceof List) {
			length = ((List<?>) source).size();
			Object target = Array.newInstance(clazz, length);
			for (int i = 0; i < length; i++) {
				Object origianlElement = ((List<?>) source).get(i);
				Array.set(target, i, origianlElement);
			}
			return (T[]) target;
		} else if (isArray()) {
			return (T[]) _convertToArray(clazz);
		}
		return (T[]) Array.newInstance(clazz, length);
	}

	public static class ConvertArray<T> implements Iterable<T> {
		Object array;
		Class<T> elementType;

		public ConvertArray(Object array, Class<T> elementType) {
			this.array = array;
			this.elementType = elementType;
		}

		public int length() {
			return Array.getLength(array);
		}

		@SuppressWarnings("unchecked")
		public T get(int index) {
			Object element = Array.get(array, index);
			return (T) element;
		}

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				int point = 0;

				@Override
				public boolean hasNext() {
					return point < length();
				}

				@Override
				public T next() {
					T t = get(point);
					++point;
					return t;
				}

				@Override
				public void remove() {
				}
			};

		}
	}

	public interface ConvertHandler {

		public <T> T handTarget(Class<T> clazz);

		public void handSource(Object obj);
	}

}