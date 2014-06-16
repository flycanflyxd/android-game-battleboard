package com.battleBoard.battleBoardGame.skills;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Point;
import android.graphics.PointF;

import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.World;
import com.battleBoard.battleBoardGame.Units.Unit;

public abstract class Skill {

	public enum CastType {
		immediate, targetUnit, targetPoint
	}

	protected String name;
	protected CastType castType = CastType.targetPoint;
	protected float mpCost = 0.0f;
	protected float castDistance = 0.0f;
	protected float effectiveRange = 0.0f;
	protected float damage = 0.0f;

	protected boolean canTargetFriend = true;
	protected boolean canTargetEnemy = true;

	public void getValidPoints(List<Point> points, Unit caster, World world) {
		if (castType == Skill.CastType.immediate) {
			points.add(caster.getBlockPosition());
		} else if (castType == Skill.CastType.targetPoint) {
			float distance = castDistance;
			for (int whichRow = 0; whichRow < world.getBoard().getHeight(); whichRow++) {
				for (int whichCol = 0; whichCol < world.getBoard().getWidth(); whichCol++) {
					if (PointF.length(whichCol - caster.getX(), whichRow - caster.getY()) <= distance) {
						points.add(new Point(whichCol, whichRow));
					}
				}
			}
		} else if (castType == Skill.CastType.targetUnit) {
			List<Player> players = new LinkedList<Player>();
			if (canTargetEnemy) {
				players.add(world.getEnemy());
			}
			if (canTargetFriend) {
				players.add(world.getUser());
			}
			float distance = castDistance;
			for (Player whichPlayer : players) {
				for (Unit whichUnit : whichPlayer.getUnits()) {
					Point blockPosition = whichUnit.getBlockPosition();
					if (PointF.length(blockPosition.x - caster.getX(), blockPosition.y - caster.getY()) <= distance) {
						points.add(blockPosition);
					}
				}
			}
		}
	}
	
	public void doEffect(Unit caster, Point targetPoint, World world) {
		float palyerMpNow = caster.getOwner().getMp();
		caster.getOwner().setMp(palyerMpNow - mpCost);
		if(canTargetEnemy) {
			for (int whichRow = 0; whichRow < world.getBoard().getHeight(); whichRow++) {
				for (int whichCol = 0; whichCol < world.getBoard().getWidth(); whichCol++) {
					if (PointF.length(whichCol - targetPoint.x, whichRow - targetPoint.y) <= effectiveRange) {
						Unit targetUnit = world.getBoard().getBlock(whichCol, whichRow).getUnit();
						if (targetUnit != null && targetUnit.getOwner() == world.getEnemy()) {
							world.DamageUnit(targetUnit, damage);
						}
					}
				}
			}
		}
		else if(canTargetFriend) {
			for (int whichRow = 0; whichRow < world.getBoard().getHeight(); whichRow++) {
				for (int whichCol = 0; whichCol < world.getBoard().getWidth(); whichCol++) {
					if (PointF.length(whichCol - targetPoint.x, whichRow - targetPoint.y) <= effectiveRange) {
						Unit targetUnit = world.getBoard().getBlock(whichCol, whichRow).getUnit();
						if (targetUnit != null && targetUnit.getOwner() == world.getUser()) {
							world.DamageUnit(targetUnit, damage);
						}
					}
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public CastType getCastType() {
		return castType;
	}

	public float getMpCost() {
		return mpCost;
	}

	public float getCastDistance() {
		return castDistance;
	}
	
	public float getEffectiveRange() {
		return effectiveRange;
	}

	public float getDamage() {
		return damage;
	}

	public boolean CanTargetFriend() {
		return canTargetFriend;
	}

	public boolean CanTargetEnemy() {
		return canTargetEnemy;
	}
}
