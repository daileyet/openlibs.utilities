package com.openthinks.libs.utilities.lookup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;
import com.openthinks.libs.utilities.exception.CheckerNoPassException;
import com.openthinks.libs.utilities.pools.object.ObjectPool;

/**
 * ClassName: LookupPool <br>
 * Function: It is used for store shared object instances and find other SPI<br>
 * Reason: follow the design pattern of fly weight to reduce instantiate new object. <br>
 * Notice: avoid store or find those object which include its own state and will be changed;<br>
 * Usage:<br>
 * 
 * <pre>
 * <code>
 * //get gloabl instance of LookupPool
 * LookupPool lookupPool = LookupPools.gloabl();
 * 
 * //get a named instance of LookupPool
 * lookupPool = LookupPools.get("pool-1");
 * 
 * // instance by class directly
 * LookupInterfaceImpl instanceImpl1 = lookupPool.lookup(LookupInterfaceImpl.class);
 * 
 * // instance by customized implementation class
 * LookupInterface instanceImpl2 = lookupPool.lookup(LookupInterface.class,InstanceWrapper.build(LookupInterfaceImpl.class));
 * 
 * // lookup shared object already existed
 * LookupInterface instanceImpl3 = lookupPool.lookup(LookupInterface.class);
 * assert instanceImpl2==instanceImpl3 ;
 * 
 * // register a shared object by name
 * lookupPool.register("beanName1",instanceImpl1);
 * // lookup shared object by name
 * LookupInterface instanceImpl4 = lookupPool.lookup("beanName1");
 * 
 * assert instanceImpl1==instanceImpl4 ;
 * 
 * // clear all shared objects and mappings
 * lookupPool.cleanUp();
 * 
 * </code>
 * </pre>
 * 
 * date: Sep 8, 2017 3:15:29 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class LookupPool {
  protected final ObjectPool objectPool;
  protected final ReadWriteLock lock;

  public abstract String name();

  protected LookupPool() {
    objectPool = new ObjectPool();
    lock = new ReentrantReadWriteLock();
  }

  /**
   * lookup a object by its type, if not find firstly, try to instance by instanceType and
   * constructor parameters args
   * 
   * @param <T> lookup object type
   * @param type Class lookup key type
   * @param args Object[] instance constructor parameters
   * @return T lookup object or null
   */
  public <T> T lookup(Class<T> type, Object... args) {
    return lookup(type, null, args);
  }

  /**
   * lookup a object by its type, if not find firstly, try to instance by instanceType and
   * constructor parameters args
   * 
   * @param <T> lookup object type
   * @param <E> lookup object type
   * @param searchType Class lookup key type
   * @param instancewrapper Class instance type when not lookup the key
   * @param args Object[] instance constructor parameters
   * @return T lookup object or null
   */
  public <T, E extends T> T lookup(final Class<T> searchType, InstanceWrapper<E> instancewrapper,
      Object... args) {
    T object = null;
    lock.readLock().lock();
    try {
      object = objectPool.get(searchType);
    } finally {
      lock.readLock().unlock();
    }
    if (object == null) {
      lock.writeLock().lock();
      try {
        object = InstanceUtilities.create(searchType, instancewrapper, args);
        register(searchType, object);
      } finally {
        lock.writeLock().unlock();
      }
    }
    return object;
  }

  /**
   * look up object by its bean name
   * 
   * @param <T> lookup object type
   * @param beanName String lookup object mapping name
   * @return T lookup object or null
   */
  public <T> T lookup(String beanName) {
    lock.readLock().lock();
    try {
      return objectPool.get(beanName);
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * lookup a optional object by its type, if not find firstly, try to instance by instanceType and
   * constructor parameters args
   * 
   * @param <T> lookup object type
   * @param type Class lookup key type
   * @param args Object[] instance constructor parameters
   * @return Optional of lookup object
   */
  public <T> Optional<T> lookupIf(Class<T> type, Object... args) {
    return Optional.ofNullable(lookup(type, args));
  }

  /**
   * lookup a optional object by its type, if not find firstly, try to instance by instanceType and
   * constructor parameters args
   * 
   * @param <T> lookup object type
   * @param <E> lookup object type
   * @param searchType Class lookup key type
   * @param instancewrapper Class instance type when not lookup the key
   * @param args Object[] instance constructor parameters
   * @return Optional of lookup object
   */
  public <T, E extends T> Optional<T> lookupIf(final Class<T> searchType,
      InstanceWrapper<E> instancewrapper, Object... args) {
    return Optional.ofNullable(lookup(searchType, instancewrapper, args));
  }

  /**
   * look up optional object by its bean name
   * 
   * @param <T> lookup object type
   * @param beanName String lookup object mapping name
   * @return Optional of lookup object
   */
  public <T> Optional<T> lookupIf(String beanName) {
    return Optional.ofNullable(lookup(beanName));
  }

  /**
   * 
   * lookupSPI:this will used {@link ServiceLoader} to load SPI which defined in folder
   * <B>META-INF/services</B>. <br>
   * It will first to try to load instance from cached {@link ObjectPool}, if not found, then try to
   * load SPI class and instantiate it.<br>
   * Notice: only load and instantiate first SPI class in defined file<br>
   * 
   * @param <T> lookup SPI interface class
   * @param spiInterface SPI interface or abstract class type
   * @param args constructor arguments
   * @return implementation of parameter spiInterface
   */
  public <T> T lookupSPI(Class<T> spiInterface, Object... args) {
    return lookupFocusSPI(spiInterface, null, args);
  }

  /**
   * 
   * lookupFocusSPI:this will used {@link ServiceLoader} to load SPI which defined in folder
   * <B>META-INF/services</B>. <br>
   * It will first to try to load instance from cached {@link ObjectPool}, if not found, then try to
   * load SPI class and instantiate it.<br>
   * Notice: only load and instantiate focused SPI class in defined file<br>
   * 
   * @param <T> lookup SPI interface class
   * @param spiInterface SPI interface or abstract class type
   * @param focusClassName focused SPI implementation class aname
   * @param args constructor arguments
   * @return implementation of parameter spiInterface
   * 
   */
  public <T> T lookupFocusSPI(Class<T> spiInterface, String focusClassName, Object... args) {
    T object = null;
    lock.readLock().lock();
    try {
      object = objectPool.get(spiInterface);
    } finally {
      lock.readLock().unlock();
    }
    if (object == null) {
      lock.writeLock().lock();
      try {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(spiInterface, focusClassName, args);
        object = serviceLoader.iterator().next();
        Checker.require(object).notNull("Cannot found SPI implementation for " + spiInterface);
        register(spiInterface, object);
      } finally {
        lock.writeLock().unlock();
      }
    }
    return object;
  }

  /**
   * 
   * lookupSPISkipCache:this will used {@link ServiceLoader} to load SPI which defined in folder
   * <B>META-INF/services</B>. <br>
   * It will do load SPI skip cache each time, not try to lookup from cache firstly.<br>
   * Notice: only load and instantiate first SPI class in defined file<br>
   * 
   * @param <T> lookup SPI interface class
   * @param spiInterface SPI interface or abstract class type
   * @param args constructor arguments
   * @return implementation of parameter spiInterface
   * @throws CheckerNoPassException when not found implementation SPI
   */
  public <T> T lookupSPISkipCache(Class<T> spiInterface, Object... args) {
    return lookupFocusSPISkipCache(spiInterface, null, args);
  }

  /**
   * 
   * lookupFocusSPISkipCache:this will used {@link ServiceLoader} to load SPI which defined in
   * folder <B>META-INF/services</B>. <br>
   * It will do load SPI skip cache each time, not try to lookup from cache firstly.<br>
   * Notice: only load and instantiate focused SPI class in defined file<br>
   * 
   * @param <T> lookup SPI interface class
   * @param spiInterface SPI interface or abstract class type
   * @param focusClassName focused SPI implementation class name
   * @param args constructor arguments
   * @return implementation of parameter spiInterface
   * 
   * @throws CheckerNoPassException when not found implementation SPI
   */
  public <T> T lookupFocusSPISkipCache(Class<T> spiInterface, String focusClassName,
      Object... args) {
    T object = null;
    ServiceLoader<T> serviceLoader = ServiceLoader.load(spiInterface, focusClassName, args);
    object = serviceLoader.iterator().next();
    Checker.require(object).notNull("Cannot found SPI implementation for " + spiInterface);
    return object;
  }

  /**
   * 
   * lookupAllSPI:fina all instance of SPI implementation. <br>
   * Notice:<BR>
   * <ul>
   * <li>all implementation need default constructor.</li>
   * <li>do not search from cache</li>
   * </ul>
   * 
   * @param <T> SPI type
   * @param spiInterface SPI interface or abstract class type
   * @return list of all SPI implementation instance
   */
  public <T> List<T> lookupAllSPI(Class<T> spiInterface) {
    List<T> list = new ArrayList<>();
    ServiceLoader<T> serviceLoader = ServiceLoader.load(spiInterface);
    Iterator<T> iterator = serviceLoader.iterator();
    while (iterator.hasNext()) {
      try {
        list.add(iterator.next());
      } catch (Exception e) {
        // ignore
      }
    }
    return list;
  }

  /**
   * 
   * register an instance, which key is object.getClass(). <br>
   * 
   * @param <T> registered object class type
   * @param object instance which need registered
   */
  public <T> void register(T object) {
    if (object != null) {
      lock.writeLock().tryLock();
      try {
        objectPool.put(object.getClass(), object);
      } finally {
        lock.writeLock().unlock();
      }
    }
  }

  /**
   * register an instance, which key is given parameter classType
   * 
   * @param <T> registered object class type
   * @param classType Class as the key for registered instance
   * @param object instance which need registered
   */
  public <T> void register(Class<T> classType, T object) {
    if (object != null) {
      lock.writeLock().tryLock();
      try {
        objectPool.put(classType, object);
      } finally {
        lock.writeLock().unlock();
      }
    }
  }

  /**
   * register object and mapping it to given bean name
   * 
   * @param <T> register object type
   * @param beanName String bean name
   * @param object register object
   */
  public <T> void register(String beanName, T object) {
    if (object != null) {
      lock.writeLock().lock();
      try {
        objectPool.put(beanName, object);
      } finally {
        lock.writeLock().unlock();
      }
    }
  }

  protected void cleanUp() {
    lock.writeLock().lock();
    try {
      objectPool.cleanUp();
    } finally {
      lock.writeLock().unlock();
    }
  }
}
