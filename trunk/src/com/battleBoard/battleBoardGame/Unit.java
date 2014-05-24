package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;
import android.graphics.PointF;

public abstract class Unit extends Sprite {
	private PointF screenPosition = new PointF(0.0f, 0.0f);
	private Bitmap image;
	
	public Unit(Bitmap image, int x, int y) {
		super(x, y);
		this.setImage(image);
	}

	public PointF getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(PointF screenPosition) {
		this.screenPosition = screenPosition;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}
