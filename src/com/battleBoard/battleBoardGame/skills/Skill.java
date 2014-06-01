package com.battleBoard.battleBoardGame.skills;

public class Skill {
	private String name;
	protected int castDistance = 0;
	protected float damage = 0.0f;

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

	public float getDamage() {
		return damage;
	}
}
