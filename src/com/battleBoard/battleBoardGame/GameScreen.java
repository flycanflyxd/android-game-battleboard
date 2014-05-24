package com.battleBoard.battleBoardGame;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		battling
	}

	private GameState state = GameState.battling;
	private Character character;
	private Board board;
	private Paint textPaint;
	boolean touchDown = false;

	public GameScreen(Game game) {
		super(game);
		
		character = new Character(80, 50);
		board = new Board();

		textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.LEFT);
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.BLACK);
	}

	@Override
	public void update(float deltaTime) {

	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		final int cWidth = Assets.characterImg.getWidth();
		final int cHeight = Assets.characterImg.getHeight();
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (inBounds(event, character.getX(), character.getY(), cWidth, cHeight)) {
				character.update(event);
				touchDown = true;
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (touchDown) {
				character.update(event);
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (inBounds(event, character.getX(), character.getY(), cWidth, cHeight)) {
				character.update(event);
				touchDown = false;
			}
		}
	}

	private boolean inBounds(MotionEvent event, int x, int y, int width,
			int height) {
		if (event.getX() > x && event.getX() < x + width - 1 && event.getY() > y
				&& event.getY() < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawBackground(Assets.backgroundImg);

		for(int i = 0; i < board.getRowSize(); ++i) {
			for(int j = 0; j < board.getColSize(); ++j) {
				g.drawBitmap(Assets.blockImg, board.getBlock(i, j).getX(), board.getBlock(i, j).getY(), null);
			}
		}
		
		g.drawBitmap(Assets.characterImg, character.getX(), character.getY(), null);
		g.drawText("xStart: " + Integer.toString(character.getxStart()), 500, 100, textPaint);
		g.drawText("yStart: " + Integer.toString(character.getyStart()), 500, 150, textPaint);
		g.drawText("xMovedDis: " + Integer.toString(character.getxMovedDis()), 500, 200, textPaint);
		g.drawText("yMovedDis: " + Integer.toString(character.getyMovedDis()), 500, 250, textPaint);
		g.drawText("xEnd: " + Integer.toString(character.getxEnd()), 500, 300, textPaint);
		g.drawText("yEnd: " + Integer.toString(character.getyEnd()), 500, 350, textPaint);
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