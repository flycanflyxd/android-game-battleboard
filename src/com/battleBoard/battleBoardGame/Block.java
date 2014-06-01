package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;

import com.battleBoard.battleBoardGame.Units.Unit;

public class Block extends Sprite {
	
	private Bitmap image = null;
	private boolean walkable = true;
	private Unit unitHere = null;
	
	public Block(int x, int y, Bitmap image, boolean walkable) {
		super(x, y);
		this.image = image;
		this.walkable = walkable;
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public void AddUnit(Unit unit){
		unitHere = unit;
	}
	
	public void removeUnit(){
		unitHere = null;
	}
	
	public Unit getUnit(){
		return unitHere;
	}

	public Bitmap getImage() {
		return image;
	}
}
