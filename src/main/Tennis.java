package main;

import network.*;
import java.io.IOException;

public class Tennis implements Runnable {

    private GameControlPanel gameController;

    public Tennis() {
	try {
	    System.out
		    .println("Doubles Tennis game launching! Waiting for players to join...");
	    gameController = new GameControlPanel();
	} catch (IOException e) {
	    System.out
		    .println("There was an error while creating a new game controller!");
	}
	// addMouseMotionListener(gameController);
	// addKeyListener(gameController);
    }

    private GameControlPanel getGameController() {
	return gameController;
    }

    public void startGame() {
	System.out.println("Starting a new game...");
	Thread thread = new Thread(this);
	thread.start();
	System.out.println("Tennis game started!");
    }

    public void run() {

	assert (gameController != null);

	gameController.launchDemo();

	boolean stop = false;
	ServerSocket serverSocket = gameController.getServerSocket();
	if (serverSocket == null)
	    return;

	while (!stop) {
	    synchronized (serverSocket) {
		try {
		    System.out
			    .println("Tennis.java: Before server socket wait.");
		    serverSocket.wait();
		    System.out
			    .println("Tennis.java: After server socket wait.");
		    stop = true;
		} catch (InterruptedException e) {
		    System.out
			    .println("The current process was interrupted.\n Exiting...");
		}
		String command = serverSocket.getBuffer();
		System.out.println("received: " + command);
		if (command.length() < 2)
		    break;

		switch (command.charAt(0)) {
		case '1':
		    gameController.keyPressed(command.charAt(1));
		    break;
		case '2':
		    gameController.keyReleased(command.charAt(1));
		    break;

		default:
		    break;
		}
	    }
	}
    }

    public static void main(String[] argv) throws IOException {
	/*
	 * final JFrame mainFrame = new JFrame("Tennis");
	 * mainFrame.setContentPane(client); mainFrame.addWindowListener(new
	 * WindowAdapter() { public void windowClosing(WindowEvent e) {
	 * System.exit(0); } public void windowActivated(WindowEvent e) {
	 * mainFrame.getContentPane().requestFocus(); } });
	 * 
	 * mainFrame.pack(); mainFrame.setVisible(true);
	 */

	Tennis tennis = new Tennis();
	GameControlPanel gameController = tennis.getGameController();
	System.out.println("Tennis.java: got a game controller.");
	if (gameController == null) {
	    System.out
		    .println("There was an error while starting a new game.\nExiting...");
	}

	GUIHelper.showOnFrame(gameController.getFramePanel(), "tennis");
	tennis.startGame();
    }

}
