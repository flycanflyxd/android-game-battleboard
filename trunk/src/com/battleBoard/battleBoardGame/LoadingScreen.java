package com.battleBoard.battleBoardGame;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Graphics.ImageFormat;
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
		Assets.blockImg = g.newImage("block.png", ImageFormat.ARGB4444);

		//This is how you would load a sound if you had one.
		//Assets.click = game.getAudio().createSound("explode.ogg");

		
		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint(float deltaTime) {
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
}