package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class Sprite {
	private Point blockPosition;
	protected Bitmap image;

	public Sprite(Point blockPosition) {
		this.blockPosition = blockPosition;
	}

	public Sprite(int x, int y) {
		blockPosition = new Point();
		blockPosition.x = x;
		blockPosition.y = y;
	}

	public Bitmap getImage() {
		return image;
	}

	public Point getBlockPosition() {
		return blockPosition;
	}

	public int getX() {
		return blockPosition.x;
	}

	public int getY() {
		return blockPosition.y;
	}

	public void setBlockPosition(Point blockPosition) {
		this.blockPosition = blockPosition;
	}
}
