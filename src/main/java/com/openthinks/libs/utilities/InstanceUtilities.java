/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: InstanceUtilities.java 
* @Package openthinks.libs.utilities 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 3, 2015
* @version V1.0   
*/
package com.openthinks.libs.utilities;

import java.lang.reflect.Constructor;

/**
 * Helper for create a instance of the given type and parameters
 * @author dailey.yet@outlook.com
 *
 */
public class InstanceUtilities {

	@SuppressWarnings("unchecked")
	public static <T, E extends T> T create(final Class<T> searchType, InstanceWrapper<E> instancewrapper,
			Object... args) {
		T object = null;
		if (searchType != null) {
			if (object == null) {
				if (instancewrapper == null)
					instancewrapper = (InstanceWrapper<E>) InstanceWrapper.build(searchType);
				Constructor<E>[] constructors = (Constructor<E>[]) instancewrapper.instanceType
						.getDeclaredConstructors();
				for (Constructor<E> c : constructors) {
					try {
						Class<?>[] paramTypes = c.getParameterTypes();
						if (instancewrapper.isMember) {
							if (paramTypes.length == 1) {
								if (args == null)
									args = new Object[0];
								Object[] tmp = new Object[args.length + 1];
								tmp[0] = instancewrapper.owner;
								System.arraycopy(args, 0, tmp, 1, args.length);
								args = tmp;
							}
						}
						Checker.require(paramTypes).sameLengthWith(args);
						Checker.require(paramTypes).sameTypeWith(args);
						c.setAccessible(true);
						object = c.newInstance(args);
						break;
					} catch (Exception e) {
						continue;
					}
				}
			}
		}
		return object;
	}

	public static final class InstanceWrapper<E> {
		Class<E> instanceType;
		boolean isMember;
		Object owner;

		/**
		 * 
		 * @param instanceType
		 *            Class<E> the class type of instancing object
		 * @param owner
		 *            Object the instance object class type define in owner, it
		 *            means the instance class is a member class.
		 */
		public InstanceWrapper(Class<E> instanceType, Object owner) {
			super();
			this.instanceType = instanceType;
			if (this.instanceType != null)
				this.isMember = this.instanceType.isMemberClass();
			if (this.isMember) {
				//TODO validate parameter owner could not be empty
			}
			this.owner = owner;
		}

		public static <T> InstanceWrapper<T> build(Class<T> instanceType, Object... objects) {
			Object owner = null;
			if (objects != null && objects.length > 0)
				owner = objects[0];
			return new InstanceWrapper<T>(instanceType, owner);
		}

	}

}
