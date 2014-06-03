package com.battleBoard.battleBoardGame.Units;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.Sprite;
import com.battleBoard.battleBoardGame.skills.Skill;

public abstract class Unit extends Sprite {

	protected float hp = 0.0f;
	protected float hpMax = 0.0f;
	protected float damage = 0.0f;
	protected float speed = 0.0f;
	protected List<Skill> skills = new ArrayList<Skill>();

	private List<Point> validMoveDirections = new ArrayList<Point>();
	private Player owner = null;
	private Paint hpBarPaint = new Paint();
	private RectF hpBarRectF = new RectF();

	public List<Skill> getSkills() {
		return skills;
	}

	public Unit(Point blockPosition, Player owner) {
		super(blockPosition.x, blockPosition.y);
		this.owner = owner;
		validMoveDirections.add(new Point(1, 0));
		validMoveDirections.add(new Point(0, 1));
		validMoveDirections.add(new Point(-1, 0));
		validMoveDirections.add(new Point(0, -1));
	}

	@Override
	public Bitmap getImage() {
		Bitmap imageToDraw = image.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(imageToDraw);
		final float width = (float) image.getWidth();
		final float height = (float) ((float) image.getHeight() * 0.1);
		hpBarPaint.setStyle(Paint.Style.FILL);
		hpBarPaint.setColor(Color.GREEN);
		hpBarRectF.set(0.0f, 0.0f, width * (hp / hpMax), height);
		canvas.drawRect(hpBarRectF, hpBarPaint);
		hpBarPaint.setColor(Color.BLUE);
		hpBarPaint.setStyle(Paint.Style.STROKE);
		hpBarRectF.set(0.0f, 0.0f, width, height);
		canvas.drawRect(hpBarRectF, hpBarPaint);
		return imageToDraw;
	}

	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> validMoves = new ArrayList<Point>();
		for (Point whichDirection : validMoveDirections) {
			validMoves.add(new Point(getX() + whichDirection.x, getY() + whichDirection.y));
		}
		return validMoves;
	}

	public void damaged(float damage) {
		hp -= damage;
		if (damage < 0.0f) {
			hp = 0.0f;
		}
	}

	public boolean isDead() {
		return hp <= 0.0f;
	}

	public Player getOwner() {
		return owner;
	}
}
