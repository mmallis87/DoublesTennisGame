package main.uiObject;

import main.FramePanel;

import javax.swing.*;

import util.Coordinates;

public abstract class Objet2D extends ControlableObject {
    protected Coordinates position;
    protected Coordinates speed;
    private FramePanel framePanel;

    public Objet2D() {
	super();
	position = new Coordinates();
	speed = new Coordinates();
	framePanel = new FramePanel();
    }

    public Objet2D(Coordinates position, Coordinates speed) {
	this.position = position;
	this.speed = speed;
    }

    public void setPosition(int x, int y) {
	position.setX(x);
	position.setY(y);
	position.setZ(0);
    }

    public void setSpeed(int x, int y) {
	speed.setX(x);
	speed.setY(y);
	speed.setZ(0);
    }

    public Coordinates getPosition() {
	return position;
    }

    public Coordinates getSpeed() {
	return speed;
    }

    public JPanel getFramePanel() {
	return this.framePanel;
    }
}