package com.battleBoard.battleBoardGame.skills;

public abstract class Skill {

	public enum CastType {
		immediate, targetUnit, targetPoint, targetUnitOrPoint
	}

	public enum TargetType {
		any, onlyFriend, onlyEnemy
	}

	protected String name;
	protected CastType castType = CastType.targetPoint;
	protected TargetType targetType = TargetType.any;
	protected float mpCost = 0.0f;
	protected int castDistance = 0;
	protected float damage = 0.0f;

	public String getName() {
		return name;
	}

	public CastType getCastType() {
		return castType;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public float getMpCost() {
		return mpCost;
	}

	public int getCastDistance() {
		return castDistance;
	}

	public float getDamage() {
		return damage;
	}
}
