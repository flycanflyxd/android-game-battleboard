package com.battleBoard.battleBoardGame.battleStates;

import com.battleBoard.battleBoardGame.IBattleScreen;
import com.battleBoard.battleBoardGame.World;
import com.battleBoard.framework.implementation.Graphics;

import android.view.MotionEvent;

public abstract class BattleState {

	protected IBattleScreen battleScreen = null;
	protected Graphics graphics = null;
	protected World world = null;

	public BattleState(IBattleScreen battleScreen) {
		this.battleScreen = battleScreen;
		this.graphics = battleScreen.getGraphics();
		this.world = battleScreen.getWorld();
	}

	public abstract void update(float deltaTime);

	public abstract void onTouchEvent(MotionEvent event);

	public abstract void paint();

	public abstract void Dispose();
}
