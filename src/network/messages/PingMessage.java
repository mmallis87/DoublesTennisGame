package network.messages;

public class PingMessage extends AbstractMessage {
    protected String data;

    public PingMessage() {
    }

    public PingMessage(String data) {
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
