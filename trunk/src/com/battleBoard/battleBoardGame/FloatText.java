package com.battleBoard.battleBoardGame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class FloatText {

	private String text = null;
	private float timeNow = 0.0f;
	private float maxTime = 3000.0f;
	private PointF screenPosition = new PointF(0.0f, 0.0f);
	private float speed = 0.02f;
	public Paint getPaint() {
		return paint;
	}

	private Paint paint = new Paint();
		
	public FloatText(String text, PointF screenPosition) {
		this.text = text;
		this.screenPosition = screenPosition;
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}
	
	public void update(float deltaTime) {
		timeNow += deltaTime;
		screenPosition.y -= deltaTime * speed;
		paint.setAlpha((int)(255.0f - timeNow / maxTime * 255.0f));
	}
	
	public boolean isDead() {
		return timeNow >= maxTime;
	}

	public String getText() {
		return text;
	}

	public PointF getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(PointF screenPosition) {
		this.screenPosition = screenPosition;
	}
}
