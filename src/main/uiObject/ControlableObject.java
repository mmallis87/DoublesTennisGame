package main.uiObject;

import network.*;

import java.awt.*;
import java.io.IOException;

public abstract class ControlableObject {

    private PingListener pingListener = null;
    private ClientSocket clientSocket = null;

    public ControlableObject() {
	try {
	    clientSocket = new ClientSocket();
	} catch (IOException e) {
	    System.out
		    .println("Cannot create a client socket for a ControlableObject: "
			    + e.getMessage());
	}
    }

    public NetworkListener getListener() {
	return pingListener;
    }

    public void setListener(PingListener listener) {
	this.pingListener = listener;
    }

    public ClientSocket getSocket() {
	return clientSocket;
    }

    public void setSocket(ClientSocket socket) {
	this.clientSocket = socket;
    }

    public void update() {
    }

    public void draw(Graphics g) {
    };

    public void move() {
    };

}