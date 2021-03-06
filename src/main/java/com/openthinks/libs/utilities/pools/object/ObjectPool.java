package com.openthinks.libs.utilities.pools.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The object pool, it will cache all the shared objects which fetch from this pool
 * @author minjdai
 * 
 */
public final class ObjectPool {
	// store the shared objects
	// private final ObjectList objects = new ObjectList();
	private final FirstLastObjectNameLookUp pool = new FirstLastObjectNameLookUp();
	// map to shared objects by alias
	private final Map<String, Ends> aliasMap = new ConcurrentHashMap<>();
	// map to shared objects by class type
	private final Map<Class<?>, Ends> typeMap = new ConcurrentHashMap<>();

	public ObjectPool() {
	}

	public void cleanUp() {
		pool.clear();
		aliasMap.clear();
		typeMap.clear();
	}

	// used for test
	FirstLastObjectNameLookUp pool() {
		return pool;
	}

	/**
	 * register the special object by the Class type
	 * 
	 * @param type
	 *            Class
	 * @param object
	 *            Object
	 */
	public void put(Class<?> type, Object object) {
		if (type == null)
			return;
		Ends ends = typeMap.get(type);
		if (ends != null && ends.isAssign()) {
			pool.replace(ends.getIndex(), object);
		} else {
			ends = pool.put(object);
			if (ends.isAssign())
				typeMap.put(type, ends);
		}
	}

	/**
	 * get the shared object by Class type key
	 * 
	 * @param <T>
	 *            object
	 * @param type
	 *            Class
	 * @return T object
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type) {
		T retVal = null;
		if (type != null) {
			Ends ends = typeMap.get(type);
			retVal = (T) pool.get(ends);
		}
		return retVal;
	}

	/**
	 * register the special object by the alias name
	 * 
	 * @param alias
	 *            String
	 * @param object
	 *            Object
	 */
	public void put(String alias, Object object) {
		if (alias == null)
			return;
		Ends ends = aliasMap.get(alias);
		if (ends != null && ends.isAssign()) {
			pool.replace(ends.getIndex(), object);
		} else {
			ends = pool.put(object);
			if (ends.isAssign())
				aliasMap.put(alias, ends);
		}
	}

	/**
	 * get the shared object by alias name key
	 * 
	 * @param alias
	 *            String
	 * @param <T>
	 *            Class
	 * @return T shared object with the given alias name
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String alias) {
		T retVal = null;
		if (alias != null) {
			Ends ends = aliasMap.get(alias);
			retVal = (T) pool.get(ends);
		}
		return retVal;
	}

	/**
	 * make an alias for the shared object, if this object is not shared in current
	 * pool, this operation will return false<BR>
	 * 
	 * <pre>
	 * <code>
	 * Object aSharedObj = get(SharedObjectClassType.class);
	 * boolean isSuccess = asAlias(aSharedObj,"aliasName");
	 * </code>
	 * </pre>
	 * 
	 * @param sharedObject
	 *            Object shared object in current pool
	 * @param alias
	 *            String alias name for this shared object
	 * @return true or false if operate success or not
	 */
	public boolean asAlias(Object sharedObject, String alias) {
		if (alias == null)
			return false;
		Ends ends = pool.findEnds(sharedObject);
		if (ends != null && !ends.equals(aliasMap.get(alias))) {
			aliasMap.put(alias, ends);
			return true;
		}
		return false;
	}

}

/**
 * two-level stored data structure
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 *
 */
class FirstLastObjectNameLookUp {

	/**
	 * <pre>
	 *  entry map : 
	 *  			KEY		: Class Name first character or class name length cast to character; it depend on {@link Ends#first()}
	 *  			VALUE	: Map(	KEY:Class Name last character; VALUE: ObjectList)
	 * </pre>
	 * 
	 */
	Map<Character, Map<Character, ObjectList>> entryMap = new ConcurrentHashMap<>();

	// combine entry map
	Map<String, ObjectList> combineMap = new ConcurrentHashMap<>();

	Ends findEnds(Object sharedObject) {
		Ends ends = Ends.build(sharedObject);
		ObjectList objList = getObjectList(ends);
		if (objList != null) {
			ends.setIndex(objList.find(sharedObject));
		}
		return ends;
	}

	private ObjectList getObjectList(Ends ends) {
		Objects.requireNonNull(ends);
		ObjectList objList = combineMap.get(ends.combine());
		if (objList == null) {
			Map<Character, ObjectList> firstEntry = entryMap.get(ends.first());
			if (firstEntry != null) {
				objList = firstEntry.get(ends.last());
			}
		}
		return objList;
	}

	Ends put(final Object object) {
		Ends ends = Ends.build(object);
		Map<Character, ObjectList> firstEntry = entryMap.get(ends.first());
		if (firstEntry == null) {
			firstEntry = new ConcurrentHashMap<>();
			entryMap.put(ends.first(), firstEntry);
		}
		ObjectList secondEntry = firstEntry.get(ends.last());
		if (secondEntry == null) {
			secondEntry = new ObjectList();
			firstEntry.put(ends.last(), secondEntry);
		}
		int index = secondEntry.add(object);
		ends.setIndex(index);
		if (ends.isAssign()) {
			combineMap.put(ends.combine(), secondEntry);
		}
		return ends;
	}

	void replace(Integer index, Object object) {
		Ends ends = Ends.build(object);
		ObjectList objList = getObjectList(ends);
		if (objList != null) {
			objList.replace(index, object);
		}

	}

	Object get(Ends ends) {
		Object retVal = null;
		if (ends == null || !ends.isAssign())
			return retVal;
		ObjectList objList = getObjectList(ends);
		if (objList != null)
			retVal = objList.get(ends.getIndex());
		return retVal;
	}

	void clear() {
		combineMap.clear();
		for (Map.Entry<Character, Map<Character, ObjectList>> entry : entryMap.entrySet()) {
			Map<Character, ObjectList> value = entry.getValue();
			if (value != null) {
				value.clear();
				value = null;
			}
		}
		entryMap.clear();
	}

	class ObjectList {
		private final List<Object> _objects = new ArrayList<>();
		private final Lock lock = new ReentrantLock();

		void replace(int index, Object newObject) {
			_objects.set(index, newObject);
		}

		int size() {
			return _objects.size();
		}

		int find(Object sharedObject) {
			int index = _objects.indexOf(sharedObject);
			return index;
		}

		int add(Object value) {
			int retVal = -1;
			lock.lock();
			try {
				boolean isSuccess = _objects.add(value);
				if (isSuccess)
					retVal = _objects.size() - 1;
			} finally {
				lock.unlock();
			}
			return retVal;
		}

		Object get(int index) {
			if (index > -1 && index < _objects.size())
				return _objects.get(index);
			return null;
		}
	}
}

/**
 * 
 * ClassName: EnhancedEnds <br/>
 * Function: the first character replace the class name length. <br/>
 * Reason: most of time all classes are in same root package, so they will have
 * the same start character. <br/>
 * date: Sep 11, 2017 10:47:04 AM <br/>
 */
class EnhancedEnds extends Ends {
	EnhancedEnds(final String className) {
		super(className);
	}

	@Override
	protected String calculateEnds(String className) {
		return (char) className.length() + className.substring(className.length() - 1, className.length());
	}
}

/**
 * First and Last(end to end) character of the object class name
 * 
 * @author minjdai
 * 
 */
class Ends {
	public static Ends build(Object object) {
		Objects.requireNonNull(object);
		return new EnhancedEnds(object.getClass().getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ends == null) ? 0 : ends.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ends other = (Ends) obj;
		if (ends == null) {
			if (other.ends != null)
				return false;
		} else if (!ends.equals(other.ends))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	protected final String ends;
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Ends(final String className) {
		ends = calculateEnds(className);
	}

	protected String calculateEnds(String className) {
		return className.substring(0, 1) + className.substring(className.length() - 1, className.length());
	}

	public Character first() {
		return ends.charAt(0);
	}

	public Character last() {
		return ends.charAt(1);
	}

	public String combine() {
		return ends;
	}

	public boolean isAssign() {
		return getIndex() != null && getIndex() >= 0;
	}
}

