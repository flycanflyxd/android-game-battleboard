package com.battleBoard.battleBoardGame;

import android.graphics.Point;

public abstract class Sprite {
	private Point blockPosition;
	
	public Sprite(int x, int y)	{
		blockPosition = new Point();
		blockPosition.x = x;
		blockPosition.y = y;
	}
	
	public Point getBlockPosition() {
		return blockPosition;
	}
	
	public void setBlockPosition(Point blockPosition) {
		this.blockPosition = blockPosition;
	}
}
