package com.battleBoard.battleBoardGame.Screens;

import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.implementation.Graphics;
import com.battleBoard.framework.implementation.Graphics.ImageFormat;
import com.battleBoard.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {

		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menuImg = g.newImage("menu.png", ImageFormat.RGB565);
		Assets.backgroundImg = g.newImage("background.png", ImageFormat.RGB565);
		Assets.characterImg = g.newImage("character.png", ImageFormat.ARGB4444);
		Assets.knightImg = g.newImage("knight.png", ImageFormat.ARGB4444);
		Assets.cleanerImg = g.newImage("cleaner.png", ImageFormat.ARGB4444);
		Assets.archerImg = g.newImage("archer.png", ImageFormat.ARGB4444);
		Assets.blockImg = g.newImage("block.png", ImageFormat.ARGB4444);
		Assets.magic = g.newImage("magic.png", ImageFormat.ARGB4444);
		Assets.unit1 = g.newImage("unit1.png", ImageFormat.ARGB4444);
		Assets.selectCircle = g.newImage("circle.png", ImageFormat.ARGB4444);
		Assets.skillTarget = g.newImage("skillTarget.png", ImageFormat.ARGB4444);
		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint() {
		Graphics g = game.getGraphics();
		g.drawBackground(Assets.splashImg);
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
	}
}