package main.tennisGamePart;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class TennisSet {
    private List<TennisGame> tennisGameList = new ArrayList<TennisGame>();

    public TennisSet(TennisGame tennisGame) {
	addTennisGame(tennisGame);
    }

    public List<TennisGame> getTennisGameList() {
	return tennisGameList;
    }

    public void addTennisGame(TennisGame tennisGame) {
	tennisGameList.add(tennisGame);
    }

    public TennisGame getLastTennisGame() {
	return tennisGameList.get(tennisGameList.size() - 1);
    }

    public int getTeam1NbTennisGames() {
	int nbGames = 0;
	for (Iterator<TennisGame> iter = tennisGameList.iterator(); iter.hasNext();) {
	    if (((TennisGame) iter.next()).getTeam1Score() == 50) {
		nbGames++;
	    }
	}
	return nbGames;

    }

    public int getTeam2NbTennisGames() {
	int nbGames = 0;
	for (Iterator<TennisGame> iter = tennisGameList.iterator(); iter.hasNext();) {
	    if (((TennisGame) iter.next()).getTeam2Score() == 50) {
		nbGames++;
	    }
	}
	return nbGames;

    }
}