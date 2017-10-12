package com.openthinks.libs.utilities.lookup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClassName: LookupPools <br/>
 * Function: utilities of {@link LookupPool}. <br/>
 * date: Sep 8, 2017 3:39:12 PM <br/>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @version
 * @since JDK 1.8
 */
public final class LookupPools {

	/**
	 * 
	 * gloabl: return common {@link LookupPool}, it is global {@link LookupPool} and
	 * will be singleton . <br/>
	 * 
	 * @return instance of {@link LookupPool}
	 */
	public static final LookupPool global() {
		return GloablLookupHolder.GLOBAL;
	}

	private static final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 
	 * get:return or create new named instance of {@link LookupPool}. <br/>
	 * 
	 * @param lookupPoolName
	 *            String name of {@link LookupPool}
	 * @return instance of {@link LookupPool}
	 */
	public static LookupPool get(String lookupPoolName) {
		lock.readLock().lock();
		try {
			if (cachedLookups.containsKey(lookupPoolName)) {
				return cachedLookups.get(lookupPoolName);
			}
		} finally {
			lock.readLock().unlock();
		}
		lock.writeLock().lock();
		try {
			cachedLookups.put(lookupPoolName, new NamedLookupPool(lookupPoolName));
			return cachedLookups.get(lookupPoolName);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * cleanUp:only clean up cached {@link LookupPool}s, not include instance which
	 * comes from {@link #global()}. <br/>
	 */
	public static void cleanUp() {
		lock.writeLock().lock();
		try {
			cachedLookups.values().forEach((lookup) -> {
				lookup.cleanUp();
			});
			cachedLookups.clear();
		} finally {
			lock.writeLock().unlock();
		}
	}

	private static class GloablLookupHolder {
		final static LookupPool GLOBAL = new LookupPool() {
			@Override
			public String name() {
				return "Global-LookupPool";
			}
		};
	}

	private LookupPools() {
	}

	private static final Map<String, LookupPool> cachedLookups = new ConcurrentHashMap<>();

	static class NamedLookupPool extends LookupPool {
		private final String name;

		NamedLookupPool(final String name) {
			this.name = name;
			cachedLookups.put(name, this);
		}

		@Override
		public String name() {
			return this.name;
		}

	}
}
