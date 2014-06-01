package com.battleBoard.battleBoardGame;

import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Matrix;
import android.graphics.Point;

public class Board {
	private Block[][] blocks = null;
	private Point size = null;
	float blockWidth = 0.0f;
	float startY = 0.0f;
	Graphics graphics;

	public Board(int width, int height, float blockWidth, float startY, Graphics graphics) {
		size = new Point(width, height);
		blocks = new Block[height][width];
		for (int whichRow = 0; whichRow < height; whichRow++) {
			for (int whichCol = 0; whichCol < width; whichCol++) {
				blocks[whichCol][whichRow] = new Block(whichCol, whichRow, Assets.blockImg, true);
			}
		}
		this.blockWidth = blockWidth;
		this.startY = startY;
		this.graphics = graphics;
	}

	public void draw() {
		Matrix matrix = new Matrix();
		for (int i = 0; i < getHeight(); ++i) {
			for (int j = 0; j < getWidth(); ++j) {
				matrix.reset();
				matrix.setScale(blockWidth / Assets.blockImg.getWidth(), blockWidth / Assets.blockImg.getHeight());
				matrix.postTranslate(blockWidth * i, startY + blockWidth * j);
				graphics.drawBitmap(Assets.blockImg, matrix, null);
			}
		}
	}

	public boolean isBlockEmpty(Point blockPosition) {
		Block block = blocks[blockPosition.x][blockPosition.y];
		return block.getUnit() == null && block.isWalkable();
	}
	
	public Block getBlock(int col, int row) {
		return blocks[col][row];
	}

	public Block getBlock(Point point) {
		return blocks[point.x][point.y];
	}

	public int getWidth() {
		return size.x;
	}

	public int getHeight() {
		return size.y;
	}

	public void UnitGetIn(Unit unit, Point blockPosition) {
		blocks[blockPosition.x][blockPosition.y].AddUnit(unit);
	}

	public void UnitGetOut(Point blockPosition) {
		blocks[blockPosition.x][blockPosition.y].removeUnit();
	}
}
