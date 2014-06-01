package com.battleBoard.battleBoardGame.skills;

public class Skill {
	private String name;
	protected int castDistance = 0;

	Skill(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCastDistance() {
		return castDistance;
	}
}
