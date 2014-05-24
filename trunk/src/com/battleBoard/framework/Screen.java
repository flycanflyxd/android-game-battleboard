package com.battleBoard.framework;

import android.view.MotionEvent;

public abstract class Screen {
    protected final Game game;
    private volatile boolean needRedraw = true;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public void paint(float deltaTime) {
    	needRedraw = false;    
    }

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
	public abstract void backButton();
	
	public abstract void onTouchEvent(MotionEvent motionEvent);
	
	public void setNeedRedraw(boolean flag) {
		needRedraw = flag;
	}
	
	public boolean NeedRedraw() {
		return needRedraw;
	}
}