package com.battleBoard.battleBoardGame;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

public abstract class Unit extends Sprite {
	private PointF screenPosition = new PointF(0.0f, 0.0f);
	private Bitmap image;
	protected ArrayList<Point> validMoveDirections;
	
	public Unit(Bitmap image, int x, int y) {
		super(x, y);
		this.image = image;
		validMoveDirections = new ArrayList<Point>();
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
