package com.battleBoard.battleBoardGame;

import android.graphics.Point;
import android.graphics.PointF;

public class Character extends Sprite {
	private Point blockPosition = new Point(0, 0);
	private PointF screenPosition = new PointF(0.0f, 0.0f);

	public Character(int x, int y) {
		super(x, y);
	}

	public Point getBlockPosition() {
		return blockPosition;
	}

	public void setBlockPosition(Point blockPosition) {
		this.blockPosition = blockPosition;
	}

	public PointF getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(PointF screenPosition) {
		this.screenPosition = screenPosition;
	}
}
