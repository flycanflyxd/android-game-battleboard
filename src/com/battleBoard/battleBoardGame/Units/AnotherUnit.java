package com.battleBoard.battleBoardGame.Units;

import android.graphics.Point;

import com.battleBoard.battleBoardGame.Assets;

public class AnotherUnit extends Unit {

	public AnotherUnit(int x, int y) {
		super(x, y);
		image = Assets.unit1;
		validMoveDirections.add(new Point(1, 1));
		validMoveDirections.add(new Point(-1, -1));
		validMoveDirections.add(new Point(-1, 1));
		validMoveDirections.add(new Point(1, -1));
	}
}
