package com.battleBoard.battleBoardGame.battleStates;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.framework.implementation.Graphics;

import android.view.MotionEvent;

public class TickingState implements IBattleState {

	private GameScreen gameScreen = null;
	private Graphics graphics = null;

	public TickingState(GameScreen gameScreen, Graphics graphics) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
