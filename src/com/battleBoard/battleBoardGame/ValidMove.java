package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;

public class ValidMove extends Sprite {
	private Bitmap image;
	
	public ValidMove(Bitmap image, int x, int y) {
		super(x, y);
		this.setImage(image);
	}
	
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}
