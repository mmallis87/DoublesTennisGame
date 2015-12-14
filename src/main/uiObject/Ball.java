package main.uiObject;

import java.awt.Color;
import java.awt.Graphics;

import util.Coordinates;

public class Ball extends Objet3D {

    static final int WIDTH = 600;
    static final int HEIGHT = 400;
    private int rebound = 0;
    private boolean out = false;

    public Ball() {
	super();
    }

    public Ball(Coordinates position, Coordinates speed) {
	super(position, speed);
    }

    public void setRebound(int rebound) {
	this.rebound = rebound;
    }

    public int getRebound() {
	return rebound;
    }

    public void setOut(boolean out) {
	this.out = out;
    }

    public boolean isOut() {
	return out;
    }

    public void move() {
	int x = this.getPosition().getX();
	int y = this.getPosition().getY();
	int z = this.getPosition().getZ();
	int Sx = this.getSpeed().getX();
	int Sy = this.getSpeed().getY();
	int Sz = this.getSpeed().getZ();
	int newX = x + Sx;
	int newY = y + Sy;
	int newZ = z + Sz;

	if (!((newZ == 0) & (Sz == 0))) {
	    Sz = Sz - 1;
	}

	if (((newX + newZ / 2 + 7) < WIDTH) & (newX - newZ / 4 >= 0)
		& ((newY + newZ / 2 + 7) < HEIGHT) & (newY - newZ / 4 >= 0)) {
	    this.setPosition(newX, newY, newZ);
	    this.setSpeed(Sx, Sy, Sz);
	} else if ((newX + newZ / 2 + 7) >= WIDTH) {
	    newX = 1200 - newX - newZ - 10;
	    Sx = -Sx;
	    this.setPosition(newX, newY, newZ);
	    this.setSpeed(Sx, Sy, Sz);
	} else if (newX - newZ / 4 < 0) {
	    newX = -newX + newZ / 4;
	    Sx = -Sx;
	    this.setPosition(newX, newY, newZ);
	    this.setSpeed(Sx, Sy, Sz);
	} else if ((newY + newZ / 2 + 7) >= HEIGHT) {
	    newY = 800 - newY - newZ - 10;
	    Sy = -Sy;
	    this.setPosition(newX, newY, newZ);
	    this.setSpeed(Sx, Sy, Sz);
	} else if (newY - newZ / 4 < 0) {
	    newY = -newY + newZ / 4;
	    Sy = -Sy;
	    this.setPosition(newX, newY, newZ);
	    this.setSpeed(Sx, Sy, Sz);
	}
	if (newZ <= 0) {
	    newZ = -newZ;
	    if (-Sz - 3 >= 0) {
		Sz = -Sz - 3;
	    } else {
		Sz = 0;
	    }

	    setPosition(newX, newY, newZ);
	    setSpeed(Sx, Sy, Sz);
	}
    }

    public void draw(Graphics g) {
	g.setColor(Color.yellow);
	g.fillOval(this.getPosition().getX() - this.getPosition().getZ() / 4,
		this.getPosition().getY() - this.getPosition().getZ() / 4,
		7 + this.getPosition().getZ() / 2, 7 + this.getPosition()
			.getZ() / 2);
	g.setColor(Color.black);
	g.drawOval(this.getPosition().getX() - this.getPosition().getZ() / 4,
		this.getPosition().getY() - this.getPosition().getZ() / 4,
		7 + this.getPosition().getZ() / 2, 7 + this.getPosition()
			.getZ() / 2);
    }
}