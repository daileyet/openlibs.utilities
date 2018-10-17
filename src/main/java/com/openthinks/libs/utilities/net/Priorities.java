package com.openthinks.libs.utilities.net;

/**
 * ClassName: Priorities <br>
 * Function: represent the Priority, instead of integer number by manual<br>
 * Reason: provider common usage for priority, use {@link Priorities#ordinal()} as final value <br>
 * date: Jul 21, 2017 10:26:33 AM <br>
 * 
 */
public enum Priorities {
  // 0
  Urgent('U'),
  // 1
  High('H'),
  // 2
  Medium('M'),
  // 3
  Low('L');

  private char code;

  private Priorities(char code) {
    this.code = code;
  }

  /**
   * 
   * isHigher: judge current instance priority is higher than other instance. <br>
   * 
   * @param otherPriority {@link Priorities}
   * @return true or false
   */
  public boolean isHigher(Priorities otherPriority) {
    return otherPriority == null ? true : this.ordinal() < otherPriority.ordinal();
  }

  /**
   * 
   * isEqual:judge current instance priority same as other priority. <br>
   * 
   * @param otherPriority {@link Priorities}
   * @return true or false
   */
  public boolean isEqual(Priorities otherPriority) {
    return otherPriority == null ? false : this.ordinal() == otherPriority.ordinal();
  }

  public static final Priorities toPriorities(char code) {
    for (Priorities p : Priorities.values()) {
      if (p.code == code) {
        return p;
      }
    }
    return null;
  }
}
