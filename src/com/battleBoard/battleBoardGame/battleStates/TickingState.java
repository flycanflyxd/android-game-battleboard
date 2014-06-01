package com.battleBoard.battleBoardGame.battleStates;

import java.util.List;
import java.util.Random;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.UnitAction;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.implementation.Graphics;

import android.view.MotionEvent;

public class TickingState implements IBattleState {

	private GameScreen gameScreen = null;
	private Graphics graphics = null;

	public TickingState(GameScreen gameScreen, Graphics graphics, UnitAction unitAction) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;

		if (unitAction.getType() == UnitAction.Type.move) {
			gameScreen.MoveUnit(unitAction.getUnit(), unitAction.getTargetPoint());
		}
		else if(unitAction.getType() == UnitAction.Type.castSkillTargetPoint) {
			Unit targetUnit = gameScreen.getBoard().getBlock(unitAction.getTargetPoint().x, unitAction.getTargetPoint().y).getUnit();
			if(targetUnit != null){
				gameScreen.DamageUnit(targetUnit, unitAction.getSkill().getDamage());
			}
		}

		for (Unit whichUnit : gameScreen.getEnemy().getUnits()) {
			List<ValidMove> moves = gameScreen.generateValidMoves(whichUnit);
			if (moves.size() > 0) {
				gameScreen.MoveUnit(whichUnit, moves.get((new Random()).nextInt(moves.size())).getBlockPosition());
				break;
			}
		}

	}

	@Override
	public void update(float deltaTime) {
		gameScreen.setState(new NormalState(gameScreen, graphics));
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		gameScreen.getBoard().draw();

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser());

	}

	@Override
	public void Dispose() {

	}
}
