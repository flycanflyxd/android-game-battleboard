package com.battleBoard.battleBoardGame.skills;

public class TargetPointSkillExample extends Skill {
	public TargetPointSkillExample() {
		name = "TargetPoint";
		castType = CastType.targetPoint;
		mpCost = 10.0f;
		castDistance = 2.0f;
		effectiveRange = 1.0f;
		damage = 40.0f;
		canTargetFriend = false;
		canTargetEnemy = true;
	}
}
