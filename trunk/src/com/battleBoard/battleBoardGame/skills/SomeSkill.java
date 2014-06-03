package com.battleBoard.battleBoardGame.skills;

public class SomeSkill extends Skill {

	public SomeSkill() {
		name = "magic";
		castType = CastType.targetPoint;
		targetType = TargetType.onlyEnemy;
		mpCost = 10.0f;
		castDistance = 2;
		damage = 40.0f;
	}
}
