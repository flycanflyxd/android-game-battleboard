package com.battleBoard.battleBoardGame;

import java.util.ArrayList;

public class Player {
	private ArrayList<Unit> units;
	
	public Player() {
		units = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}
}
