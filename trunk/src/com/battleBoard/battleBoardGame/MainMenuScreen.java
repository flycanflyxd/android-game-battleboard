package com.battleBoard.battleBoardGame;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Input.TouchEvent;
import com.battleBoard.framework.Screen;

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
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if (inBounds(event, 50, 330, 350, 70)) {
					game.setScreen(new GameScreen(game));
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
		g.drawBackground(Assets.menuImg.getBitmap());	
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