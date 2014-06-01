package com.battleBoard.battleBoardGame.battleStates;

import android.app.Activity;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.battleBoard.battleBoardGame.Assets;
import com.battleBoard.battleBoardGame.R;
import com.battleBoard.battleBoardGame.ValidMove;
import com.battleBoard.battleBoardGame.Screens.GameScreen;
import com.battleBoard.battleBoardGame.Units.Unit;
import com.battleBoard.battleBoardGame.skills.Skill;
import com.battleBoard.framework.implementation.Graphics;

public class SelectingUnitState implements IBattleState {

	private final GameScreen gameScreen;
	private Graphics graphics = null;
	private Unit selectingUnit = null;
	private ViewGroup abilityButtons;

	public SelectingUnitState(final GameScreen gameScreen, final Graphics graphics, final Unit selectingUnit) {
		this.gameScreen = gameScreen;
		this.graphics = graphics;
		this.selectingUnit = selectingUnit;

		abilityButtons = (ViewGroup) (((Activity) gameScreen.getGame()).findViewById(R.id.buttons));

		// 新增技能 按鈕
		for (final Skill whichSkill : selectingUnit.getSkills()) {
			Button abilityButton = (Button) LayoutInflater.from((Activity) gameScreen.getGame()).inflate(R.layout.button_test, abilityButtons, false);
			abilityButton.setText(whichSkill.getName());
			abilityButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					gameScreen.setState(new ChooseSkillTargetState(gameScreen, graphics, selectingUnit, whichSkill, abilityButtons));
					gameScreen.setNeedRedraw(true);
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
		Point blockPosition = gameScreen.screenToBlockPosition(event.getX(), event.getY());

		if (!selectingUnit.getBlockPosition().equals(blockPosition)) {

			abilityButtons.removeAllViews();
			gameScreen.setState(new NormalState(gameScreen, graphics));
			gameScreen.onTouchEvent(event);
		}
	}

	@Override
	public void paint() {
		graphics.drawBackground(Assets.backgroundImg);

		gameScreen.getBoard().draw();

		gameScreen.drawPlayerUnits(gameScreen.getEnemy());
		gameScreen.drawPlayerUnits(gameScreen.getUser());

		gameScreen.drawSprite(new ValidMove(Assets.selectCircle, selectingUnit.getBlockPosition().x, selectingUnit.getBlockPosition().y));
	}

	@Override
	public void Dispose() {
		abilityButtons.removeAllViews();
	}
}
