package network;

import network.messages.*;

public interface NetworkListener {
    public void notifyMessageArrived(AbstractSocket sender, AbstractMessage message);
}
