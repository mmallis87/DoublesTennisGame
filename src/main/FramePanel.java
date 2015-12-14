package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import main.tennisGamePart.TennisMatch;
import main.uiObject.ControlableObject;

public class FramePanel extends JPanel {
    private static final long serialVersionUID = -8480042655898560570L;
    private List<ControlableObject> objectList = new ArrayList<ControlableObject>();
    TennisMatch tennisMatch;
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;

    public FramePanel() {
	this.setPreferredSize(new Dimension(600, 400));
	this.setForeground(Color.GREEN);
    }

    public void addObject(ControlableObject o) {
	objectList.add(o);
	repaint();
    }

    public void removeObjet(ControlableObject o) {
	objectList.remove(o);
	repaint();
    }

    public void paint(Graphics g) {
	super.paint(g);

	// draw the tennis court
	g.setColor(Color.black);
	Image image = Toolkit.getDefaultToolkit().getImage("court.png");
	super.paintComponent(g);
	g.drawImage(image, 34, 70, 535, 260, this);

	/*
	 * g.drawRect(60, 90, 480, 220); // le cadre ext�rieur g.drawRect(60,
	 * 118, 480, 164); // les bandes de double g.drawRect(170, 118, 260,
	 * 164); // limite de service g.drawLine(300, 90, // 300, 310); // filet
	 * g.drawLine(170, 200, 430, 200); // ligne horizontale
	 */

	// draw the teams scores
	g.drawString("Team 1 : " + tennisMatch.getTeam1NbTennisSets() + " | "
		+ tennisMatch.getLastTennisSet().getTeam1NbTennisGames() + " | "
		+ tennisMatch.getLastTennisSet().getLastTennisGame().getTeam1Score(),
		10, 10);
	g.drawString("Team 2 : " + tennisMatch.getTeam2NbTennisSets() + " | "
		+ tennisMatch.getLastTennisSet().getTeam2NbTennisGames() + " | "
		+ tennisMatch.getLastTennisSet().getLastTennisGame().getTeam2Score(),
		10, 30);

	// draw the UI objects
	for (Iterator<ControlableObject> iter = objectList.iterator(); iter
		.hasNext();) {
	    ((ControlableObject) iter.next()).draw(g);
	}
    }

    public List<ControlableObject> getObjectList() {
	return objectList;
    }

    public void setGame(TennisMatch match) {
	this.tennisMatch = match;
    }

    /*
     * public void dessinerTerrain() {
     * 
     * Graphics g = this.getGraphics(); g.setColor(Color.green); g.fillRect(60,
     * 90, 480, 220); g.setColor(Color.black); g.drawRect(60, 90, 480, 220);
     * g.drawRect(60, 118, 480, 164); g.drawRect(170, 118, 260, 164);
     * g.drawLine(300, 90, 300, 310); g.drawLine(170, 200, 430, 200);
     * 
     * }
     */
}
