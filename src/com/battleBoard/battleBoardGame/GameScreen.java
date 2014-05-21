package com.battleBoard.battleBoardGame;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Input.TouchEvent;
import com.battleBoard.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Running
	}

	private GameState state = GameState.Running;
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
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		if (state == GameState.Running) {
			updateRunning(touchEvents, deltaTime);
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_DOWN)
			{
				if (inBounds(event, character.getX(), character.getY(), 76, 76)) {
					character.update(event);
					touchDown = true;
				}
			}
			else if (event.type == TouchEvent.TOUCH_DRAGGED) {
				if (touchDown) {
					character.update(event);
				}
			}
			else if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, character.getX(), character.getY(), 76, 76)) {
					character.update(event);
					touchDown = false;
				}
			}

		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.backgroundImg, 0, 0);

		for(int i = 0; i < board.getRowSize(); ++i) {
			for(int j = 0; j < board.getColSize(); ++j) {
				g.drawImage(Assets.blockImg, board.getBlock(i, j).getX(), board.getBlock(i, j).getY());
			}
		}
		
		g.drawImage(Assets.characterImg, character.getX(), character.getY());
		g.drawString("xStart: " + Integer.toString(character.getxStart()), 500, 100, textPaint);
		g.drawString("yStart: " + Integer.toString(character.getyStart()), 500, 150, textPaint);
		g.drawString("xMovedDis: " + Integer.toString(character.getxMovedDis()), 500, 200, textPaint);
		g.drawString("yMovedDis: " + Integer.toString(character.getyMovedDis()), 500, 250, textPaint);
		g.drawString("xEnd: " + Integer.toString(character.getxEnd()), 500, 300, textPaint);
		g.drawString("yEnd: " + Integer.toString(character.getyEnd()), 500, 350, textPaint);
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