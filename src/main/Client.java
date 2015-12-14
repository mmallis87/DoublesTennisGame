package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import network.messages.PingMessage;
import main.uiObject.Player;

@SuppressWarnings("serial")
public class Client extends JPanel implements KeyListener {
    FramePanel framePanel;
    JLabel direction;
    JPanel indications;
    private Player player = new Player();

    public Client() {
	framePanel = new FramePanel();
	direction = new JLabel("direction");
	indications = new JPanel();
	indications.setLayout(new GridLayout(1, 1));
	indications.add(direction);
	setLayout(new BorderLayout(5, 5));
	add(indications, BorderLayout.NORTH);
	add(framePanel, BorderLayout.CENTER);

	addKeyListener(this);
    }

    public static void main(String[] argv) throws IOException {
	@SuppressWarnings("unused")
	Client client = new Client();
    }

    public void keyPressed(KeyEvent e) {
	try {
	    System.out.println("client: 1" + e.getKeyCode());
	    player.getSocket().send(new PingMessage("1" + e.getKeyCode()));
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    public void keyReleased(KeyEvent e) {
	try {
	    System.out.println("client: 2" + e.getKeyCode());
	    player.getSocket().send(new PingMessage("2" + e.getKeyCode()));
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    public void keyTyped(KeyEvent e) {
	try {
	    System.out.println("client: 3" + e.getKeyCode());
	    player.getSocket().send(new PingMessage("3" + e.getKeyCode()));
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }
}