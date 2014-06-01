package com.battleBoard.battleBoardGame;

import android.graphics.Point;

import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.skills.Skill;

public class UnitAction {

	public enum Type {
		move, attack, castSkillTargetPoint
	}

	private Unit unit;
	private Type type;
	private Point targetPoint;
	private Skill skill;

	public UnitAction(Unit unit, Type type, Point targetPoint) {
		this.unit = unit;
		this.type = type;
		this.targetPoint = targetPoint;
	}

	public UnitAction(Unit unit, Type type, Point targetPoint, Skill skill) {
		this.unit = unit;
		this.type = type;
		this.targetPoint = targetPoint;
		this.skill = skill;
	}

	public Unit getUnit() {
		return unit;
	}

	public Type getType() {
		return type;
	}

	public Point getTargetPoint() {
		return targetPoint;
	}

	public Skill getSkill() {
		return skill;
	}

}
