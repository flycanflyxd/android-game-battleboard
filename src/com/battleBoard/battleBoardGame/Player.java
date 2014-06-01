package com.battleBoard.battleBoardGame;

import java.util.ArrayList;
import java.util.List;

import com.battleBoard.battleBoardGame.Units.Unit;

public class Player {

	private ArrayList<Unit> units = null;

	public Player() {
		units = new ArrayList<Unit>();
	}

	public void addUnit(Unit unit) {
		units.add(unit);
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
}
