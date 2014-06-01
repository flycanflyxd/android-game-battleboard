package com.battleBoard.battleBoardGame;

import android.graphics.Point;
import android.graphics.PointF;

import com.battleBoard.framework.implementation.Graphics;

public interface IBattleScreenForFloatText {

	public Point screenToBlockPosition(float x, float y);

	public PointF blockToScreenPosition(Point blockPosition);

	public float getBlockWidth();

	public Graphics getGraphics();
}
