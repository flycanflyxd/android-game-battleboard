package com.battleBoard.battleBoardGame.Units;

import java.util.ArrayList;
import java.util.List;

import com.battleBoard.battleBoardGame.Sprite;
import com.battleBoard.battleBoardGame.skills.Skill;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class Unit extends Sprite {
	protected Bitmap image = null;
	protected List<Point> validMoveDirections = new ArrayList<Point>();
	protected float hp = 0.0f;
	protected List<Skill> skills = new ArrayList<Skill>();

	public Unit(Point blockPosition) {
		super(blockPosition.x, blockPosition.y);
	}
	
	public Unit(int x, int y) {
		super(x, y);
	}

	public Bitmap getImage() {
		return image;
	}

	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> validMoves = new ArrayList<Point>();
		for (Point whichDirection : validMoveDirections) {
			validMoves.add(new Point(getX() + whichDirection.x, getY() + whichDirection.y));
		}
		return validMoves;
	}
}
