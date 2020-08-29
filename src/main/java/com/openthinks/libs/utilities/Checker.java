/**
 * Licensed to the Apache Software Foundation (ASF) under one 
 * or more contributor license agreements. See the NOTICE file 
 * distributed with this work for additional information 
 * regarding copyright ownership. The ASF licenses this file 
 * to you under the Apache License, Version 2.0 (the 
 * "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License. 
 * 
 * @Title: Checker.java 
 * @Package utilities 
 * @Description: TODO
 * @author dailey dai 
 * @date 2012-2-26
 * @version V1.0 
 */
package com.openthinks.libs.utilities;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import com.openthinks.libs.utilities.exception.CheckerNoPassException;

/**
 * Validation of common check
 * @author dailey dai
 *
 */
public class Checker {

	public static <T> Requirer<T> require(T requireObject) {
		return new Requirer<T>(requireObject);
	}

	public static boolean isArray(Object checkObj) {
		try {
			Array.getLength(checkObj);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}

	}

	public static class Requirer<T> {
		private T requireObject = null;

		public Requirer(T requireObject) {
			super();
			this.requireObject = requireObject;
		}

		public void notNull(String... args) {

			if (requireObject == null) {
				throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(),
						CommonUtilities.toString4Array(args));
			}
		}

		public void notEmpty(String... args) {
			notNull(args);
			if ("".equals(requireObject)) {
				throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(),
						CommonUtilities.toString4Array(args));
			}
		}

		/**
		 * check the requirer is array type
		 */
		public void isArray() {
			if (!Checker.isArray(requireObject))
				throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(), "Not array type.");
		}

		public void equalTo(T comparedObj) {
			if (requireObject == comparedObj) {
				return;
			}
			if (requireObject != null && requireObject.equals(comparedObj)) {
				return;
			}
			if (Checker.isArray(requireObject) && Checker.isArray(comparedObj)) {
				if (Arrays.deepEquals((Object[]) requireObject, (Object[]) comparedObj)) {
					return;
				}
			}
			//TODO compare collection

			throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod());
		}

		/**
		 * check the call object extends or implements from the parameter class
		 * @param clzz parent class
		 */
		public void isExtendsFrom(Class<?> clzz) {
			if (clzz == null || requireObject.getClass() == null) {
				throw new CheckerNoPassException();
			} else if (!clzz.isAssignableFrom(requireObject.getClass())) {
				throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(), requireObject.getClass()
						+ ":" + requireObject + " need extends or implements " + clzz);
			}
		}

		/**
		 * when the call object is File type, check the file exist in local
		 */
		public void needExist() {
			if (requireObject instanceof File) {
				if (!((File) requireObject).exists()) {
					throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(), requireObject
							+ " does not exist.");
				}
			}
		}

		/**
		 * should in the region which between the given parameters
		 * @param min Integer
		 * @param max Integer
		 */
		public void inScope(int min, int max) {
			notNull();
			if (Integer.valueOf(requireObject.toString()) < min || Integer.valueOf(requireObject.toString()) > max) {
				throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod(), requireObject
						+ " not between " + min + " and " + max);
			}
		}

		/**
		 * check the required object as array type and has the same length with given parameters
		 * @param args Object[]
		 */
		public void sameLengthWith(Object... args) {
			isArray();
			int length = Array.getLength(this.requireObject);
			if (args != null && args.length == length) {
				return;
			}
			throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod());
		}

		/**
		 * check the same type between required and given parameters, consider the inheritance as same type too.
		 * @param args Object[]
		 */
		public void sameTypeWith(Object... args) {
			boolean isArray = Checker.isArray(requireObject);

			if (isArray) {
				if (args != null) {
					for (int index = 0, len = Array.getLength(requireObject); index < len; index++) {
						if (checkSameTypeForSingleton(Array.get(requireObject, index), args[index])) {
							continue; // PASS
						}
						throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod());
					}
					return;
				}
			} else {
				if (requireObject == null && args == null) {
					return;
				}
				if (requireObject != null && args != null && args.length == 1 && args[0] != null) {
					if (checkSameTypeForSingleton(this.requireObject, args[0]))
						return;
				}
			}
			throw new CheckerNoPassException(CommonUtilities.getCurrentInvokerMethod());
		}

		private boolean checkSameTypeForSingleton(Object reqiured, Object compared) {
			if (reqiured == compared) {
				return true;
			}
			if (reqiured != null) {
				try {
					Class<?> reqiuredClazz = (Class<?>) reqiured;
					if (compared == null)// special case
						return true;
					if (reqiuredClazz == compared) {
						return true;
					}
					if (reqiuredClazz == compared.getClass()) {
						return true;
					}
					if (reqiuredClazz.isAssignableFrom(compared.getClass())) {
						return true;
					}
					try {
						if (reqiuredClazz.isAssignableFrom((Class<?>) compared))
							return true;
					} catch (Exception e) {
					}

				} catch (Exception e) {
					if (compared != null && reqiured.getClass() == compared)
						return true;
					if (compared != null && reqiured.getClass() == compared.getClass())
						return true;
					if (compared != null && reqiured.getClass().isAssignableFrom(compared.getClass()))
						return true;
					try {
						if (compared != null && reqiured.getClass().isAssignableFrom((Class<?>) compared))
							return true;
					} catch (Exception e1) {
					}
				}
			}
			return false;
		}
	}

	public static void main(String[] args) {
		boolean result = Checker.require(Exception.class).checkSameTypeForSingleton(Exception.class, new Exception());
		System.out.println(result == true);
		result = Checker.require(Exception.class).checkSameTypeForSingleton(Exception.class, Exception.class);
		System.out.println(result == true);
		result = Checker.require(Exception.class).checkSameTypeForSingleton(Exception.class, null);
		System.out.println(result == true);
		result = Checker.require(Exception.class).checkSameTypeForSingleton(Exception.class,
				new CheckerNoPassException());
		System.out.println(result == true);
		result = Checker.require(Exception.class).checkSameTypeForSingleton(Exception.class,
				CheckerNoPassException.class);
		System.out.println(result == true);
		result = Checker.require(Exception.class).checkSameTypeForSingleton(new Exception(),
				CheckerNoPassException.class);
		System.out.println(result == true);

		Checker.require(new Class[] { Exception.class }).sameTypeWith(new Exception());
		Checker.require(new Class[] { Exception.class }).sameTypeWith(new Object[] { null });
		Checker.require(new Class[] { Exception.class }).sameTypeWith(Exception.class);
		Checker.require(new Class[] { Exception.class }).sameTypeWith(CheckerNoPassException.class);
		Checker.require(new Class[] { Exception.class }).sameTypeWith(CheckerNoPassException.class, null);
	}
}
