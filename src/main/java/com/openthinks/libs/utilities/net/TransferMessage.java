package com.openthinks.libs.utilities.net;

import java.io.Serializable;
import com.openthinks.libs.utilities.DateUtils;

/**
 * 
 * ClassName: TransferMessage is one wrapper of send data object, it record the delay
 */
public class TransferMessage implements Serializable {
  private static final long serialVersionUID = -2529118767504410503L;
  /*
   * when the message created
   */
  public final long create = DateUtils.currentTimeMillis();
  /*
   * data object to send
   */
  public final Object model;
  /*
   * when message was send out
   */
  protected volatile long finish;
  /*
   * default lowest priority
   */
  private Priorities qos;

  public TransferMessage(Object model) {
    this(model, Priorities.Medium);
  }

  public TransferMessage(Object model, Priorities qos) {
    super();
    this.model = model;
    this.qos = qos;
  }

  public final long getFinish() {
    return finish;
  }

  public final long getCreate() {
    return create;
  }

  public final int getDelay() {
    return (int) (finish - create);
  }

  public final int getTimeSkipped() {
    return (int) (DateUtils.currentTimeMillis() - create);
  }

  public final Priorities getQoS() {
    return qos;
  }

  public final void setQoS(Priorities qos) {
    this.qos = qos;
  }

  public void setFinish(long finish) {
    this.finish = finish;
  }

  @Override
  public String toString() {
    return "TransferMessage [create=" + create + ", model=" + model + ", finish=" + finish
        + ", QoS=" + qos + "]";
  }
}
