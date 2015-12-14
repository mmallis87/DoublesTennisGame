package network;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket extends AbstractSocket {

    public ClientSocket() throws IOException {
	super();
    }

    public ClientSocket(Socket socket) throws IOException {
	// this.socket = new Socket(ip, port);
	this.socket = socket;
    }

    @Override
    protected void createSocket() throws IOException {
	super.createSocket();
	// socket = new Socket(ip, port);
    }

}
