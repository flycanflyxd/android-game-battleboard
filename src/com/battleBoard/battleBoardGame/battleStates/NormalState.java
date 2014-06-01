package com.battleBoard.battleBoardGame.battleStates;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.Units.Unit;

public class NormalState extends BattleState {

	public NormalState(IBattleScreen battleScreen) {
		super(battleScreen);
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = battleScreen.screenToBlockPosition(event.getX(), event.getY());

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Unit unitHere = world.getBoard().getBlock(blockPosition).getUnit();
			if (unitHere != null) {
				battleScreen.setState(new DraggingUnitState(battleScreen, screenPosition, unitHere, world.generateValidMoves(unitHere)));
			}
		}
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
