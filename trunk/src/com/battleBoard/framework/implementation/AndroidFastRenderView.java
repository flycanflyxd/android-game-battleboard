package com.battleBoard.framework.implementation;

import com.battleBoard.framework.Game;
import com.battleBoard.framework.Screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class AndroidFastRenderView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

	SurfaceView surfaceView = null;
	Game game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public AndroidFastRenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void Initialize(Game game, Bitmap framebuffer) {
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	public void run() {
		Rect dstRect = new Rect();
		Canvas canvas;
		Screen currentScreen;
		long startTime = System.nanoTime();
		while (running) {
			float deltaTime = (System.nanoTime() - startTime) * 0.000001f;
			startTime = System.nanoTime();

//			if (deltaTime > 3.15f) {
//				deltaTime = 3.15f;
//			}

			currentScreen = game.getCurrentScreen();
			currentScreen.update(deltaTime);
			//if (currentScreen.NeedRedraw()) {
			if(true) {
				currentScreen.paint();
				canvas = holder.lockCanvas();
				if (canvas != null) {
					canvas.getClipBounds(dstRect);
					canvas.drawBitmap(framebuffer, null, dstRect, null);
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void pause() {
		running = false;
		while (true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		game.getCurrentScreen().onTouchEvent(motionEvent);
		game.getCurrentScreen().setNeedRedraw(true);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		holder.addCallback(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
}