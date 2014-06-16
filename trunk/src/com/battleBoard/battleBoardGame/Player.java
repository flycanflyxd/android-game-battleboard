package com.battleBoard.battleBoardGame;

import java.util.ArrayList;
import java.util.List;

import com.battleBoard.battleBoardGame.Units.Unit;

public class Player {

	private ArrayList<Unit> units = null;
	private float mpNow = 500.0f;
	private float mpMax = 500.0f;

	public float getMpMax() {
		return mpMax;
	}

	public void setMpMax(float mpMax) {
		this.mpMax = mpMax;
	}

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

	public float getMp() {
		return mpNow;
	}

	public void setMp(float mp) {
		this.mpNow = mp;
	}
}
