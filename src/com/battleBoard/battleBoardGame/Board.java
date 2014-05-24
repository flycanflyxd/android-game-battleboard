package com.battleBoard.battleBoardGame;

public class Board {	
	private final int bWidth = Assets.blockImg.getWidth();
	private final int bHeight = Assets.blockImg.getHeight();
	private final int leftBound = 80;
	private final int upBound = 50;
	private final int rowSize = 5;
	private final int colSize = 5;
	private Block[][] blocks = new Block[rowSize][colSize];
	
	public Board() {
	    for(int i = 0; i < rowSize; ++i) {
	    	for(int j = 0; j < colSize; ++j) {
	    		blocks[i][j] = new Block(leftBound + bHeight * i, upBound + bWidth * j);
	    	}
	    }
    }
	
	public Block getBlock(int i, int j)	{
		return blocks[i][j];
	}

	public int getRowSize()	{
		return rowSize;
	}

	public int getColSize()	{
		return colSize;
	}
}
