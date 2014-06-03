package com.battleBoard.battleBoardGame.Units;

import android.graphics.Point;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.skills.SomeSkill;

public class SomeUnit extends Unit {

	public SomeUnit(Point BlockPosition, Player owner) {
		super(BlockPosition, owner);

		hp = 100.0f;
		hpMax = 100.0f;
		damage = 10.0f;
		speed = 5.0f;

		image = Assets.characterImg;

		skills.add(new SomeSkill());
		skills.add(new SomeSkill());
	}
}
