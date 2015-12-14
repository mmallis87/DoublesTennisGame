package main;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import network.AbstractSocket;
import network.ClientSocket;
import network.NetworkListener;
import network.ServerSocket;
import network.messages.AbstractMessage;
import network.messages.PingMessage;
import network.messages.PongMessage;

class PingListener implements NetworkListener {
    AbstractSocket socket;

    public PingListener(AbstractSocket server) {
	this.socket = server;
    }

    public void notifyMessageArrived(AbstractSocket sender,
	    AbstractMessage message) {
	if (message instanceof PingMessage) {
	    PingMessage ping = (PingMessage) message;
	    PongMessage pong = new PongMessage();
	    pong.setBroadcast(ping.isBroadcast());
	    pong.setData(ping.getData());

	    System.out.println("Ping received! Replying... " + pong);

	    try {
		socket.send(pong);
	    } catch (IOException e) {
		System.out.println(e);
	    }
	}
    }
}

class EchoListener implements NetworkListener {
    public void notifyMessageArrived(AbstractSocket sender,
	    AbstractMessage message) {
	System.out.println(message);
    }
}

class BroadcastListener implements NetworkListener {
    protected Vector<ServerSocket> serverSockets = new Vector<ServerSocket>();

    public void addServerSocket(ServerSocket server) {
	serverSockets.add(server);
    }

    public void notifyMessageArrived(AbstractSocket sender,
	    AbstractMessage message) {
	System.out.println("Message arrived ...");
	if (!message.isBroadcast())
	    return;
	System.out.println("Broadcast message! Sending to other clients...");

	Iterator<ServerSocket> iterator = serverSockets.iterator();
	ServerSocket s;
	while (iterator.hasNext()) {
	    s = iterator.next();
	    if (s != sender) {
		try {
		    s.send(message);
		} catch (IOException e) {
		}
	    }
	}
    }
}

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
	/*
	 * MessageFactory messageFactory = MessageFactory.getInstance();
	 * PingMessage message;
	 * 
	 * message = new PingMessage("test"); message.setBroadcast(true);
	 * System.out.println(message);
	 * 
	 * String serialization = messageFactory.serialize(message);
	 * System.out.println(Serialization);
	 * 
	 * message = (PingMessage) messageFactory.deserialize(serialization);
	 * 
	 * System.out.println(message);
	 */

	if (args.length > 0) {
	    try {
		EchoListener echoListener = new EchoListener();
		BroadcastListener broadcastListener = new BroadcastListener();

		Vector<ServerSocket> serverSockets = new Vector<ServerSocket>();

		for (int i = 0; i < 4; i++) {
		    ServerSocket server = new ServerSocket();
		    serverSockets.add(server);

		    server.addListener(echoListener);
		    broadcastListener.addServerSocket(server);
		    server.addListener(broadcastListener);
		}
	    } catch (Exception e) {
		System.out.println(e);
	    }
	} else {
	    try {
		ClientSocket client = new ClientSocket();

		client.addListener(new EchoListener());
		client.addListener(new PingListener(client));

		PingMessage message;
		message = new PingMessage("Broadcast message");
		message.setBroadcast(true);
		System.out.println(message);
		client.send(message);
	    } catch (Exception e) {
		System.out.println(e);
	    }
	}
    }

}
