package com.battleBoard.battleBoardGame.battleStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.implementation.Graphics;

public class DraggingUnitState implements IBattleState {

	private GameScreen gameScreen = null;
	private Graphics graphics = null;
	private Unit draggingUnit = null;
	private PointF draggingScreenPosition = new PointF(0.0f, 0.0f);
	private List<ValidMove> validMoves = null;

	public DraggingUnitState(GameScreen gameScreen, Graphics graphics, PointF touchScreenPosition, Unit touchUnit, List<ValidMove> validmoves) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;
		this.draggingScreenPosition = touchScreenPosition;
		this.draggingUnit = touchUnit;
		this.validMoves = validmoves;

	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = gameScreen.screenToBlockPosition(event.getX(), event.getY());

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (draggingUnit.getBlockPosition().equals(blockPosition)) {
				gameScreen.setState(new SelectingUnitState(gameScreen, graphics, draggingUnit));
			} else {
				boolean collide = true;
				for (ValidMove whichMove : validMoves) {
					if (whichMove.getBlockPosition().equals(blockPosition)) {
						collide = false;
						break;
					}
				}
				if (!collide) {
					gameScreen.MoveUnit(draggingUnit, blockPosition);

					for (Unit whichUnit : gameScreen.getEnemy().getUnits()) {
						List<ValidMove> moves = gameScreen.generateValidMoves(whichUnit);
						if (moves.size() > 0) {
							gameScreen.MoveUnit(whichUnit, moves.get((new Random()).nextInt(moves.size())).getBlockPosition());
							break;
						}
					}
				}
				gameScreen.setState(new NormalState(gameScreen, graphics));
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			draggingScreenPosition = screenPosition;
		}
	}

	@Override
	public void paint() {
		Matrix matrix = new Matrix();
		float blockWidth = gameScreen.getBlockWidth();

		graphics.drawBackground(Assets.backgroundImg);

		gameScreen.getBoard().draw();

		List<ValidMove> tempValidMoves = new ArrayList<ValidMove>(validMoves);
		for (ValidMove whichValidMove : tempValidMoves) {
			gameScreen.drawSprite(whichValidMove);
		}

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser(), draggingUnit);

		matrix.reset();
		matrix.setScale(blockWidth / draggingUnit.getImage().getWidth(), blockWidth / draggingUnit.getImage().getHeight());
		matrix.postTranslate(draggingScreenPosition.x - 0.5f * blockWidth, draggingScreenPosition.y - 0.5f * blockWidth);
		graphics.drawBitmap(draggingUnit.getImage(), matrix, null);

	}

	@Override
	public void Dispose() {

	}

}
