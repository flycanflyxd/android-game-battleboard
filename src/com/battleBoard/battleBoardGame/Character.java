package com.battleBoard.battleBoardGame;

import android.view.MotionEvent;


public class Character extends Sprite {
	private int xStart = 0;
	private int yStart = 0;
	private int xEnd = 0;
	private int yEnd = 0;
	private int xMovedDis = 0;
	private int yMovedDis = 0;

	private final int oneBlock = Assets.blockImg.getWidth();
	private final int twoBlock = oneBlock * 2;
	private final int halfBlock = oneBlock / 2;
	private final int oneHalfBlock = (int) (oneBlock * 1.5);
	private final int leftBound = 80;
	private final int upBound = 50;

	public Character(int x, int y) {
		super(x, y);
	}

	public void update(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xStart = this.x + halfBlock;
			yStart = this.y + halfBlock;
			xMovedDis = 0;
			yMovedDis = 0;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			xMovedDis = ((int)event.getX() - xStart);
			yMovedDis = ((int)event.getY() - yStart);
			this.x = xStart + xMovedDis - halfBlock;
			this.y = yStart + yMovedDis - halfBlock;
			if (illegalMoving()) {
				this.x = xStart - halfBlock;
				this.y = yStart - halfBlock;
				xMovedDis = 0;
				yMovedDis = 0;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (xMovedDis != 0 || yMovedDis != 0) {
				this.x = xStart - halfBlock;
				this.y = yStart - halfBlock;
			}

			if (legalMoving()) {
				if (xMovedDis <= -halfBlock && xStart - oneHalfBlock >= leftBound) {
					this.x = xStart - oneHalfBlock;
				} else if (xMovedDis >= halfBlock
						&& xStart + halfBlock < leftBound + oneBlock * 5) {
					this.x = xStart + halfBlock;
				} else {
					this.x = xStart - halfBlock;
				}

				if (yMovedDis <= -halfBlock && yStart - oneHalfBlock >= upBound) {
					this.y = yStart - oneHalfBlock;
				} else if (yMovedDis >= halfBlock
						&& yStart + halfBlock < upBound + oneBlock * 5) {
					this.y = yStart + halfBlock;
				} else {
					this.y = yStart - halfBlock;
				}
				xEnd = this.x + halfBlock;
				yEnd = this.y + halfBlock;
				xMovedDis = 0;
				yMovedDis = 0;
			}
		}
	}

	public int getxMovedDis() {
		return xMovedDis;
	}

	public int getyMovedDis() {
		return yMovedDis;
	}

	public int getxStart() {
		return xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public int getxEnd() {
		return xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	private boolean legalMoving() {
		return (xMovedDis <= oneHalfBlock || xMovedDis >= -oneHalfBlock
				|| yMovedDis <= oneHalfBlock || yMovedDis >= -oneHalfBlock)
				&& (xMovedDis <= -halfBlock || xMovedDis >= halfBlock
						|| yMovedDis <= -halfBlock || yMovedDis >= halfBlock);
	}

	private boolean illegalMoving() {
		return xMovedDis > twoBlock || xMovedDis < -twoBlock
				|| yMovedDis > twoBlock || yMovedDis < -twoBlock;
	}
}
