package com.battleBoard.battleBoardGame.battleStates;

import android.view.MotionEvent;

public interface IBattleState {
	public void update(float deltaTime);

	public void onTouchEvent(MotionEvent event);

	public void paint();

	public void Dispose();
}
