package com.battleBoard.framework;

import android.view.MotionEvent;

public abstract class Screen {
	protected final Game game;

	public Screen(Game game) {
		this.game = game;
	}

	public abstract void update(float deltaTime);

	public abstract void paint();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void backButton();

	public abstract void onTouchEvent(MotionEvent motionEvent);
}