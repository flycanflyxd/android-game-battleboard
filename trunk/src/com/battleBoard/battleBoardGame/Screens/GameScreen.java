package com.battleBoard.battleBoardGame.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Board;
import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.AnotherUnit;
import com.battleBoard.battleBoardGame.Units.SomeUnit;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.Graphics;
import com.battleBoard.battleBoardGame.R;

import android.app.Activity;

public class GameScreen extends Screen {
	private enum BattleState {
		normal, selectingUnit, draggingUnit, selectingAbility, ticking
	}

	private BattleState state = BattleState.normal;
	private final Point boardSize = new Point(5, 5);
	private Board board = new Board(boardSize.x, boardSize.y);
	private final int screenWidth;
	private final int screenHeight;
	private final float blockWidth;
	private final float startY;
	private Player user = new Player();
	private Player enemy = new Player();
	private Unit draggingUnit = null;
	private Unit selectingUnit = null;
	private PointF draggingScreenPosition = new PointF(0.0f, 0.0f);
	private List<ValidMove> validMoves = new ArrayList<ValidMove>();
	private ViewGroup abilityButtons;

	public GameScreen(Game game) {
		super(game);

		screenWidth = game.getScreenRect().width();
		screenHeight = game.getScreenRect().height();
		blockWidth = (float) screenWidth / (float) boardSize.x;
		startY = screenHeight * 0.5f - blockWidth * (float) boardSize.y * 0.5f;

		Unit unit = new SomeUnit(new Point(0, 0));
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());
		
		unit = new SomeUnit(new Point(1, 1));
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		unit = new AnotherUnit(4, 4);
		enemy.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		abilityButtons = (ViewGroup) (((Activity) game).findViewById(R.id.buttons));
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = screenToBlockPosition(event.getX(), event.getY());

		if (state == BattleState.normal) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Unit unitHere = board.getBlock(blockPosition).getUnit();
				if (unitHere != null) {
					draggingUnit = unitHere;
					draggingScreenPosition = screenPosition;
					validMoves = generateValidMoves(unitHere);
					state = BattleState.draggingUnit;
				}
			}
		} else if (state == BattleState.draggingUnit) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (draggingUnit.getBlockPosition().equals(blockPosition)) {
					selectingUnit = draggingUnit;
					draggingUnit = null;
					
					
					Button abilityButton = (Button) LayoutInflater.from((Activity) game).inflate(R.layout.button_test, abilityButtons, false);
					abilityButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// 進入施展技能狀態
						}
					});
					abilityButtons.addView(abilityButton, 0);

					validMoves.clear();
					state = BattleState.selectingUnit;
				} else {
					boolean collide = true;
					for (ValidMove whichMove : validMoves) {
						if (whichMove.getBlockPosition().equals(blockPosition)) {
							collide = false;
							break;
						}
					}
					if (!collide) {
						MoveUnit(draggingUnit, blockPosition);

						for (Unit whichUnit : enemy.getUnits()) {
							List<ValidMove> moves = generateValidMoves(whichUnit);
							if (moves.size() > 0) {
								MoveUnit(whichUnit,  moves.get((new Random()).nextInt(moves.size())).getBlockPosition());
								break;
							}
						}
					}
					draggingUnit = null;
					selectingUnit = null;
					validMoves.clear();
					abilityButtons.removeAllViews();
					state = BattleState.normal;
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				draggingScreenPosition = screenPosition;
			}
		} else if (state == BattleState.selectingUnit) {
			if (!selectingUnit.getBlockPosition().equals(blockPosition)) {
				selectingUnit = null;
				abilityButtons.removeAllViews();
				state = BattleState.normal;
				onTouchEvent(event);
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		super.paint(deltaTime);
		Graphics graphics = game.getGraphics();
		graphics.drawBackground(Assets.backgroundImg);

		Matrix matrix = new Matrix();
		for (int i = 0; i < board.getHeight(); ++i) {
			for (int j = 0; j < board.getWidth(); ++j) {
				matrix.reset();
				matrix.setScale(blockWidth / Assets.blockImg.getWidth(), blockWidth / Assets.blockImg.getHeight());
				matrix.postTranslate(blockWidth * i, startY + blockWidth * j);
				graphics.drawBitmap(Assets.blockImg, matrix, null);
			}
		}

		List<ValidMove> tempValidMoves = new ArrayList<ValidMove>(validMoves);
		for (ValidMove whichValidMove : tempValidMoves) {
			matrix.reset();
			matrix.setScale(blockWidth / whichValidMove.getImage().getWidth(), blockWidth / whichValidMove.getImage().getHeight());
			PointF p = blockToScreenPosition(whichValidMove.getBlockPosition());
			matrix.postTranslate(p.x, p.y);
			graphics.drawBitmap(whichValidMove.getImage(), matrix, null);
		}

		drawPlayerUnits(enemy);
		drawPlayerUnits(user);
		
		if(state == BattleState.selectingUnit){
			matrix.reset();
			matrix.setScale(blockWidth / Assets.selectCircle.getWidth(), blockWidth / Assets.selectCircle.getHeight());
			PointF circlePosition = blockToScreenPosition(selectingUnit.getBlockPosition());
			matrix.postTranslate(circlePosition.x, circlePosition.y);
			graphics.drawBitmap(Assets.selectCircle, matrix, null);
		}
		
		if(draggingUnit != null){
			matrix.reset();
			matrix.setScale(blockWidth / draggingUnit.getImage().getWidth(), blockWidth / draggingUnit.getImage().getHeight());
			matrix.postTranslate(draggingScreenPosition.x - 0.5f * blockWidth, draggingScreenPosition.y - 0.5f * blockWidth);
			graphics.drawBitmap(draggingUnit.getImage(), matrix, null);
		}
		
		this.setNeedRedraw(false);
	}

	void MoveUnit(Unit unit, Point newBlockPosition) {
		board.UnitGetOut(unit.getBlockPosition());
		board.UnitGetIn(unit, newBlockPosition);
		unit.setBlockPosition(newBlockPosition);
	}
	
	private List<ValidMove> generateValidMoves(Unit whichUnit) {
		List<ValidMove> answer = new ArrayList<ValidMove>();
		draggingUnit = whichUnit;
		for (Point whichMove : draggingUnit.getValidMoves()) {
			if (ValidBlockPosition(whichMove)) {
				boolean collide = false;
				for (Unit whichNearUnit : user.getUnits()) {
					if (whichMove.equals(whichNearUnit.getBlockPosition())) {
						collide = true;
						break;
					}
				}
				if (!collide) {
					for (Unit whichNearUnit : enemy.getUnits()) {
						if (whichMove.equals(whichNearUnit.getBlockPosition())) {
							collide = true;
							break;
						}
					}
				}
				if (!collide) {
					answer.add(new ValidMove(Assets.magic, whichMove.x, whichMove.y));
				}
			}
		}
		return answer;
	}

	private void drawPlayerUnits(Player player) {
		Graphics graphics = game.getGraphics();
		Matrix matrix = new Matrix();
		for (Unit whichUnit : player.getUnits()) {
			matrix.reset();
			matrix.setScale(blockWidth / whichUnit.getImage().getWidth(), blockWidth / whichUnit.getImage().getHeight());

			if (state == BattleState.draggingUnit && whichUnit == draggingUnit) {
				
			} else {
				PointF p = blockToScreenPosition(whichUnit.getBlockPosition());
				matrix.postTranslate(p.x, p.y);
				graphics.drawBitmap(whichUnit.getImage(), matrix, null);
			}
			
		}
	}

	private Point screenToBlockPosition(float x, float y) {
		Point answer = new Point((int) (x / blockWidth), (int) ((y - startY) / blockWidth));
		if (answer.x < 0) {
			answer.x = 0;
		}
		if (answer.x >= boardSize.x) {
			answer.x = boardSize.x - 1;
		}
		if (answer.y < 0) {
			answer.y = 0;
		}
		if (answer.y >= boardSize.y) {
			answer.y = boardSize.y - 1;
		}
		return answer;
	}

	private PointF blockToScreenPosition(Point blockPosition) {
		return new PointF(blockPosition.x * blockWidth, startY + blockPosition.y * blockWidth);
	}

	private boolean ValidBlockPosition(Point blockPosition) {
		return blockPosition.x >= 0 && blockPosition.x < boardSize.x && blockPosition.y >= 0 && blockPosition.y < boardSize.y;
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void backButton() {
		game.setScreen(new MainMenuScreen(game));
	}
}