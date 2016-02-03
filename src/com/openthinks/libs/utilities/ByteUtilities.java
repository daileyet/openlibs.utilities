package com.openthinks.libs.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ByteUtilities {

	/**
	 * convert a object to byte array, use serialization
	 * @param obj Object
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] toByteArray(final Object obj) throws IOException {
		byte[] byteArray = null;
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baout);
		oout.writeObject(obj);
		oout.flush();
		byteArray = baout.toByteArray();
		baout.close();
		oout.close();
		return byteArray;
	}

	/**
	 * get object from byte array, use deserialization
	 * @param byteArray byte[]
	 * @return Object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object fromByteArray(final byte[] byteArray) throws IOException, ClassNotFoundException {
		if (byteArray == null || byteArray.length == 0)
			return null;
		ByteArrayInputStream bain = new ByteArrayInputStream(byteArray);
		ObjectInputStream oin = new ObjectInputStream(bain);
		Object obj = oin.readObject();
		bain.close();
		oin.close();
		return obj;
	}

	/**
	 * cache for load class
	 */
	private static Map<Long, Class<?>> loadedClassMap = new HashMap<Long, Class<?>>();

	/**
	 * load external class file
	 * 
	 * @param classFile
	 *            File
	 * @return Class<?> Type of this file represent
	 */
	public static Class<?> findClass(File classFile) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class,
					byte[].class, int.class, int.class });
			method.setAccessible(true);
			byte[] totalData = toByteArray(classFile);
			Long key = getKey(totalData);
			Class<?> claz = loadedClassMap.get(key);
			if (claz == null) {
				claz = (Class<?>) method.invoke(classLoader, new Object[] { null, totalData, 0, totalData.length });
				loadedClassMap.put(key, claz);
			}
			return claz;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static Long getKey(byte[] bytes) {
		long sum = 0;
		for (int i = 0; i < bytes.length; i++) {
			sum = 31 * i + bytes[i];
		}
		return sum;
	}

	/**
	 * get byte arrays from the local file
	 * @param classFile File
	 * @return byte[]
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] toByteArray(File classFile) throws FileNotFoundException, IOException {
		byte[] totalData = new byte[1024];
		int total = 0;
		int current = 0;
		byte[] buff = new byte[1024];
		InputStream in = null;
		try {
			in = new FileInputStream(classFile);
			do {
				current = in.read(buff);
				if (current == -1)
					break;
				byte[] temp = new byte[total + current];
				if (total >= totalData.length) {
					System.arraycopy(totalData, 0, temp, 0, total);
				}
				System.arraycopy(buff, 0, temp, total, current);
				totalData = temp;
				total += current;
			} while (true);
		} finally {
			if (in != null)
				in.close();
		}
		return totalData;
	}
}
