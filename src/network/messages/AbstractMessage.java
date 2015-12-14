package network.messages;

/**
 * Abstract class, parent to all Message classes.
 */
public abstract class AbstractMessage {
    /**
     * Whether the message should be broadcasted, in which case the message is
     * sent to all clients.
     */
    protected boolean broadcast = false;

    public AbstractMessage() {
    }

    /**
     * Checks whether the message is a broadcast.
     * 
     * @return true if the message is a broadcast and false otherwise.
     */
    public boolean isBroadcast() {
	return broadcast;
    }

    /**
     * Sets the broadcast flag.
     * 
     * @param value
     *            the new flag value.
     */
    public void setBroadcast(boolean value) {
	this.broadcast = value;
    }

    /**
     * Serializes the message to a String object.
     * 
     * @return the serialized message.
     */
    public abstract String getContent();

    /**
     * Sets the message content. 
     * 
     * @param content a serialized message String object.
     */
    public abstract void setContent(String content);

    public String toString() {
	return this.getClass().getName()
		+ (this.broadcast ? " (broadcast)" : "") + ": "
		+ this.getContent();
    }
}
