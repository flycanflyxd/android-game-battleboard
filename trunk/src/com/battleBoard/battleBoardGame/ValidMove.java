package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;
import android.graphics.Point;

public class ValidMove extends Sprite {

	public ValidMove(Bitmap image, Point blockPosition) {
		super(blockPosition);
		this.image = image;
	}
}
