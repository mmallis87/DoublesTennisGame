package main;

import javax.swing.*;

import network.ClientSocket;
import network.PingListener;
import network.ServerSocket;
import main.tennisGamePart.Team;
import main.tennisGamePart.TennisGame;
import main.tennisGamePart.TennisMatch;
import main.tennisGamePart.TennisSet;
import main.uiObject.Ball;
import main.uiObject.ControlableObject;
import main.uiObject.Player;
import main.uiObject.Player.Service;
import main.uiObject.Player.Side;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// TODO refactor this class to make it more readable and easy to maintain
public class GameControlPanel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1062185426982548030L;
    ServerSocket serverSocket;
    FramePanel framePanel;
    TennisMatch game;
    Timer timer;
    Team team1;
    Team team2;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Ball ball;
    JLabel directionLabel;
    JPanel indicationsPanel;

    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    boolean service = false;

    Player player = player1;

    GameControlPanel() throws IOException {
	/*
	 * this.ardoise = new Cadre(); this.ardoise.setPreferredSize(new
	 * Dimension(600, 400)); this.ardoise.setForeground(getBackground());
	 * this.direction = new JLabel(" "); this.indications = new JPanel();
	 * this.player1 = new Player(); this.ball = new Ball();
	 * indications.setLayout(new GridLayout(1, 1));
	 * indications.add(direction); setLayout(new BorderLayout(5, 5));
	 * add(indications, BorderLayout.NORTH); add(ardoise,
	 * BorderLayout.CENTER); ardoise.addObjet(joueur1);
	 * ardoise.addObjet(ball);
	 */

	// this.fenetre = fenetre;
	// this.player = player;
	// addKeyListener (this);
	/*
	 * timer = createTimer(); timer.start (); joueur.setPosition(50,50);
	 * player.setVitesse(0,0); ball.setPosition(30, 10, 20);
	 * ball.setVitesse(8, 3, 10); this.dessinerTerrain();
	 */
	super();

	serverSocket = new ServerSocket();
	System.out.println("GCP.java: server socket was created.");
	List<ClientSocket> connectedClientSockets = serverSocket
		.getConnectedClientSockets();
	assert (connectedClientSockets.size() > 0);
	player1 = new Player(Player.Side.LEFT);
	player2 = new Player(Player.Side.LEFT);
	player3 = new Player(Player.Side.RIGHT);
	player4 = new Player(Player.Side.RIGHT);
	team1 = new Team(player1, player2);
	team2 = new Team(player3, player4);
	ball = new Ball();
	System.out.println("GCP.java: UI objects were created.");
	// player1.setSocket(connectedClientSockets.get(0));

	// 2-way listener binding
	for (int i = 0; i < connectedClientSockets.size(); i++)
	    connectedClientSockets.get(i).addListener(
		    new PingListener(serverSocket));

	serverSocket.addListener(new PingListener(player1.getSocket()));
	serverSocket.addListener(new PingListener(player2.getSocket()));
	serverSocket.addListener(new PingListener(player3.getSocket()));
	serverSocket.addListener(new PingListener(player4.getSocket()));
	serverSocket.addListener(new PingListener(ball.getSocket()));
	System.out.println("GCP.java: listeners binding done.");

	framePanel = new FramePanel();
	framePanel.setFocusable(true);
	framePanel.requestFocus();
	framePanel.requestFocusInWindow();
	directionLabel = new JLabel("direction");
	indicationsPanel = new JPanel();
	indicationsPanel.setLayout(new GridLayout(1, 1));
	indicationsPanel.add(directionLabel);
	setLayout(new BorderLayout(5, 5));
	add(indicationsPanel, BorderLayout.NORTH);
	add(framePanel, BorderLayout.CENTER);

	// add a this object as a key listener, so it can handle its own events.
	framePanel.addKeyListener(this);
    }

    public FramePanel getFramePanel() {
	return framePanel;
    }

    public void launchDemo() {

	// this.controleur = new ControleurJeu(this.fenetre, this.joueur1);

	game = new TennisMatch(new TennisSet(new TennisGame()));

	// player1 = new Player(Player.Side.LEFT);
	player1.setNumPlayer(1);
	// player2 = new Player(Player.Side.LEFT);
	player2.setNumPlayer(2);
	// player3 = new Player(Player.Side.RIGHT);
	player3.setNumPlayer(3);
	// player4 = new Player(Player.Side.RIGHT);
	player4.setNumPlayer(4);
	// team1 = new Team(this.player1, this.player2);
	player1.setTeam(team1);
	player2.setTeam(team1);
	// team2 = new Team(this.player3, this.player4);
	player3.setTeam(team2);
	player4.setTeam(team2);
	// ball = new Ball();
	player1.setPosition(50, 150);
	player2.setPosition(100, 250);
	player3.setPosition(450, 150);
	player4.setPosition(450, 250);
	player1.setService(Player.Service.HIGH_SERVICE);
	// player1.setSpeed(0,0);
	ball.setPosition(30, 10, 20);
	ball.setSpeed(8, 3, 10);
	// player = player1;

	framePanel.setGame(game);

	framePanel.addObject(player1);
	framePanel.addObject(player2);
	framePanel.addObject(player3);
	framePanel.addObject(player4);
	framePanel.addObject(ball);

	timer = createTimer();
	timer.start();
	// framePanel.drawCourt();
    }

    public void stopDemo() {
	serverSocket.removeListener(player1.getListener());
	serverSocket.removeListener(player2.getListener());
	serverSocket.removeListener(player3.getListener());
	serverSocket.removeListener(player4.getListener());
	serverSocket.removeListener(ball.getListener());
    }

    private Timer createTimer() {
	ActionListener action = new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		// fenetre.controleur.notifier();
		updateUIObjects();
		framePanel.repaint();
	    }
	};

	return new Timer(100, action);
    }

    public void handleTennisPoint() {
	boolean point = false;
	TennisSet lastTennisSet = game.getLastTennisSet();
	assert (lastTennisSet != null);
	TennisGame lastTennisGame = lastTennisSet.getLastTennisGame();
	assert (lastTennisGame != null);

	if (ball.isOut() && (ball.getRebound() != 2)) {
	    if (team1.isHit()) {
		lastTennisGame.incrementTeam2Score();
		team1.setHit(false);
	    } else {
		lastTennisGame.incrementTeam1Score();
		team2.setHit(false);
	    }
	    point = true;
	    ball.setOut(false);
	    ball.setRebound(0);
	}
	if (ball.getRebound() == 2) {
	    if (team1.isHit()) {
		lastTennisGame.incrementTeam1Score();
		team1.setHit(false);
	    } else {
		lastTennisGame.incrementTeam2Score();
		team2.setHit(false);
	    }
	    point = true;
	    ball.setOut(false);
	    ball.setRebound(0);
	}

	if (point) {
	    if ((lastTennisGame.getTeam1Score() == 50)
		    | (lastTennisGame.getTeam2Score() == 50)) {
		if ((lastTennisSet.getTeam1NbTennisGames() == 6)
			| (lastTennisSet.getTeam1NbTennisGames() == 6)) {
		    // end of tennis set -> start a new one and switch team
		    // sides
		    game.addTennisSet(new TennisSet(new TennisGame()));
		    if (player1.getSide() == Side.LEFT) {
			player1.setSide(Side.RIGHT);
			player2.setSide(Side.RIGHT);
			player3.setSide(Side.LEFT);
			player4.setSide(Side.LEFT);
		    } else {
			player1.setSide(Side.LEFT);
			player2.setSide(Side.LEFT);
			player3.setSide(Side.RIGHT);
			player4.setSide(Side.RIGHT);
		    }
		} else {
		    // the set is not finished -> add the game to the set count
		    lastTennisSet.addTennisGame(new TennisGame());
		}

		// handle service position as well as player positions
		if ((player1.getService() == Service.LOW_SERVER)
			|| (player1.getService() == Service.HIGH_SERVER)) {
		    player1.setService(Service.NOT_ON_SERVICE);
		    player3.setService(Service.HIGH_SERVICE);
		    player3.initServicePosition();
		    player4.initNormalPosition('b');
		    player1.initNormalPosition('b');
		    player2.initNormalPosition('h');
		} else if ((player2.getService() == Service.LOW_SERVER)
			|| (player2.getService() == Service.HIGH_SERVER)) {
		    player2.setService(Service.NOT_ON_SERVICE);
		    player4.setService(Service.HIGH_SERVICE);
		    player4.initServicePosition();
		    player3.initNormalPosition('b');
		    player1.initNormalPosition('h');
		    player2.initNormalPosition('b');
		} else if ((player3.getService() == Service.LOW_SERVER)
			|| (player3.getService() == Service.HIGH_SERVER)) {
		    player3.setService(Service.NOT_ON_SERVICE);
		    player2.setService(Service.HIGH_SERVICE);
		    player2.initServicePosition();
		    player1.initNormalPosition('b');
		    player3.initNormalPosition('h');
		    player4.initNormalPosition('b');
		} else if ((player4.getService() == Service.LOW_SERVER)
			|| (player4.getService() == Service.HIGH_SERVER)) {
		    player4.setService(Service.NOT_ON_SERVICE);
		    player1.setService(Service.HIGH_SERVICE);
		    player1.initServicePosition();
		    player2.initNormalPosition('b');
		    player3.initNormalPosition('b');
		    player4.initNormalPosition('h');
		}
	    } else {
		if ((player1.getService() == Service.LOW_SERVER)
			|| (player1.getService() == Service.HIGH_SERVER)) {
		    player1.setService((player1.getService() == Service.HIGH_SERVER ? Service.LOW_SERVICE
			    : Service.HIGH_SERVICE));
		    player1.initServicePosition();
		    player2.initNormalPosition((player1.getService() == Service.HIGH_SERVICE ? 'b'
			    : 'h'));
		    player3.initNormalPosition('h');
		    player4.initNormalPosition('b');
		} else if ((player2.getService() == Service.LOW_SERVER)
			|| (player2.getService() == Service.HIGH_SERVER)) {
		    player2.setService((player2.getService() == Service.HIGH_SERVER ? Service.LOW_SERVICE
			    : Service.HIGH_SERVICE));

		    player2.initServicePosition();
		    player1.initNormalPosition((player2.getService() == Service.HIGH_SERVICE ? 'b'
			    : 'h'));
		    player3.initNormalPosition('b');
		    player4.initNormalPosition('h');
		} else if ((player3.getService() == Service.LOW_SERVER)
			|| (player3.getService() == Service.HIGH_SERVER)) {
		    player3.setService((player3.getService() == Service.HIGH_SERVER ? Service.LOW_SERVICE
			    : Service.HIGH_SERVICE));
		    player3.initServicePosition();
		    player4.initNormalPosition((player3.getService() == Service.HIGH_SERVICE ? 'b'
			    : 'h'));
		    player1.initNormalPosition('h');
		    player2.initNormalPosition('b');
		} else if ((player4.getService() == Service.LOW_SERVER)
			|| (player4.getService() == Service.HIGH_SERVER)) {
		    player4.setService((player4.getService() == Service.HIGH_SERVER ? Service.LOW_SERVICE
			    : Service.HIGH_SERVICE));
		    player4.initServicePosition();
		    player3.initNormalPosition((player4.getService() == Service.HIGH_SERVICE ? 'b'
			    : 'h'));
		    player1.initNormalPosition('b');
		    player2.initNormalPosition('h');
		}
	    }
	}
    }

    public void keyPressed(int key) {
	System.out.println("GCP.java: 1" + key);
	if (key == KeyEvent.VK_UP) {
	    directionLabel.setText("up");
	    // if (!up)
	    // {
	    player.setSpeed(player.getSpeed().getX(),
		    (player.getSpeed().getY() == 0 ? -8 : 0));
	    up = true;
	    // }
	} else if (key == KeyEvent.VK_LEFT) {
	    directionLabel.setText("left");
	    // if (!left)
	    // {
	    player.setSpeed((player.getSpeed().getX() == 0 ? -8 : 0), player
		    .getSpeed().getY());
	    left = true;
	    // }
	} else if (key == KeyEvent.VK_RIGHT) {
	    directionLabel.setText("right");
	    // if (!right)
	    // {
	    player.setSpeed((player.getSpeed().getX() == 0 ? 8 : 0), player
		    .getSpeed().getY());
	    right = true;
	    // }
	} else if (key == KeyEvent.VK_DOWN) {
	    directionLabel.setText("down");
	    // if (!down)
	    // {
	    player.setSpeed(player.getSpeed().getX(),
		    (player.getSpeed().getY() == 0 ? 8 : 0));
	    down = true;
	    // }
	}
	if (key == KeyEvent.VK_S) {
	    // the racket moves but the hit is ignored when the key is released
	    player.setHitType(Player.HitType.KEY_PRESSED);
	}

	if (key == KeyEvent.VK_D) {
	    // the racket moves but the hit is ignored when the key is released
	    player.setHitType(Player.HitType.KEY_PRESSED);
	}
    }

    public void keyReleased(int key) {
	System.out.println("GCP.java: 2" + key);
	if (key == KeyEvent.VK_UP) {
	    player.setSpeed(player.getSpeed().getX(), 0);
	    // up = false;
	    // direction.setText(" ");
	} else if (key == KeyEvent.VK_LEFT) {
	    player.setSpeed(0, player.getSpeed().getY());
	    // left = false;
	    // direction.setText(" ");
	} else if (key == KeyEvent.VK_RIGHT) {
	    player.setSpeed(0, player.getSpeed().getY());
	    // right = false;
	    // direction.setText(" ");
	} else if (key == KeyEvent.VK_DOWN) {
	    player.setSpeed(player.getSpeed().getX(), 0);
	    // down = false;
	    // direction.setText(" ");
	}
	if (key == KeyEvent.VK_S) {
	    player.setHitType(Player.HitType.HIGH_HIT);
	}
	if (key == KeyEvent.VK_D) {
	    player.setHitType(Player.HitType.LOW_HIT);
	}
    }

    // public void keyTyped(KeyEvent evt){}

    public void updateUIObjects() {
	for (int i = 0; i < 4; i++) {
	    player = (Player) framePanel.getObjectList().get(i);
	    handleCollision();
	    playService();
	}
	player = (Player) framePanel.getObjectList().get(0);

	for (Iterator<ControlableObject> iter = framePanel.getObjectList()
		.iterator(); iter.hasNext();) {
	    ((ControlableObject) iter.next()).move();
	}
	playService();
    }

    public void refreshUI() {

	// Graphics g = canvas.getGraphics();
	// g.setColor(Color.green);
	// g.fillRect(0, 0, 600, 400);
	// g.clearRect(0, 0, 600, 400);
	handleTennisPoint();
	player.draw(framePanel.getGraphics());
	updateUIObjects();
	framePanel.repaint();

	ball.move();
	// ball.draw(canvas);
    }

    public void initializePoint() {
	throw new UnsupportedOperationException();
    }

    public void handleCollision() {
	// boolean hit = false;
	int newSpeedY;
	if ((player.getHitType() == Player.HitType.HIGH_HIT)
		|| (player.getHitType() == Player.HitType.LOW_HIT)) {
	    if (ball.getPosition().getZ() <= 15) {
		if (player.getHitLimits().contains(ball.getPosition().getX(),
			ball.getPosition().getY())) {
		    if (player.getHitType() == Player.HitType.LOW_HIT) {
			if (!service) {
			    newSpeedY = (int) (ball.getSpeed().getY() + (ball
				    .getPosition().getY()
				    - player.getHitLimits().getY() - 10));
			    ball.setSpeed(
				    -ball.getSpeed().getX()
					    + (player.getSide() == Player.Side.RIGHT ? -5
						    : 5), newSpeedY, 6); // ball.getSpeed().getZ()
			    // - 3
			    // ball.getSpeed().setX(-ball.getSpeed().getX());
			    // ball.getSpeed().setZ(0);
			} else {
			    newSpeedY = (int) ((ball.getPosition().getY()
				    - player.getHitLimits().getY() - 10));
			    ball.setSpeed(
				    player.getSide() == Player.Side.RIGHT ? -19
					    : 19, newSpeedY, 7); // (player.getService()
			    // ==
			    // Player.Service.HIGH_SERVICE
			    // ?
			    // 5:
			    // -5)
			    service = false;
			    player.setService(player.getService() == Player.Service.HIGH_SERVICE ? Player.Service.HIGH_SERVER
				    : Player.Service.LOW_SERVER);

			}
		    } else {
			if (!service) {
			    newSpeedY = (int) (ball.getSpeed().getY() + (ball
				    .getPosition().getY()
				    - player.getHitLimits().getY() - 10));
			    ball.setSpeed(
				    -ball.getSpeed().getX()
					    + (player.getSide() == Player.Side.RIGHT ? 3
						    : -3), newSpeedY, 9); // ball.getSpeed().getZ()
			    // + 3
			    // balle.getVitesse().setZ(9);
			} else {
			    newSpeedY = (int) ((ball.getPosition().getY()
				    - player.getHitLimits().getY() - 10));
			    ball.setSpeed(
				    this.player.getSide() == Player.Side.RIGHT ? -11
					    : 11, newSpeedY, 12); // (player.getService()
			    // ==
			    // Player.Service.HIGH_SERVICE
			    // ?
			    // 5:
			    // -5)
			    service = false;
			    player.setService(player.getService() == Player.Service.HIGH_SERVICE ? Player.Service.HIGH_SERVER
				    : Player.Service.LOW_SERVER);
			}
		    }
		    // hit = true;
		    ball.setOut(false);
		    ball.setRebound(0);
		    player.getTeam().setHit(true);
		}
	    }
	    player.setHitType(Player.HitType.NO_HIT);
	}
    }

    public void playService() {
	if ((player.getService() == Player.Service.LOW_SERVICE)
		|| (player.getService() == Player.Service.HIGH_SERVICE)) {
	    if ((player.getHitType() == Player.HitType.NO_HIT) && (!service)) {
		if (player.getSide() == Player.Side.LEFT) {
		    ball.setPosition(player.getPosition().getX() + 5, player
			    .getPosition().getY() + 11, 0);
		} else {
		    ball.setPosition(player.getPosition().getX(), player
			    .getPosition().getY() - 6, 0);
		}
		ball.setSpeed(0, 0, 0);
	    } else if ((player.getHitType() == Player.HitType.KEY_PRESSED)
		    && !service) {
		service = true;
		ball.setSpeed(0, 0, 10);
	    }
	}

    }

    public void keyPressed(KeyEvent e) {
	System.out.println("GCP.java: keyPressed" + e.getKeyCode());
	keyPressed(e.getKeyCode());

    }

    public void keyReleased(KeyEvent e) {
	System.out.println("GCP.java: keyReleased" + e.getKeyCode());
	keyReleased(e.getKeyCode());

    }

    public void keyTyped(KeyEvent e) {
	System.out.println("GCP.java: keyTyped" + e.getKeyCode());
	// not used
    }

    public ServerSocket getServerSocket() {
	return serverSocket;
    }
}
