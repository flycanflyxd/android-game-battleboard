package com.battleBoard.battleBoardGame.battleStates;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.UnitAction;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.Unit;

public class DraggingUnitState extends BattleState {

	private Unit draggingUnit = null;
	private PointF draggingScreenPosition = new PointF(0.0f, 0.0f);
	private List<ValidMove> validMoves = null;
	Matrix matrix = new Matrix();
	Paint paint = new Paint();

	public DraggingUnitState(IBattleScreen battleScreen, PointF touchScreenPosition, Unit touchUnit, List<ValidMove> validMoves) {
		super(battleScreen);
		this.draggingScreenPosition = touchScreenPosition;
		this.draggingUnit = touchUnit;
		this.validMoves = validMoves;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = battleScreen.screenToBlockPosition(event.getX(), event.getY());

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (draggingUnit.getBlockPosition().equals(blockPosition)) {
				battleScreen.setState(new SelectingUnitState(battleScreen, draggingUnit, validMoves));
			} else {
				boolean collide = true;
				for (ValidMove whichMove : validMoves) {
					if (whichMove.getBlockPosition().equals(blockPosition)) {
						collide = false;
						break;
					}
				}
				if (!collide) {
					battleScreen.setState(new TickingState(battleScreen, new UnitAction(draggingUnit, UnitAction.Type.move, blockPosition)));
				} else {
					battleScreen.setState(new NormalState(battleScreen));
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			draggingScreenPosition = screenPosition;
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		world.getBoard().draw();

		List<ValidMove> tempValidMoves = new ArrayList<ValidMove>(validMoves);
		for (ValidMove whichValidMove : tempValidMoves) {
			battleScreen.drawSprite(whichValidMove);
		}

		battleScreen.drawPlayerUnits(world.getEnemy());
		battleScreen.drawPlayerUnits(world.getUser());

		paint.setAlpha(150);
		float blockWidth = battleScreen.getBlockWidth();
		matrix.setScale(blockWidth / draggingUnit.getImage().getWidth(), blockWidth / draggingUnit.getImage().getHeight());
		matrix.postTranslate(draggingScreenPosition.x - 0.5f * blockWidth, draggingScreenPosition.y - 0.5f * blockWidth);
		graphics.drawBitmap(draggingUnit.getImage(), matrix, paint);
	}

	@Override
	public void Dispose() {

	}
}
