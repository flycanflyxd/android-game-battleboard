package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;
import android.graphics.Point;

public class SomeUnit extends Unit{

	public SomeUnit(Bitmap image, int x, int y) {
		super(image, x, y);
		validMoveDirections.add(new Point(1, 0));
		validMoveDirections.add(new Point(0,1));
		validMoveDirections.add(new Point(-1, 0));
		validMoveDirections.add(new Point(0, -1));
	}
}
