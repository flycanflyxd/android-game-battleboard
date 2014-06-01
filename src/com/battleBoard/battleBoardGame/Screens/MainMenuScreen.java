package com.battleBoard.battleBoardGame.Screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.Graphics;

public class MainMenuScreen extends Screen {
	Paint paint;

	public MainMenuScreen(Game game) {
		super(game);
		paint = new Paint();
		paint.setTextSize(70);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
	}

	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {

			if (inBounds(event, 50, 330, 350, 70)) {
				game.setScreen(new GameScreen(game));
			}

		}
	}

	private boolean inBounds(MotionEvent event, int x, int y, int width, int height) {
		if (event.getX() > x && event.getX() < x + width - 1 && event.getY() > y && event.getY() < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint() {
		Graphics g = game.getGraphics();
		g.drawBackground(Assets.menuImg);
		g.drawText("START", 50, 400, paint);
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
		android.os.Process.killProcess(android.os.Process.myPid());

	}
}