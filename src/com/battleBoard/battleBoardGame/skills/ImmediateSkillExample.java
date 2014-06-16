package com.battleBoard.battleBoardGame.skills;

public class ImmediateSkillExample extends Skill {
	public ImmediateSkillExample() {
		name = "immediate";
		castType = CastType.immediate;
		mpCost = 110.0f;
		castDistance = 2.0f;
		effectiveRange = 2.0f;
		damage = 40.0f;
		canTargetFriend = false;
		canTargetEnemy = true;
	}
}
