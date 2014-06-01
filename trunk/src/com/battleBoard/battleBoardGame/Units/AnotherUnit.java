package com.battleBoard.battleBoardGame.Units;

import android.graphics.Point;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Player;

public class AnotherUnit extends Unit {

	public AnotherUnit(Point BlockPosition, Player owner) {
		super(BlockPosition, owner);
		image = Assets.unit1;
		validMoveDirections.add(new Point(1, 1));
		validMoveDirections.add(new Point(-1, -1));
		validMoveDirections.add(new Point(-1, 1));
		validMoveDirections.add(new Point(1, -1));
		
		hp = 100.0f;
	}
}
