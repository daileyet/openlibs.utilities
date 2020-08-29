/**
 * 
 */
package com.openthinks.libs.utilities.logger;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Root Logger ImplManager
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class RootLoggerManager extends ImplManager {

  private static final RootLoggerManager INSTANCE = new RootLoggerManager();

  public static RootLoggerManager getInstance() {
    return INSTANCE;
  }

  private RootLoggerManager() {}

  private final Map<String, Impl> implMap = new ConcurrentHashMap<>();

  public boolean isEmpty() {
    return implMap.isEmpty();
  }

  public synchronized boolean addLoggerImpl(String name, Impl loggerImpl) {
    if (loggerImpl != null) {
      implMap.put(name, loggerImpl);
      return true;
    }
    return false;
  }

  public synchronized boolean removeLoggerImpl(Impl loggerImpl) {
    if (loggerImpl == null)
      return false;
    Set<Entry<String, Impl>> hitSet = implMap.entrySet().stream()
        .filter(entry -> entry.getValue() == loggerImpl).collect(Collectors.toSet());
    if (hitSet.isEmpty())
      return false;
    for (Entry<String, Impl> e : hitSet) {
      implMap.remove(e.getKey());
    }
    return true;
  }

  public synchronized boolean removeLoggerImpl(String name) {
    if (name == null)
      return false;
    return implMap.remove(name) != null;
  }

  @Override
  public Impl createImpl() {
    return new InternalImpl();
  }

  final class InternalImpl extends ConsoleImpl {

    @Override
    public void action(PLLevel level, String pattern, Object... arguments) {
      if (implMap.isEmpty()) {
        super.action(level, pattern, arguments);
      } else {
        implMap.forEach((k, v) -> {
          v.action(level, pattern, arguments);
        });
      }
    }

    @Override
    public void action(PLLevel level, Exception... exs) {
      if (implMap.isEmpty()) {
        super.action(level, exs);
      } else {
        implMap.forEach((k, v) -> {
          v.action(level, exs);
        });
      }
    }

  }

}
