package com.battleBoard.battleBoardGame;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Screen;

public class GameScreen extends Screen {
	enum BattleState {
		normal, selectingUnit, draggingUnit, selectingAbility, castingAbility, computerMove
	}
	
	private BattleState state = BattleState.normal;
	private Character character;
	private Board board;
	private Paint textPaint;
	private Point boardSize = new Point(5, 5);
	private final int screenWidth;
	private final int screenHeight;
	private final float blockWidth;
	private final float startY;

	public GameScreen(Game game) {
		super(game);
		
		character = new Character(80, 50);
		board = new Board();

		textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.LEFT);
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.BLACK);
		
		screenWidth = game.getScreenRect().width();
		screenHeight = game.getScreenRect().height();		
		blockWidth = (float)screenWidth / (float)boardSize.x;
		startY = screenHeight * 0.5f - blockWidth * 2.5f;
	}

	@Override
	public void update(float deltaTime) {

	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		PointF screenPosition = new PointF(event.getX(), event.getY());
		Point blockPosition = screenToBlockPosition(event.getX(), event.getY());
						
		if(state == BattleState.normal) {
			if(blockPosition.equals(character.getBlockPosition()) && event.getAction() == MotionEvent.ACTION_DOWN) {
				character.setScreenPosition(screenPosition);
				state = BattleState.draggingUnit;
			}
		}
		else if(state == BattleState.draggingUnit) {
			if(event.getAction() == MotionEvent.ACTION_UP) {
				character.setBlockPosition(blockPosition);
				character.setScreenPosition(blockToScreenPosition(blockPosition));
				state = BattleState.normal;
			}
			else if(event.getAction() == MotionEvent.ACTION_MOVE) {
				screenPosition.x -= blockWidth * 0.5;
				screenPosition.y -= blockWidth * 0.5;
				character.setScreenPosition(screenPosition);
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
		
		matrix.reset();
		matrix.setScale(blockWidth / Assets.characterImg.getWidth(), blockWidth / Assets.characterImg.getHeight());
		if(state == BattleState.normal) {
			PointF p = blockToScreenPosition(character.getBlockPosition());
			matrix.postTranslate(p.x, p.y);
		}
		else if(state == BattleState.draggingUnit) {
			matrix.postTranslate(character.getScreenPosition().x, character.getScreenPosition().y);
		}
		graphics.drawBitmap(Assets.characterImg, matrix, null);
	}

	private Point screenToBlockPosition(float x, float y) {
		Point answer = new Point((int)(x / blockWidth) , (int) ((y - startY) / blockWidth));
		if(answer.x >= boardSize.x) {
			answer.x = boardSize.x - 1;
		}
		if(answer.y >= boardSize.y) {
			answer.y = boardSize.y - 1;
		}
		return answer;
	}
	
	private PointF blockToScreenPosition(Point blockPosition) {
		return new PointF(blockPosition.x * blockWidth, startY + blockPosition.y * blockWidth);
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