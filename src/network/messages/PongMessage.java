package network.messages;

public class PongMessage extends AbstractMessage {
    protected String data;

    public PongMessage() {
    }

    public PongMessage(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getContent() {
        return data;
    }

    @Override
    public void setContent(String string) {
        this.data = string;
    }
}
