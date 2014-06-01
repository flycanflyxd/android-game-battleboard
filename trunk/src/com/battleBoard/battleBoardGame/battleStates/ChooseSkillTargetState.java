package com.battleBoard.battleBoardGame.battleStates;

import java.util.ArrayList;
import java.util.List;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.skills.Skill;
import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class ChooseSkillTargetState implements IBattleState {

	private GameScreen gameScreen = null;
	private Graphics graphics = null;
	private Unit caster = null;
	private Skill skill = null;
	private List<ValidMove> validTargetShadows = new ArrayList<ValidMove>();
	private Boolean[][] validTargetTable = null;
	ViewGroup abilityButtons;

	public ChooseSkillTargetState(GameScreen gameScreen, Graphics graphics, Unit caster, Skill skill, ViewGroup abilityButtons) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;
		this.caster = caster;
		this.skill = skill;
		this.abilityButtons = abilityButtons;
		validTargetTable = new Boolean[gameScreen.getBoard().getWidth()][gameScreen.getBoard().getHeight()];

		int distance = skill.getCastDistance();
		for (int whichRow = 0; whichRow < gameScreen.getBoard().getHeight(); whichRow++) {
			for (int whichCol = 0; whichCol < gameScreen.getBoard().getWidth(); whichCol++) {
				if (PointF.length(whichCol - caster.getX(), whichRow - caster.getY()) <= distance) {
					validTargetShadows.add(new ValidMove(Assets.skillTarget, whichCol, whichRow));
					validTargetTable[whichCol][whichRow] = true;
				} else {
					validTargetTable[whichCol][whichRow] = false;
				}
			}
		}
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		Point blockPosition = gameScreen.screenToBlockPosition(event.getX(), event.getY());

		if (validTargetTable[blockPosition.x][blockPosition.y]) {
			// gameScreen.setState(new TickingState());
			gameScreen.setState(new NormalState(gameScreen, graphics));
		} else {
			gameScreen.setState(new NormalState(gameScreen, graphics));
		}

	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		gameScreen.getBoard().draw();

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser());

		List<ValidMove> tempValidMoves = new ArrayList<ValidMove>(validTargetShadows);
		for (ValidMove whichValidMove : tempValidMoves) {
			gameScreen.drawSprite(whichValidMove);
		}

		gameScreen.drawSprite(new ValidMove(Assets.selectCircle, caster.getBlockPosition().x, caster.getBlockPosition().y));
	}

	@Override
	public void Dispose() {
		abilityButtons.removeAllViews();
	}

}
