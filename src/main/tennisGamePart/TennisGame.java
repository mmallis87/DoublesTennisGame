package main.tennisGamePart;

public class TennisGame {
    private int team1Score = 0;
    private int team2Score = 0;

    public int getTeam1Score() {
	return team1Score;
    }

    public int getTeam2Score() {
	return team2Score;
    }

    public void setTeam1Score(int score) {
	team1Score = score;
    }

    public void setTeam2Score(int score) {
	team2Score = score;
    }

    public void incrementTeam1Score() {
	if (team1Score < 30)
	    team1Score += 15;
	else
	    team1Score += 10;
    }

    public void incrementTeam2Score() {
	if (team2Score < 30)
	    team2Score += 15;
	else
	    team2Score += 10;
    }
}