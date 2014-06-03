package com.battleBoard.battleBoardGame.Units;

import android.graphics.Point;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.skills.TargetPointSkillExample;

public class AnotherUnit extends Unit {

	public AnotherUnit(Point BlockPosition, Player owner) {
		super(BlockPosition, owner);

		hp = 140.0f;
		hpMax = 140.0f;
		damage = 10.0f;
		speed = 5.0f;

		image = Assets.unit1;

		skills.add(new TargetPointSkillExample());

	}
}
