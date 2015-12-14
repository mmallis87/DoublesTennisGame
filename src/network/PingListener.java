package network;

import network.messages.AbstractMessage;
import network.messages.PingMessage;

public class PingListener implements NetworkListener {
    AbstractSocket socket;

    public PingListener(AbstractSocket socket) {
	this.socket = socket;
    }

    public void notifyMessageArrived(AbstractSocket sender, AbstractMessage message) {
	if (message instanceof PingMessage) {
	    PingMessage ping = (PingMessage) message;
	    String buffer = ping.getContent();
	    socket.setBuffer(buffer);
	}
	socket.notifyAll();
    }
}