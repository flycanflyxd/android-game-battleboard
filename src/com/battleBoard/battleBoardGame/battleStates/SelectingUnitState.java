package com.battleBoard.battleBoardGame.battleStates;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

public class SelectingUnitState implements IBattleState {

	private GameScreen gameScreen = null;
	private Graphics graphics = null;
	private Unit selectingUnit = null;

	public SelectingUnitState(GameScreen gameScreen, Graphics graphics, Unit selectingUnit) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;
		this.selectingUnit = selectingUnit;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		Point blockPosition = gameScreen.screenToBlockPosition(event.getX(), event.getY());

		if (!selectingUnit.getBlockPosition().equals(blockPosition)) {

			// abilityButtons.removeAllViews();
			gameScreen.setState(new NormalState(gameScreen, graphics));
			gameScreen.onTouchEvent(event);
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		Matrix matrix = new Matrix();
		gameScreen.getBoard().draw();

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser());

		matrix.reset();
		matrix.setScale(gameScreen.getBlockWidth() / Assets.selectCircle.getWidth(), gameScreen.getBlockWidth() / Assets.selectCircle.getHeight());
		PointF circlePosition = gameScreen.blockToScreenPosition(selectingUnit.getBlockPosition());
		matrix.postTranslate(circlePosition.x, circlePosition.y);
		graphics.drawBitmap(Assets.selectCircle, matrix, null);

	}

}
