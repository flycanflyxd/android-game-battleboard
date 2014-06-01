package com.battleBoard.battleBoardGame.battleStates;

import java.util.List;
import java.util.Random;

import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.UnitAction;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.Unit;

public class TickingState extends BattleState {

	public TickingState(IBattleScreen battleScreen, UnitAction unitAction) {
		super(battleScreen);

		if (unitAction.getType() == UnitAction.Type.move) {
			world.MoveUnit(unitAction.getUnit(), unitAction.getTargetPoint());
		} else if (unitAction.getType() == UnitAction.Type.castSkillTargetPoint) {
			Unit targetUnit = world.getBoard().getBlock(unitAction.getTargetPoint().x, unitAction.getTargetPoint().y).getUnit();
			if (targetUnit != null) {
				world.DamageUnit(targetUnit, unitAction.getSkill().getDamage());
			}
		}

		for (Unit whichUnit : world.getEnemy().getUnits()) {
			List<ValidMove> moves = world.generateValidMoves(whichUnit);
			if (moves.size() > 0) {
				world.MoveUnit(whichUnit, moves.get((new Random()).nextInt(moves.size())).getBlockPosition());
				break;
			}
		}

	}

	@Override
	public void update(float deltaTime) {
		battleScreen.setState(new NormalState(battleScreen));
	}

	@Override
	public void onTouchEvent(MotionEvent event) {

	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		world.getBoard().draw();

		battleScreen.drawPlayerUnits(world.getEnemy());
		battleScreen.drawPlayerUnits(world.getUser());

	}

	@Override
	public void Dispose() {

	}
}
