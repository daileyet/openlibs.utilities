package com.openthinks.libs.utilities.net.udp;

import java.util.Optional;

/**
 * ClassName: EndPointSettings <br>
 * date: Dec 25, 2017 2:21:43 PM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 */
public interface EndPointSettings {
  /**
   * 10KB received buffer
   */
  int DEFAULT_BUFFER_SIZE = 10240;
  int NO_BIND_PORT = -1;

  Optional<String> getDefaultRemoteAddr();

  Optional<Integer> getDefaultRemotePort();

  default int getLocalBindPort() {
    return NO_BIND_PORT;
  };

  /**
   * 
   * getPacketBufferSize: the size of received buffer. <br>
   * 
   * @return size of received packet buffer size; default size is 1024 bytes
   */
  default int getPacketBufferSize() {
    return DEFAULT_BUFFER_SIZE;
  };

  static EndPointSettings createEndPointSettings(final int localPort, final Integer bufferSize,
      final String remoteAddr, final Integer remotePort) {
    return new EndPointSettings() {
      @Override
      public int getLocalBindPort() {
        return localPort;
      }

      @Override
      public int getPacketBufferSize() {
        return bufferSize == null ? DEFAULT_BUFFER_SIZE : bufferSize;
      }

      @Override
      public Optional<Integer> getDefaultRemotePort() {
        return Optional.ofNullable(remotePort);
      }

      @Override
      public Optional<String> getDefaultRemoteAddr() {
        return Optional.ofNullable(remoteAddr);
      }
    };
  }

  static EndPointSettings createEndPointSettings(final Integer bufferSize, final String remoteAddr,
      final int remotePort) {
    return createEndPointSettings(NO_BIND_PORT, bufferSize, remoteAddr, remotePort);
  }

  static EndPointSettings createEndPointSettings(final String remoteAddr, final int remotePort) {
    return createEndPointSettings(NO_BIND_PORT, DEFAULT_BUFFER_SIZE, remoteAddr, remotePort);
  }

  static EndPointSettings createEndPointSettings() {
    return createEndPointSettings(NO_BIND_PORT, DEFAULT_BUFFER_SIZE, null, null);
  }
}
