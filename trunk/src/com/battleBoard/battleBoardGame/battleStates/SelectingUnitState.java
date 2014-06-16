package com.battleBoard.battleBoardGame.battleStates;

import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.R;
import com.battleBoard.battleBoardGame.UnitAction;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.skills.Skill;

public class SelectingUnitState extends BattleState {

	private Unit selectingUnit = null;
	private ViewGroup abilityButtons = null;
	private List<ValidMove> validMoves = null;

	public SelectingUnitState(final IBattleScreen battleScreen, final Unit selectingUnit, List<ValidMove> validmoves) {
		super(battleScreen);
		this.selectingUnit = selectingUnit;
		abilityButtons = (ViewGroup) (((Activity) battleScreen.getGame()).findViewById(R.id.buttons));
		this.validMoves = validmoves;

		// 新增技能 按鈕
		for (final Skill whichSkill : selectingUnit.getSkills()) {
			Button abilityButton = (Button) LayoutInflater.from((Activity) battleScreen.getGame()).inflate(R.layout.button_test, abilityButtons, false);
			abilityButton.setText(whichSkill.getName());
			abilityButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					battleScreen.setState(new ChooseSkillTargetState(battleScreen, selectingUnit, whichSkill, abilityButtons));
				}
			});
			abilityButtons.addView(abilityButton);
		}
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		Point blockPosition = battleScreen.screenToBlockPosition(event.getX(), event.getY());

		if (!selectingUnit.getBlockPosition().equals(blockPosition)) {

			boolean collide = true;
			for (ValidMove whichMove : validMoves) {
				if (whichMove.getBlockPosition().equals(blockPosition)) {
					collide = false;
					break;
				}
			}
			if (!collide) {
				battleScreen.setState(new TickingState(battleScreen, new UnitAction(selectingUnit, UnitAction.Type.move, blockPosition)));
			} else {
				battleScreen.setState(new NormalState(battleScreen));
			}
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		world.getBoard().draw();

		// List<ValidMove> tempValidMoves = new
		// ArrayList<ValidMove>(validMoves);
		for (ValidMove whichValidMove : validMoves) {
			battleScreen.drawSprite(whichValidMove);
		}

		battleScreen.drawPlayerUnits(world.getEnemy());
		battleScreen.drawPlayerUnits(world.getUser());
		battleScreen.drawMpBar(world.getUser());

		battleScreen.drawSprite(new ValidMove(Assets.selectCircle, selectingUnit.getBlockPosition()));
	}

	@Override
	public void Dispose() {
		abilityButtons.removeAllViews();
	}
}
