package main.uiObject;

import java.awt.*;

import main.tennisGamePart.Team;
import util.Coordinates;

public class Player extends Objet2D {

    public static enum HitType {
	NO_HIT, LOW_HIT, HIGH_HIT, KEY_PRESSED
    }

    public static enum Side {
	LEFT, RIGHT
    }

    public static enum Service {
	NOT_ON_SERVICE, HIGH_SERVICE, LOW_SERVICE, HIGH_SERVER, LOW_SERVER
    }

    int WIDTH = 600;
    int HEIGHT = 400;

    private HitType hitType = HitType.NO_HIT;
    private Side side;
    private Rectangle hitLimits; // rectangle where the hit is
    private Service service;
    private Team team;
    private int numPlayer;

    public void setNumPlayer(int num) {
	this.numPlayer = num;
    }

    public int getNumPlayer() {
	return numPlayer;
    }

    public void setTeam(Team team) {
	this.team = team;
    }

    public Team getTeam() {
	return team;
    }

    public Player() {
	super();
	hitLimits = new Rectangle(20, 25);

	// this.getCadre().setPreferredSize(new Dimension(12,20));
    }

    public Player(Coordinates position, Coordinates speed) {
	super(position, speed);
    }

    public Player(Side side) {
	this.side = side;
	hitLimits = new Rectangle(20, 25);
    }

    public Rectangle getHitLimits() {
	return hitLimits;
    }

    public void setService(Service service) {
	this.service = service;
    }

    public Service getService() {
	return service;
    }

    public void setHitType(HitType type) {
	this.hitType = type;
    }

    public HitType getHitType() {
	return hitType;
    }

    public void setSide(Side side) {
	this.side = side;

    }

    public Side getSide() {
	return side;
    }

    public void initNormalPosition(char position) { // position == 'h' or 'b'
	setPosition((side == Side.LEFT ? 100 : 450), (position == 'h' ? 150
		: 250));
    }

    public void initServicePosition() {
	setPosition((side == Side.LEFT ? 50 : 540),
		(this.service == Service.HIGH_SERVICE ? 150 : 250));
    }

    public void move() {
	int x = getPosition().getX();
	int y = getPosition().getY();
	int Sx = getSpeed().getX();
	int Sy = getSpeed().getY();
	int newX = x + Sx;
	int newY = y + Sy;
	if (this.service == Service.NOT_ON_SERVICE) {
	    if (((newX + 10) < (side == Side.RIGHT ? WIDTH : 300))
		    && (newX > (side == Side.RIGHT ? 300 : 0))
		    && ((newY + (side == Side.RIGHT ? 10 : 20)) < HEIGHT)
		    && (newY - (side == Side.RIGHT ? 10 : 0) > 0)) {
		setPosition(newX, newY);
		hitLimits.setLocation(newX - 10, newY
			+ (side == Side.RIGHT ? -18 : 3));
	    }
	} else if (service == Service.HIGH_SERVICE) {
	    if (((newX) <= (side == Side.RIGHT ? 540 : 50))
		    && (newX > (side == Side.RIGHT ? 539 : 49))
		    && ((newY + 10) < 200) & (newY > 90)) {
		setPosition(newX, newY);
		hitLimits.setLocation(newX - 10, newY
			+ (side == Side.RIGHT ? -18 : 3));
	    }
	} else if (service == Service.LOW_SERVICE) {
	    if (((newX) <= (side == Side.RIGHT ? 540 : 50))
		    && (newX > (side == Side.RIGHT ? 539 : 49))
		    && ((newY + 10) < 310) & (newY > 200)) {
		setPosition(newX, newY);
		hitLimits.setLocation(newX - 10, newY
			+ (side == Side.RIGHT ? -18 : 3));
	    }
	}

	setPosition(newX, newY);
	setSpeed(Sx, Sy);

	getFramePanel().setLocation(this.getPosition().getX(),
		getPosition().getY());
	getFramePanel().repaint();
    }

    public void draw(Graphics g) {

	// Graphics g = getFramePanel.getGraphics();
	if (side == Side.LEFT) {
	    g.setColor(Color.red);
	} else {
	    g.setColor(Color.blue);
	}
	// g.fillOval(2,0,10,10);
	g.fillOval(getPosition().getX(), getPosition().getY(), 10, 10);
	g.setColor(Color.black);
	if (hitType == HitType.NO_HIT) {
	    // g.drawLine(7, 10, 7, 20);
	    if (side == Side.LEFT) {
		g.drawLine(getPosition().getX() + 5, getPosition().getY() + 10,
			getPosition().getX() + 5, getPosition().getY() + 20);
	    } else {
		g.drawLine(getPosition().getX() + 5, getPosition().getY(),
			getPosition().getX() + 5, getPosition().getY() - 10);
	    }
	} else if (hitType == HitType.KEY_PRESSED) {
	    // g.drawLine(7, 10, 0, 17);
	    if (side == Side.LEFT) {
		g.drawLine(getPosition().getX() + 5, getPosition().getY() + 10,
			getPosition().getX() - 2, getPosition().getY() + 17);
	    } else {
		g.drawLine(getPosition().getX() + 5, getPosition().getY(),
			getPosition().getX() + 12, getPosition().getY() - 7);
	    }
	}

    }
}