package com.battleBoard.battleBoardGame.battleStates;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.UnitAction;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.skills.Skill;

public class ChooseSkillTargetState extends BattleState {

	private Unit caster = null;
	private Skill skill = null;
	private List<ValidMove> validTargetShadows = new ArrayList<ValidMove>();
	private Boolean[][] validTargetTable = null;
	private ViewGroup abilityButtons = null;

	public ChooseSkillTargetState(IBattleScreen battleScreen, Unit caster, Skill skill, ViewGroup abilityButtons) {
		super(battleScreen);
		this.caster = caster;
		this.skill = skill;
		this.abilityButtons = abilityButtons;
		validTargetTable = new Boolean[world.getBoard().getWidth()][world.getBoard().getHeight()];

		List<Point> validPoints = new ArrayList<Point>();
		skill.getValidPoints(validPoints, caster, world);

		for (int whichRow = 0; whichRow < world.getBoard().getHeight(); whichRow++) {
			for (int whichCol = 0; whichCol < world.getBoard().getWidth(); whichCol++) {
				validTargetTable[whichCol][whichRow] = false;
			}
		}
		
		if (skill.getCastType() == Skill.CastType.immediate) {
			battleScreen.setState(new TickingState(battleScreen, new UnitAction(caster, UnitAction.Type.castSkillTargetPoint, caster.getBlockPosition(), skill)));
		} else {
			
			for (Point whichValidPoint : validPoints) {
				validTargetShadows.add(new ValidMove(Assets.skillTarget, whichValidPoint));
				validTargetTable[whichValidPoint.x][whichValidPoint.y] = true;
			}
		}
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		Point blockPosition = battleScreen.screenToBlockPosition(event.getX(), event.getY());

		if (validTargetTable[blockPosition.x][blockPosition.y]) {
			battleScreen.setState(new TickingState(battleScreen, new UnitAction(caster, UnitAction.Type.castSkillTargetPoint, blockPosition, skill)));
		} else {
			battleScreen.setState(new NormalState(battleScreen));
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		world.getBoard().draw();

		battleScreen.drawPlayerUnits(battleScreen.getWorld().getEnemy());
		battleScreen.drawPlayerUnits(battleScreen.getWorld().getUser());

		List<ValidMove> tempValidMoves = new ArrayList<ValidMove>(validTargetShadows);
		for (ValidMove whichValidMove : tempValidMoves) {
			battleScreen.drawSprite(whichValidMove);
		}

		battleScreen.drawSprite(new ValidMove(Assets.selectCircle, caster.getBlockPosition()));
	}

	@Override
	public void Dispose() {
		abilityButtons.removeAllViews();
	}

}
