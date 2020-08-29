package com.openthinks.libs.utilities.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.openthinks.libs.utilities.DateUtils;
import com.openthinks.libs.utilities.EventListenerList;
import com.openthinks.libs.utilities.net.Lifecycle;
import com.openthinks.libs.utilities.net.TransferMessage;

/**
 * ClassName: EndPoint <br>
 * Function: UDP end point, could be take as UDP client or server. <br>
 * date: Nov 24, 2017 11:14:34 AM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 */
public final class EndPoint implements Lifecycle {
  private volatile boolean running = false;
  private DatagramSocket socket;
  private Future<?> receiveFuture;
  private final ExecutorService executorService;
  private final EventListenerList listeners;
  private ProtocolMarshalling protocolMarshalling = ProtocolMarshalling.NULL;
  private final EndPointSettings settings;

  public EndPoint(EndPointSettings settings, ExecutorService executorService) {
    this.executorService = executorService;
    this.listeners = new EventListenerList();
    this.settings = settings;
  }

  public EndPoint(EndPointSettings settings, EventListenerList listeners,
      ExecutorService executorService) {
    this.executorService = executorService;
    this.listeners = listeners;
    this.settings = settings;
  }

  /**
   * tell current running status
   * 
   * @return true or false
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * 
   * send: send message directly by UDP and with default destination address in instance of
   * {@link EndPointSettings}.
   * 
   * @param message {@link TransferMessage}
   * @throws IOException if an I/O error occurs.
   */
  public void send(TransferMessage message) throws IOException {
    boolean isValid =
        settings.getDefaultRemoteAddr().isPresent() && settings.getDefaultRemotePort().isPresent();
    if (!isValid) {
      throw new IllegalArgumentException("Not setting default remote UDP address and port.");
    }
    InetSocketAddress defaultServerAddress = new InetSocketAddress(
        settings.getDefaultRemoteAddr().get(), settings.getDefaultRemotePort().get());
    send(message, defaultServerAddress);
  }

  /**
   * 
   * send: send message directly by UDP and with special destination address.
   * 
   * @param message {@link TransferMessage}
   * @param remoteAddress destination address
   * @throws IOException if an I/O error occurs.
   */
  public void send(TransferMessage message, SocketAddress remoteAddress) throws IOException {
    DatagramPacket dataPack = encode(message.model);
    dataPack.setSocketAddress(remoteAddress);
    this.socket.send(dataPack);
    message.setFinish(DateUtils.currentTimeMillis());
    fireDeliveryComplete(message, dataPack);
  }

  public void addEndPointListener(EndPointListener udpClientListener) {
    this.listeners.add(EndPointListener.class, udpClientListener);
  }

  public void removeEndPointListener(EndPointListener listener) {
    listeners.remove(EndPointListener.class, listener);
  }

  public void installProtocolMarshalling(ProtocolMarshalling protocolMarshalling) {
    this.protocolMarshalling = protocolMarshalling;
  }

  class ReceiverWork implements Runnable {
    @Override
    public void run() {
      final byte[] receiveData = new byte[settings.getPacketBufferSize()];
      while (running && !Thread.currentThread().isInterrupted()) {
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
          socket.receive(receivePacket);
          Object obj = decode(receivePacket);
          fireOnReceived(obj, receivePacket);
        } catch (SocketException e) {
          if (running) {// stop should not capture this type exception
            fireOnException(e);
          }
        } catch (Exception e) {
          fireOnException(e);
        }
      }

    }
  }

  @Override
  public synchronized void start() {
    if (running) {
      return;
    }
    if (receiveFuture != null) {
      receiveFuture.cancel(true);
      receiveFuture = null;
    }
    if (socket != null) {
      socket.close();
      socket = null;
    }
    try {
      this.socket = createSocket();
      running = true;
      receiveFuture = executorService.submit(new ReceiverWork());
    } catch (SocketException e) {
      fireOnException(e);
    }

  }

  private DatagramSocket createSocket() throws SocketException {
    if (this.socket == null || this.socket.isClosed()) {
      if (this.settings.getLocalBindPort() == EndPointSettings.NO_BIND_PORT)
        this.socket = new DatagramSocket();
      else
        this.socket = new DatagramSocket(settings.getLocalBindPort());
    }
    return this.socket;
  }

  protected void fireOnException(Exception e) {
    Object[] listeners = this.listeners.getListenerList();
    int numListeners = listeners.length;
    for (int i = 0; i < numListeners; i += 2) {
      ((EndPointListener) listeners[i + 1]).onExceptionCaught(e);
    }
  }

  protected void fireOnReceived(Object obj, DatagramPacket packet) {
    Object[] listeners = this.listeners.getListenerList();
    int numListeners = listeners.length;
    for (int i = 0; i < numListeners; i += 2) {
      ((EndPointListener) listeners[i + 1]).onReceived(obj, packet);
    }
  }

  protected void fireDeliveryComplete(TransferMessage message, DatagramPacket packet) {
    Object[] listeners = this.listeners.getListenerList();
    int numListeners = listeners.length;
    for (int i = 0; i < numListeners; i += 2) {
      ((EndPointListener) listeners[i + 1]).onDeliveryComplete(message, packet);
    }

  }

  private DatagramPacket encode(Object model) {
    return protocolMarshalling.encode(model);
  }

  private Object decode(DatagramPacket receivePacket) {
    return protocolMarshalling.decode(receivePacket);
  }

  @Override
  public synchronized void stop() {
    running = false;
    if (receiveFuture != null) {
      receiveFuture.cancel(true);
      receiveFuture = null;
    }
    if (socket != null) {
      socket.close();
      socket = null;
    }
  }

  @Override
  public void shrink() {}

  @Override
  public void expand() {}
}
