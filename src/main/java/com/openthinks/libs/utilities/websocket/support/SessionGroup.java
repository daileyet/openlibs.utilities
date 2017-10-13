package com.openthinks.libs.utilities.websocket.support;


import java.util.HashSet;

/**
 * 
 * ClassName: SessionGroup <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:23:00 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public final class SessionGroup<T extends IEndPointSession> extends HashSet<T>
    implements IEndPointSession {
  private static final long serialVersionUID = 3529009300847141055L;
  private String groupName;

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public String getId() {
    return groupName;
  }

  @Override
  public String getGroup() {
    return groupName;
  }

}
