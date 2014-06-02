package com.battleBoard.battleBoardGame.Screens;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Board;
import com.battleBoard.battleBoardGame.FloatText;
import com.battleBoard.battleBoardGame.FloatTextManager;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.IBattleScreenForFloatText;
import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.Sprite;
import com.battleBoard.battleBoardGame.World;
import com.battleBoard.battleBoardGame.Units.AnotherUnit;
import com.battleBoard.battleBoardGame.Units.SomeUnit;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.battleStates.BattleState;
import com.battleBoard.battleBoardGame.battleStates.NormalState;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.Graphics;

public class GameScreen extends Screen implements IBattleScreen, IBattleScreenForFloatText {

	private final float blockWidth;
	private final float startY;

	private World world = null;
	private Matrix matrix = new Matrix();
	private BattleState state;
	private FloatTextManager floatTextManager;

	public GameScreen(Game game) {
		super(game);

		final Point boardSize = new Point(5, 5);
		final int screenWidth = game.getScreenRect().width();
		final int screenHeight = game.getScreenRect().height();
		blockWidth = (float) screenWidth / (float) boardSize.x;
		startY = screenHeight * 0.5f - blockWidth * (float) boardSize.y * 0.5f;

		Board board = new Board(boardSize.x, boardSize.y, blockWidth, startY, game.getGraphics());

		Player user = new Player();
		Player enemy = new Player();

		Unit unit = new SomeUnit(new Point(0, 0), user);
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		unit = new SomeUnit(new Point(1, 1), user);
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		unit = new AnotherUnit(new Point(4, 4), enemy);
		enemy.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		world = new World(this, board, user, enemy);

		setState(new NormalState(this));

		floatTextManager = new FloatTextManager(this);
	}

	@Override
	public void update(float deltaTime) {
		state.update(deltaTime);
		floatTextManager.update(deltaTime);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		state.onTouchEvent(event);
	}

	@Override
	public void paint() {
		state.paint();

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(22);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		game.getGraphics().drawText("state=" + state.toString(), 0, 100, paint);

		floatTextManager.paint();
	}

	@Override
	public void drawPlayerUnits(Player player) {
		drawPlayerUnits(player, null);
	}

	public void drawPlayerUnits(Player player, Unit dontDrawThisUnit) {
		Graphics graphics = game.getGraphics();
		for (Unit whichUnit : player.getUnits()) {
			matrix.reset();
			matrix.setScale(blockWidth / whichUnit.getImage().getWidth(), blockWidth / whichUnit.getImage().getHeight());

			if (whichUnit != dontDrawThisUnit) {
				PointF p = blockToScreenPosition(whichUnit.getBlockPosition());
				matrix.postTranslate(p.x, p.y);
				graphics.drawBitmap(whichUnit.getImage(), matrix, null);
			}
		}
	}

	@Override
	public void drawSprite(Sprite sprite) {
		Graphics graphics = game.getGraphics();
		matrix.reset();
		matrix.setScale(blockWidth / sprite.getImage().getWidth(), blockWidth / sprite.getImage().getHeight());
		PointF screenPosition = blockToScreenPosition(sprite.getBlockPosition());
		matrix.postTranslate(screenPosition.x, screenPosition.y);
		graphics.drawBitmap(sprite.getImage(), matrix, null);
	}

	@Override
	public Point screenToBlockPosition(float x, float y) {
		Point answer = new Point((int) (x / blockWidth), (int) ((y - startY) / blockWidth));

		if (answer.x < 0) {
			answer.x = 0;
		} else if (answer.x >= world.getBoard().getWidth()) {
			answer.x = world.getBoard().getWidth() - 1;
		}

		if (answer.y < 0) {
			answer.y = 0;
		} else if (answer.y >= world.getBoard().getHeight()) {
			answer.y = world.getBoard().getHeight() - 1;
		}

		return answer;
	}

	@Override
	public PointF blockToScreenPosition(Point blockPosition) {
		return new PointF(blockPosition.x * blockWidth, startY + blockPosition.y * blockWidth);
	}

	@Override
	public void setState(BattleState newState) {
		BattleState oldState = state;

		state = newState;
		if (oldState != null) {
			oldState.Dispose();
		}
	}

	@Override
	public float getBlockWidth() {
		return blockWidth;
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public Graphics getGraphics() {
		return game.getGraphics();
	}

	@Override
	public void createFloatText(String text, Point blockPosition) {
		PointF screenPosition = blockToScreenPosition(blockPosition);
		screenPosition.x += blockWidth * 0.3f;
		floatTextManager.createFloatText(new FloatText(text, screenPosition));
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
		state.Dispose();
		game.setScreen(new MainMenuScreen(game));
	}

}