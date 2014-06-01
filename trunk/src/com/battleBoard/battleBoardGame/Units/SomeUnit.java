package com.battleBoard.battleBoardGame.Units;

import com.battleBoard.battleBoardGame.Assets;

import android.graphics.Point;

public class SomeUnit extends Unit {

	public SomeUnit(Point BlockPosition) {
		super(BlockPosition);
		image = Assets.characterImg;
		validMoveDirections.add(new Point(1, 0));
		validMoveDirections.add(new Point(0, 1));
		validMoveDirections.add(new Point(-1, 0));
		validMoveDirections.add(new Point(0, -1));
	}
}
