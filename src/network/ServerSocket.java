package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocket extends AbstractSocket {
    protected java.net.ServerSocket listeningSocket = null;
    List<ClientSocket> connectedClientSockets;

    public ServerSocket() throws IOException {
	super();
	// createSocket();
    }

    public List<ClientSocket> getConnectedClientSockets() {
	return connectedClientSockets;
    }

    @Override
    protected void createSocket() throws IOException {
	if (listeningSocket == null) {
	    listeningSocket = new java.net.ServerSocket(port);
	}
	assert (listeningSocket != null);

	// WARNING blocking and waiting for client sockets!!!
	Socket client = listeningSocket.accept();
	ClientSocket clientSocket = new ClientSocket(client);
	// newC.addListener(new PingListener(this));
	if (connectedClientSockets == null)
	    connectedClientSockets = new ArrayList<ClientSocket>();
	connectedClientSockets.add(clientSocket);
	socket = client;
    }
}
