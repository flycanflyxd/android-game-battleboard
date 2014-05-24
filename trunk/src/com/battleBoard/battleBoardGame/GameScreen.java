package com.battleBoard.battleBoardGame;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Units.AnotherUnit;
import com.battleBoard.battleBoardGame.Units.SomeUnit;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Screen;

public class GameScreen extends Screen {
	enum BattleState {
		normal, selectingUnit, draggingUnit, selectingAbility, castingAbility, computerMove
	}
	
	private BattleState state = BattleState.normal;
	private Board board = new Board();
	private Paint textPaint = new Paint();
	private final Point boardSize = new Point(5, 5);
	private final int screenWidth;
	private final int screenHeight;
	private final float blockWidth;
	private final float startY;
	
	private Player user = new Player();
	private Player enemy = new Player();
	private Unit draggingUnit = null;
	private ArrayList<ValidMove> validMoves = new ArrayList<ValidMove>();

	public GameScreen(Game game) {
		super(game);

		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.LEFT);
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.BLACK);
		
		screenWidth = game.getScreenRect().width();
		screenHeight = game.getScreenRect().height();		
		blockWidth = (float)screenWidth / (float)boardSize.x;
		startY = screenHeight * 0.5f - blockWidth * 2.5f;
		
		user.addUnit(new SomeUnit(0, 0));
		user.addUnit(new SomeUnit(1, 1));
		user.addUnit(new SomeUnit(1, 2));
		user.addUnit(new SomeUnit(0, 2));
		
		enemy.addUnit(new AnotherUnit(4, 4));
		enemy.addUnit(new AnotherUnit(4, 3));
		enemy.addUnit(new AnotherUnit(4, 2));
	}

	@Override
	public void update(float deltaTime) {

	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = screenToBlockPosition(event.getX(), event.getY());
						
		if(state == BattleState.normal) {
			for(Unit whichUnit : user.getUnits()) {
				if(blockPosition.equals(whichUnit.getBlockPosition()) && event.getAction() == MotionEvent.ACTION_DOWN) {
					screenPosition.x -= blockWidth * 0.5;
					screenPosition.y -= blockWidth * 0.5;
					whichUnit.setScreenPosition(screenPosition);
					generateValidMoves(whichUnit);				
					state = BattleState.draggingUnit;
					break;
				}
			}
		}
		else if(state == BattleState.draggingUnit) {
			if(event.getAction() == MotionEvent.ACTION_UP) {
				boolean collide = true;
				for(ValidMove whichMove : validMoves) {
					if(whichMove.getBlockPosition().equals(blockPosition)) {
						collide = false;
						break;
					}
				}
				if(!collide) {
					draggingUnit.setBlockPosition(blockPosition);
					draggingUnit.setScreenPosition(blockToScreenPosition(blockPosition));
				}
				draggingUnit = null;
				validMoves.clear();
				state = BattleState.normal;
			}
			else if(event.getAction() == MotionEvent.ACTION_MOVE) {
				screenPosition.x -= blockWidth * 0.5;
				screenPosition.y -= blockWidth * 0.5;
				draggingUnit.setScreenPosition(screenPosition);
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics graphics = game.getGraphics();
		graphics.drawBitmap(Assets.backgroundImg, null, new Rect(0, 0, screenWidth, screenHeight), null);
		
		Matrix matrix = new Matrix();
		for(int i = 0; i < board.getRowSize(); ++i) {
			for(int j = 0; j < board.getColSize(); ++j) {
				matrix.reset();
				matrix.setScale(blockWidth / Assets.blockImg.getWidth(), blockWidth / Assets.blockImg.getHeight());
				matrix.postTranslate(blockWidth * i, startY + blockWidth * j);
				graphics.drawBitmap(Assets.blockImg, matrix, null);
			}
		}
		
		for(ValidMove whichValidMove : validMoves) {
			matrix.reset();
			matrix.setScale(blockWidth / whichValidMove.getImage().getWidth(), blockWidth / whichValidMove.getImage().getHeight());			
			PointF p = blockToScreenPosition(whichValidMove.getBlockPosition());
			matrix.postTranslate(p.x, p.y);		
			graphics.drawBitmap(whichValidMove.getImage(), matrix, null);
		}
		
		drawPlayerUnits(user);
		drawPlayerUnits(enemy);
	}
	
	private void generateValidMoves(Unit whichUnit) {
		draggingUnit = whichUnit;
		for(Point whichMove : draggingUnit.getValidMoves()) {
			if(ValidBlockPosition(whichMove)) {
				boolean collide = false;
				for(Unit whichNearUnit : user.getUnits()) {
					if(whichMove.equals(whichNearUnit.getBlockPosition())) {
						collide = true;
						break;
					}
				}
				if(!collide) {
					for(Unit whichNearUnit : enemy.getUnits()) {
						if(whichMove.equals(whichNearUnit.getBlockPosition())) {
							collide = true;
							break;
						}
					}
				}
				if(!collide) {
					validMoves.add(new ValidMove(Assets.magic, whichMove.x, whichMove.y));
				}
			}
		}
	}

	private void drawPlayerUnits(Player player) {
		Graphics graphics = game.getGraphics();
		Matrix matrix = new Matrix();
		for(Unit whichUnit : player.getUnits()) {
			matrix.reset();
			matrix.setScale(blockWidth / whichUnit.getImage().getWidth(), blockWidth / whichUnit.getImage().getHeight());
			
			if(state == BattleState.draggingUnit && whichUnit == draggingUnit) {
				matrix.postTranslate(whichUnit.getScreenPosition().x, whichUnit.getScreenPosition().y);
			}
			else {
				PointF p = blockToScreenPosition(whichUnit.getBlockPosition());
				matrix.postTranslate(p.x, p.y);
			}
			graphics.drawBitmap(whichUnit.getImage(), matrix, null);
		}
	}

	private Point screenToBlockPosition(float x, float y) {
		Point answer = new Point((int)(x / blockWidth) , (int) ((y - startY) / blockWidth));
		if(answer.x < 0) {
			answer.x = 0;
		}
		if(answer.x >= boardSize.x) {
			answer.x = boardSize.x - 1;
		}
		if(answer.y < 0) {
			answer.y = 0;
		}
		if(answer.y >= boardSize.y) {
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