package main.tennisGamePart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TennisMatch {
    private List<TennisSet> tennisSetList = new ArrayList<TennisSet>();
    private Team team1;
    private Team team2;

    public TennisMatch(Team team1, Team team2) {
	this.team1 = team1;
	this.team2 = team2;
    }

    public TennisMatch(TennisSet set) {
	addTennisSet(set);
    }

    public List<TennisSet> getTennisSetList() {
	return tennisSetList;
    }

    public Team getTeam1() {
	return team1;
    }

    public Team getTeam2() {
	return team2;
    }

    public void addTennisSet(TennisSet set) {
	tennisSetList.add(set);
    }

    public void setTennisSetList(List<TennisSet> tennisSetList) {
	this.tennisSetList = tennisSetList;
    }

    public void setTeam1(Team team1) {
	this.team1 = team1;
    }

    public void setTeam2(Team team2) {
	this.team2 = team2;
    }

    public TennisSet getLastTennisSet() {
	return tennisSetList.get(tennisSetList.size() - 1);
    }

    public int getTeam1NbTennisSets() {
	int nbSets = 0;
	for (Iterator<TennisSet> iter = tennisSetList.iterator(); iter
		.hasNext();) {
	    if (((TennisSet) iter.next()).getTeam1NbTennisGames() == 6) {
		nbSets++;
	    }
	}
	return nbSets;
    }

    public int getTeam2NbTennisSets() {
	int nbSets = 0;
	for (Iterator<TennisSet> iter = tennisSetList.iterator(); iter
		.hasNext();) {
	    if (((TennisSet) iter.next()).getTeam2NbTennisGames() == 6) {
		nbSets++;
	    }
	}
	return nbSets;
    }
}