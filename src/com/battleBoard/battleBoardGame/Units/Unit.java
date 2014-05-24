package com.battleBoard.battleBoardGame.Units;

import java.util.ArrayList;

import com.battleBoard.battleBoardGame.Sprite;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

public abstract class Unit extends Sprite {
	private PointF screenPosition = new PointF(0.0f, 0.0f);
	protected Bitmap image = null;
	protected ArrayList<Point> validMoveDirections = new ArrayList<Point>();
	
	public Unit(int x, int y) {
		super(x, y);
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

	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> validMoves = new ArrayList<Point>();
		for(Point whichDirection : validMoveDirections) {
			validMoves.add(new Point(getX() + whichDirection.x, getY() + whichDirection.y));
		}
		return validMoves;
	}
}
