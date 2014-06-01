package com.battleBoard.battleBoardGame.Screens;

import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.Graphics;
import com.battleBoard.framework.implementation.Graphics.ImageFormat;

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splashImg = g.newImage("splash.jpg", ImageFormat.RGB565);
		game.setScreen(new LoadingScreen(game));
	}

	@Override
	public void paint() {
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
	}

	@Override
	public void onTouchEvent(MotionEvent motionEvent) {
		// TODO Auto-generated method stub

	}
}