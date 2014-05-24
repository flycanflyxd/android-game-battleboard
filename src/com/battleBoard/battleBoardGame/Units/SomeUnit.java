package com.battleBoard.battleBoardGame.Units;

import com.battleBoard.battleBoardGame.Assets;

import android.graphics.Point;

public class SomeUnit extends Unit{

	public SomeUnit(int x, int y) {
		super(x, y);
		image = Assets.characterImg;
		validMoveDirections.add(new Point(1, 0));
		validMoveDirections.add(new Point(0,1));
		validMoveDirections.add(new Point(-1, 0));
		validMoveDirections.add(new Point(0, -1));
	}
}
