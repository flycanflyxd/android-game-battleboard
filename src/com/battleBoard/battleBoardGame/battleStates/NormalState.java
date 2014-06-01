package com.battleBoard.battleBoardGame.battleStates;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

public class NormalState implements IBattleState{

	private GameScreen gameScreen = null;
	private Graphics graphics;
	
	public NormalState(GameScreen gameScreen, Graphics graphics){
		this.gameScreen = gameScreen;
		this.graphics = graphics;
	}
	
	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = gameScreen.screenToBlockPosition(event.getX(), event.getY());
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Unit unitHere = gameScreen.getBoard().getBlock(blockPosition).getUnit();
			if (unitHere != null) {
				gameScreen.setState(new DraggingUnitState(gameScreen, graphics, screenPosition, unitHere, gameScreen.generateValidMoves(unitHere)));
			}
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);
		
		gameScreen.getBoard().draw();

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser());
	}

}
