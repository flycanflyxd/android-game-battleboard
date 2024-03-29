package com.battleBoard.framework;

import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Rect;

public interface Game {

	public Audio getAudio();

	public FileIO getFileIO();

	public Graphics getGraphics();

	public void setScreen(Screen screen);

	public Screen getCurrentScreen();

	public Screen getInitScreen();

	public Rect getScreenRect();
}
