package main.tennisGamePart;

import main.uiObject.Player;


public class Team {
    private Player player1;
    private Player player2;
    private boolean hit;

    public Team(Player player1, Player player2) {
	this.player1 = player1;
	player1.setTeam(this);
	this.player2 = player2;
	player2.setTeam(this);
    }

    public boolean isHit() {
	return hit;
    }

    public void setHit(boolean hit) {
	this.hit = hit;
    }

    public Player getPlayer1() {
	return player1;
    }

    public Player getPlayer2() {
	return player2;
    }

    public void setPlayer1(Player player1) {
	this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
	this.player2 = player2;
    }
}