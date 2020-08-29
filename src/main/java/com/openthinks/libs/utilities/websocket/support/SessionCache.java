package com.openthinks.libs.utilities.websocket.support;


import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * 
 * ClassName: SessionCache <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:22:53 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public final class SessionCache<T extends IEndPointSession> {
  private final Map<String, T> allSessionCache; // session id <=> session
  private final Map<String, SessionGroup<T>> groupCache; // group id <=> group session

  public SessionCache() {
    allSessionCache = new ConcurrentHashMap<String, T>();
    groupCache = new ConcurrentHashMap<String, SessionGroup<T>>();
  }

  public int allSize() {
    return allSessionCache.size();
  }

  public int groupSize(String groupName) {
    SessionGroup<T> sessionGrp = groupCache.get(groupName);
    return sessionGrp == null ? 0 : sessionGrp.size();
  }

  public void add(T session) {
    allSessionCache.put(session.getId(), session);
    SessionGroup<T> group = groupCache.get(session.getGroup());
    if (group == null) {
      group = new SessionGroup<T>();
      groupCache.put(session.getGroup(), group);
    }
    group.add(session);
    ProcessLogger.debug(allSessionCache.keySet().toString());
  }

  public SessionGroup<T> getSessionGroup(IEndPointSession ednpointSession) {
    SessionGroup<T> group = groupCache.get(ednpointSession.getGroup());
    if (group == null) {
      group = new SessionGroup<T>();
    }
    return group;
  }

  public Collection<T> allSession() {
    return allSessionCache.values();
  }

  public void remove(IEndPointSession ednpointSession) {
    getSessionGroup(ednpointSession).remove(ednpointSession);
    allSessionCache.remove(ednpointSession.getId());
  }

  public void removeGroup(IEndPointSession ednpointSession) {
    SessionGroup<T> group = getSessionGroup(ednpointSession);
    for (T t : group) {
      allSessionCache.remove(t.getId());
    }
    groupCache.remove(group.getId());
  }
}
