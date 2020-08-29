package com.openthinks.libs.utilities.net.udp;

import java.net.DatagramPacket;

/**
 * ClassName: ProtocolMarshalling <br>
 * date: Dec 18, 2017 10:28:45 AM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 */
public interface ProtocolMarshalling {

  public DatagramPacket encode(Object model);

  public Object decode(DatagramPacket packet);

  ProtocolMarshalling NULL = new ProtocolMarshalling() {

    @Override
    public DatagramPacket encode(Object model) {
      return null;
    }

    @Override
    public Object decode(DatagramPacket packet) {
      return null;
    }
  };
}
