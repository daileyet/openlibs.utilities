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

import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * Helper for create a instance of the given type and parameters
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class InstanceUtilities {

	/**
	 * 
	 * create:instantiate a object by its super interface or class. 
	 * 
	 * <pre>
	 * <code>
	 * interface A{ public void test();}
	 * 
	 * class AImpl implements A{
	 * 	public void test(){
	 * 		System.out.println("Implementation!!!");
	 * 	};
	 * }
	 * 
	 * class Client{
	 * 
	 * public void static main(String[] args){
	 * 		InstanceWrapper&lt;AImpl&gt; wrapper = InstanceWrapper.build(AImpl.class);
	 * 		A aInstance = InstanceUtilities.create(A.class,wrapper);
	 * 
	 * 	}
	 * 
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param <T>
	 *            search interface or class type
	 * @param <E>
	 *            implementation or child for search interface or class type
	 * @param searchType
	 *            search interface or class
	 * @param instancewrapper
	 *            {@link InstanceWrapper}
	 * @param args
	 *            array of object to as constructor parameters
	 * @return instance of implementation of search interface or class type
	 */
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
							if (args == null)
								args = new Object[0];
							if (paramTypes.length == (1 + args.length)) {
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
						ProcessLogger.warn("Get exception when instance Class:" + searchType, e);
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
		 *            the class type of instancing object
		 * @param owner
		 *            the instance object class type define in owner, it means the
		 *            instance class is a member class, if not a member class, let is as
		 *            empty or null.
		 */
		InstanceWrapper(Class<E> instanceType, Object owner) {
			super();
			this.instanceType = instanceType;
			if (this.instanceType != null)
				this.isMember = this.instanceType.isMemberClass();
			if (this.isMember) {
				Checker.require(owner).notNull("Member class cannot be separate from its own class instance");
			}
			this.owner = owner;
		}

		/**
		 * 
		 * build: create a instance of {@link InstanceWrapper} <br>
		 * 
		 * @param <T>
		 *            class type
		 * @param instanceType
		 *            Class which need to be instantiated really
		 * @param objects
		 *            array of own object, if instanceType is not member class,this
		 *            parameter can be empty; otherwise, this parameter cannot be null,
		 * @return instance of {@link InstanceWrapper}
		 */
		public static <T> InstanceWrapper<T> build(Class<T> instanceType, Object... objects) {
			Object owner = null;
			if (objects != null && objects.length > 0)
				owner = objects[0];
			return new InstanceWrapper<>(instanceType, owner);
		}

	}

}
