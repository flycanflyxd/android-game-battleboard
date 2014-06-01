package com.battleBoard.battleBoardGame.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.Board;
import com.battleBoard.battleBoardGame.Player;
import com.battleBoard.battleBoardGame.Sprite;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.AnotherUnit;
import com.battleBoard.battleBoardGame.Units.SomeUnit;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.battleStates.IBattleState;
import com.battleBoard.battleBoardGame.battleStates.NormalState;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.Graphics;

public class GameScreen extends Screen {

	private Board board = null;
	private final int screenWidth;
	private final int screenHeight;
	private final float blockWidth;
	private final float startY;
	private Player user = new Player();
	private Player enemy = new Player();
	Matrix matrix = new Matrix();
	private IBattleState state;

	public GameScreen(Game game) {
		super(game);

		Point boardSize = new Point(5, 5);
		screenWidth = game.getScreenRect().width();
		screenHeight = game.getScreenRect().height();
		blockWidth = (float) screenWidth / (float) boardSize.x;
		startY = screenHeight * 0.5f - blockWidth * (float) boardSize.y * 0.5f;

		board = new Board(boardSize.x, boardSize.y, blockWidth, startY, game.getGraphics());

		Unit unit = new SomeUnit(new Point(0, 0), user);
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		unit = new SomeUnit(new Point(1, 1), user);
		user.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		unit = new AnotherUnit(new Point(4, 4), enemy);
		enemy.addUnit(unit);
		board.UnitGetIn(unit, unit.getBlockPosition());

		setState(new NormalState(this, game.getGraphics()));
	}

	@Override
	public void update(float deltaTime) {
		state.update(deltaTime);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		state.onTouchEvent(event);
		this.setNeedRedraw(true);
	}

	@Override
	public void paint() {
		super.paint();
		state.paint();
		this.setNeedRedraw(false);
	}

	public void MoveUnit(Unit unit, Point newBlockPosition) {
		board.UnitGetOut(unit.getBlockPosition());
		board.UnitGetIn(unit, newBlockPosition);
		unit.setBlockPosition(newBlockPosition);
	}

	public void DamageUnit(Unit unitUnderDamage, float damageAmount) {
		unitUnderDamage.damage(60.0f);
		if(unitUnderDamage.isDead()) {
			unitUnderDamage.getOwner().removeUnit(unitUnderDamage);
			board.getBlock(unitUnderDamage.getBlockPosition()).removeUnit();
		}
	}
	
	// TODO 移到適當地方
	public List<ValidMove> generateValidMoves(Unit whichUnit) {
		List<ValidMove> answer = new ArrayList<ValidMove>();
		for (Point whichMove : whichUnit.getValidMoves()) {
			if (ValidBlockPosition(whichMove)) {
				if (board.isBlockEmpty(whichMove)) {
					answer.add(new ValidMove(Assets.magic, whichMove.x, whichMove.y));
				}
			}
		}
		return answer;
	}

	public void drawPlayerUnits(Player player) {
		Graphics graphics = game.getGraphics();
		for (Unit whichUnit : player.getUnits()) {
			matrix.reset();
			matrix.setScale(blockWidth / whichUnit.getImage().getWidth(), blockWidth / whichUnit.getImage().getHeight());

			PointF p = blockToScreenPosition(whichUnit.getBlockPosition());
			matrix.postTranslate(p.x, p.y);
			graphics.drawBitmap(whichUnit.getImage(), matrix, null);

		}
	}

	public void drawPlayerUnits(Player player, Unit dontDrawThisUnit) {
		Graphics graphics = game.getGraphics();
		for (Unit whichUnit : player.getUnits()) {
			matrix.reset();
			matrix.setScale(blockWidth / whichUnit.getImage().getWidth(), blockWidth / whichUnit.getImage().getHeight());

			if (whichUnit == dontDrawThisUnit) {

			} else {
				PointF p = blockToScreenPosition(whichUnit.getBlockPosition());
				matrix.postTranslate(p.x, p.y);
				graphics.drawBitmap(whichUnit.getImage(), matrix, null);
			}

		}
	}

	public void drawSprite(Sprite sprite) {
		Graphics graphics = game.getGraphics();
		matrix.setScale(blockWidth / sprite.getImage().getWidth(), blockWidth / sprite.getImage().getHeight());
		PointF screenPosition = blockToScreenPosition(sprite.getBlockPosition());
		matrix.postTranslate(screenPosition.x, screenPosition.y);
		graphics.drawBitmap(sprite.getImage(), matrix, null);
	}

	public Point screenToBlockPosition(float x, float y) {
		Point answer = new Point((int) (x / blockWidth), (int) ((y - startY) / blockWidth));

		if (answer.x < 0) {
			answer.x = 0;
		} else if (answer.x >= board.getWidth()) {
			answer.x = board.getWidth() - 1;
		}

		if (answer.y < 0) {
			answer.y = 0;
		} else if (answer.y >= board.getHeight()) {
			answer.y = board.getHeight() - 1;
		}

		return answer;
	}

	public PointF blockToScreenPosition(Point blockPosition) {
		return new PointF(blockPosition.x * blockWidth, startY + blockPosition.y * blockWidth);
	}

	private boolean ValidBlockPosition(Point blockPosition) {
		return blockPosition.x >= 0 && blockPosition.x < board.getWidth() && blockPosition.y >= 0 && blockPosition.y < board.getHeight();
	}

	public void setState(IBattleState newState) {
		IBattleState oldState = state;

		state = newState;
		if (oldState != null) {
			oldState.Dispose();
		}
	}

	public float getBlockWidth() {
		return blockWidth;
	}

	public Game getGame() {
		return game;
	}

	public Board getBoard() {
		return board;
	}

	public Player getUser() {
		return user;
	}

	public Player getEnemy() {
		return enemy;
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