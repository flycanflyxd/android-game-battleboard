package com.battleBoard.battleBoardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.Image;
import com.battleBoard.framework.Input.TouchEvent;
import com.battleBoard.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	// Variable Setup

	private static Background background;
	private static Character character;
	public static Board board;

	private Image characterImg, boardImg, characterImage;

	int livesLeft = 1;
	Paint paint, paint2, paint3;
	
	boolean touchDown = false;

	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here

		background = new Background(0, 0);
		character = new Character(80, 50);
		board = new Board();

		characterImg = Assets.characterImg;
		boardImg = Assets.blockImg;

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
		
		paint3 = new Paint();
		paint3.setTextSize(30);
		paint3.setTextAlign(Paint.Align.LEFT);
		paint3.setAntiAlias(true);
		paint3.setColor(Color.BLACK);

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

		// This is identical to the update() method from our Unit 2/3 game.

		// 1. All touch input is handled here:
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

		// 2. Check miscellaneous events like death:

		if (livesLeft == 0) {
			state = GameState.GameOver;
		}

		// 3. Call individual update() methods here.
		// This is where all the game updates happen.
		// For example, robot.update();
		/*robot.update();
		if (robot.isJumped()) {
			currentSprite = Assets.characterJump;
		} else if (robot.isJumped() == false && robot.isDucked() == false) {
			currentSprite = anim.getImage();
		}

		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				projectiles.remove(i);
			}
		}

		updateTiles();
		hb.update();
		hb2.update();
		bg1.update();
		bg2.update();
		animate();*/

		if (character.getY() > 500) {
			state = GameState.GameOver;
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

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 800, 480)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.backgroundImg, background.getBgX(), background.getBgY());

		// First draw the game elements.
		for(int i = 0; i < board.getRowSize(); ++i)
		{
			for(int j = 0; j < board.getColSize(); ++j)
			{
				g.drawImage(Assets.blockImg, board.block(i, j).getX(), board.block(i, j).getY());
			}
		}
		
		g.drawImage(Assets.characterImg, character.getX(), character.getY());
		g.drawString("xStart: " + Integer.toString(character.getxStart()), 500, 100, paint3);
		g.drawString("yStart: " + Integer.toString(character.getyStart()), 500, 150, paint3);
		g.drawString("xMovedDis: " + Integer.toString(character.getxMovedDis()), 500, 200, paint3);
		g.drawString("yMovedDis: " + Integer.toString(character.getyMovedDis()), 500, 250, paint3);
		g.drawString("xEnd: " + Integer.toString(character.getxEnd()), 500, 300, paint3);
		g.drawString("yEnd: " + Integer.toString(character.getyEnd()), 500, 350, paint3);

		// Example:
		// g.drawImage(Assets.background, 0, 0);
		// g.drawImage(Assets.character, characterX, characterY);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		background = null;
		character = null;
		board = null;
		characterImg = null;
		boardImg = null;

		// Call garbage collector to clean up memory.
		System.gc();

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 400, 240, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		/*g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
		g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
		g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
		g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);*/

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paint2);
		g.drawString("Tap to return.", 400, 290, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBackground() {
		// TODO Auto-generated method stub
		return background;
	}

	public static Character getCharacter() {
		// TODO Auto-generated method stub
		return character;
	}

}