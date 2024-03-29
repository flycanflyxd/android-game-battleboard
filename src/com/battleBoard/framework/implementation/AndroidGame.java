package com.battleBoard.framework.implementation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.battleBoard.battleBoardGame.R;
import com.battleBoard.framework.Audio;
import com.battleBoard.framework.FileIO;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
	protected AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	FileIO fileIO;
	Screen screen;
	private Rect screenRect = new Rect();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics display = getResources().getDisplayMetrics();
		screenRect = new Rect();
		screenRect.right = display.widthPixels;
		screenRect.bottom = display.heightPixels;

		Bitmap frameBuffer = Bitmap.createBitmap(screenRect.width(), screenRect.height(), Config.RGB_565);

		setContentView(R.layout.main_layout);
		renderView = (AndroidFastRenderView) findViewById(R.id.main_view);
		renderView.Initialize(this, frameBuffer);
		graphics = new Graphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		screen = getInitScreen();
	}

	@Override
	public void onResume() {
		super.onResume();
		screen.resume();
		renderView.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		renderView.pause();
		screen.pause();

		if (isFinishing())
			screen.dispose();
	}

	@Override
	public FileIO getFileIO() {
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		return graphics;
	}

	@Override
	public Audio getAudio() {
		return audio;
	}

	@Override
	public void setScreen(Screen screen) {
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");

		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	public Screen getCurrentScreen() {
		return screen;
	}

	@Override
	public Rect getScreenRect() {
		return screenRect;
	}
}