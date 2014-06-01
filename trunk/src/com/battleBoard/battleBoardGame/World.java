package com.battleBoard.battleBoardGame;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.battleBoard.battleBoardGame.Units.Unit;

public class World {

	private IBattleScreen battleScreen = null;
	private Board board = null;
	private Player user = null;
	private Player enemy = null;

	Paint paint = null;
	
	public World(IBattleScreen battleScreen, Board board, Player user, Player enemy) {
		this.battleScreen = battleScreen;
		this.board = board;
		this.user = user;
		this.enemy = enemy;
		
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(22);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}

	public Board getBoard() {
		return board;
	}

	public Player getUser() {
		return user;
	}

	public Player getEnemy() {
		return enemy;
	}

	public void MoveUnit(Unit unit, Point newBlockPosition) {
		board.UnitGetOut(unit.getBlockPosition());
		board.UnitGetIn(unit, newBlockPosition);
		unit.setBlockPosition(newBlockPosition);
	}

	public void DamageUnit(Unit unitUnderDamage, float damageAmount) {
		unitUnderDamage.damage(damageAmount);
		if (unitUnderDamage.isDead()) {
			unitUnderDamage.getOwner().removeUnit(unitUnderDamage);
			board.getBlock(unitUnderDamage.getBlockPosition()).removeUnit();
		}
		
		PointF screenPosition = battleScreen.blockToScreenPosition(unitUnderDamage.getBlockPosition());
		battleScreen.getGraphics().drawText(String.valueOf(damageAmount), screenPosition.x, screenPosition.y, paint);
	}

	public List<ValidMove> generateValidMoves(Unit whichUnit) {
		List<ValidMove> answer = new ArrayList<ValidMove>();
		for (Point whichMove : whichUnit.getValidMoves()) {
			if (ValidBlockPosition(whichMove)) {
				if (board.isBlockEmpty(whichMove)) {
					answer.add(new ValidMove(Assets.magic, whichMove.x, whichMove.y));
				}
			}
		}
		return answer;
	}

	private boolean ValidBlockPosition(Point blockPosition) {
		return blockPosition.x >= 0 && blockPosition.x < board.getWidth() && blockPosition.y >= 0 && blockPosition.y < board.getHeight();
	}
}
