package com.battleBoard.battleBoardGame;

import com.battleBoard.battleBoardGame.battleStates.BattleState;
import com.battleBoard.framework.Game;
import com.battleBoard.framework.implementation.Graphics;

import android.graphics.Point;
import android.graphics.PointF;

public interface IBattleScreen {
	public void drawPlayerUnits(Player player);

	public void drawSprite(Sprite sprite);

	public Point screenToBlockPosition(float x, float y);

	public PointF blockToScreenPosition(Point blockPosition);

	public void setState(BattleState newState);

	public float getBlockWidth();

	public World getWorld();

	public Graphics getGraphics();

	public Game getGame();

	public void createFloatText(String text, Point blockPosition);
	
	public void drawMpBar(Player player);
}
