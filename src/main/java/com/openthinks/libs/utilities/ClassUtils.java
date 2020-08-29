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
	 * 
	 * @param beanClass
	 *            bean class
	 * @param propertyName
	 *            property name
	 * @return write {@link Method}
	 * @throws IntrospectionException
	 *             if an exception occurs during introspection
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
	 * 
	 * @param bean
	 *            Object bean instance
	 * @param propertyName
	 *            property name
	 * @param propertyValue
	 *            property value
	 * @throws IntrospectionException
	 *             - if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             - if this Method object is enforcing Java language access control
	 *             and the underlying method is inaccessible.
	 * @throws IllegalArgumentException
	 *             - if the method is an instance method and the specified object
	 *             argument is not an instance of the class or interface declaring
	 *             the underlying method (or of a subclass or implementor thereof);
	 *             if the number of actual and formal parameters differ; if an
	 *             unwrapping conversion for primitive arguments fails; or if, after
	 *             possible unwrapping, a parameter value cannot be converted to the
	 *             corresponding formal parameter type by a method invocation
	 *             conversion
	 * @throws InvocationTargetException - if the underlying method throws an exception
	 */
	public static void invokeWriteMethod(Object bean, String propertyName, Object propertyValue)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getWriteMethod(bean.getClass(), propertyName);
		method.invoke(bean, propertyValue);
	}

	public static PropertyDescriptor[] getAllPropertyDescriptor(Class<?> beanClass) throws IntrospectionException {

		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);

		return beanInfo.getPropertyDescriptors();
	}

	///////////////////////////////////////////////////////////////
}
