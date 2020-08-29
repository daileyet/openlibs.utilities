package com.openthinks.libs.utilities.net.udp;

import java.net.DatagramPacket;
import java.util.EventListener;

import com.openthinks.libs.utilities.net.TransferMessage;


/**
 * ClassName: EndPointListener <br>
 * date: Dec 18, 2017 10:16:34 AM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 */
public interface EndPointListener extends EventListener {

	/**
	 * notification when {@link EndPoint} received a message
	 * @param model the business model decode by {@link ProtocolMarshalling}, not {@link TransferMessage}
	 * @param packet {@link DatagramPacket}
	 */
	void onReceived(Object model, DatagramPacket packet);

	/**
	 * notification when {@link EndPoint} encounter a exception
	 * @param e Exception
	 */
	default void onExceptionCaught(Exception e) {
	}

	/**
	 * notification when {@link EndPoint} send a message
	 * @param message {@link TransferMessage}
	 * @param packet {@link DatagramPacket}
	 */
	default void onDeliveryComplete(TransferMessage message,DatagramPacket packet) {
	}
}
