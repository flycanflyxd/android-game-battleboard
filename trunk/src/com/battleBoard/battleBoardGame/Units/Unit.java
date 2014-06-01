package com.battleBoard.battleBoardGame.Units;

import java.util.ArrayList;
import java.util.List;

import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.Sprite;
import com.battleBoard.battleBoardGame.skills.Skill;

import android.graphics.Point;

public abstract class Unit extends Sprite {
	protected List<Point> validMoveDirections = new ArrayList<Point>();
	protected float hp = 0.0f;
	protected List<Skill> skills = new ArrayList<Skill>();
	protected Player owner = null;

	public List<Skill> getSkills() {
		return skills;
	}

	public Unit(Point blockPosition, Player owner) {
		super(blockPosition.x, blockPosition.y);
		this.owner = owner;
	}

	public Unit(int x, int y) {
		super(x, y);
	}

	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> validMoves = new ArrayList<Point>();
		for (Point whichDirection : validMoveDirections) {
			validMoves.add(new Point(getX() + whichDirection.x, getY() + whichDirection.y));
		}
		return validMoves;
	}

	public void damage(float damage) {
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
