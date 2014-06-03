package com.battleBoard.battleBoardGame.skills;

public class TargetUnitSkillExample extends Skill {
	public TargetUnitSkillExample() {
		name = "TargetUnit";
		castType = CastType.targetUnit;
		mpCost = 10.0f;
		castDistance = 3.0f;
		effectiveRange = 1.0f;
		damage = 30.0f;
		canTargetFriend = false;
		canTargetEnemy = true;
	}
}
