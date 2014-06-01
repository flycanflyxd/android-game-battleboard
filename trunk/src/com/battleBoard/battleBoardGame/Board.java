package com.battleBoard.battleBoardGame;

import com.battleBoard.battleBoardGame.Units.Unit;

import android.graphics.Point;

public class Board {
	private Block[][] blocks = null;
	private Point size = null;

	public Board(int width, int height) {
		size = new Point(width, height);
		blocks = new Block[height][width];
		for (int whichRow = 0; whichRow < height; whichRow++) {
			for (int whichCol = 0; whichCol < width; whichCol++){
				blocks[whichCol][whichRow] = new Block(whichCol, whichRow, Assets.blockImg, true);
			}
		}
	}

	public Block getBlock(int col, int row) {
		return blocks[col][row];
	}
	
	public Block getBlock(Point point) {
		return blocks[point.x][point.y];
	}
	
	public int getWidth(){
		return size.x;
	}
	
	public int getHeight(){
		return size.y;
	}
	
	public void UnitGetIn(Unit unit, Point blockPosition) {
		blocks[blockPosition.x][blockPosition.y].AddUnit(unit);
	}
	
	public void UnitGetOut(Point blockPosition) {
		blocks[blockPosition.x][blockPosition.y].removeUnit();
	}
}
