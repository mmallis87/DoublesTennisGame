package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

import network.messages.AbstractMessage;
import network.messages.MessageFactory;

/**
 * Abstract class, parent of all socket classes (Client and Server).
 */
public abstract class AbstractSocket implements Runnable {
    /**
     * Server IP. defaults to localhost
     */
    protected static final String ip = "127.0.0.1";

    /**
     * Server port. defaults to 7777
     */
    protected static final int port = 7777;

    /**
     * The java.net Socket object. To be initialized in createSocket method.
     */
    protected Socket socket = null;

    /**
     * The listeners to be notified when a message arrived on the Socket.
     */
    protected Vector<NetworkListener> listeners;

    /**
     * The socket output stream.
     */
    protected BufferedWriter outputStream;

    /**
     * The socket input stream.
     */
    protected BufferedReader inputStream;

    /**
     * The Socket String buffer.
     */
    private String buffer;

    /**
     * The Socket main thread.
     */
    protected Thread thread;

    /**
     * Default constructor
     * 
     * @throws IOException
     *             if failed to create the Socket.
     */
    public AbstractSocket() throws IOException {
	if (socket == null) {
	    createSocket();
	}
	assert (socket != null);

	inputStream = new BufferedReader(new InputStreamReader(
		socket.getInputStream()));
	outputStream = new BufferedWriter(new OutputStreamWriter(
		socket.getOutputStream()));
	assert (inputStream != null);
	assert (outputStream != null);

	listeners = new Vector<NetworkListener>();

	thread = new Thread(this);
	thread.start();
    }

    /**
     * Creates a java.net Socket object.
     * 
     * @throws IOException
     *             If not able to create the socket with the provided IP and
     *             port.
     */
    protected void createSocket() throws IOException {
	socket = new Socket(ip, port);
    }

    /**
     * Runs the Socket thread in order to listen for messages.
     */
    public void run() {
	while (true) {
	    try {
		String line = inputStream.readLine();
		AbstractMessage message = MessageFactory.getInstance()
			.deserialize(line);

		if (message != null) {
		    notifyNewMessage(message);
		}
	    } catch (IOException ioe) {
		System.out
			.println("Error while reading the socket input stream at ip="
				+ ip + " and port=" + port);
	    }
	}
    }

    /**
     * Sends a message on the Socket object.
     * 
     * @param message
     *            The message to be serialized and to be sent.
     * @throws IOException
     *             If fails to write on the output stream.
     */
    public void send(AbstractMessage message) throws IOException {
	String line = MessageFactory.getInstance().serialize(message) + "\n";
	outputStream.write(line);
	outputStream.flush();
    }

    /**
     * Adds a listener.
     * 
     * @param listener
     *            the listener to be added.
     */
    public void addListener(NetworkListener listener) {
	if (!listeners.contains(listener))
	    listeners.add(listener);
    }

    /**
     * Removes a listener.
     * 
     * @param listener
     *            the listener to be removed.
     */
    public void removeListener(NetworkListener listener) {
	listeners.remove(listener);
    }

    /**
     * Notifies the listeners that a new message has arrived.
     * 
     * @param message
     *            the new message.
     */
    protected void notifyNewMessage(AbstractMessage message) {
	Iterator<NetworkListener> iterator = listeners.iterator();
	while (iterator.hasNext()) {
	    iterator.next().notifyMessageArrived(this, message);
	}
    }

    public void setBuffer(String buffer) {
	this.buffer = buffer;
    }

    public String getBuffer() {
	return buffer;
    }
}
