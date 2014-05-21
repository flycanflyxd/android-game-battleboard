package com.battleBoard.battleBoardGame;

import com.battleBoard.framework.Input.TouchEvent;

public class Character extends Sprite {
	private int xStart = 0;
	private int yStart = 0;
	private int xEnd = 0;
	private int yEnd = 0;
	private int xMovedDis = 0;
	private int yMovedDis = 0;
	
	public Character(int x, int y) {
		super(x, y);
	}

	public void update(TouchEvent event)	{
		if(event.type == TouchEvent.TOUCH_DOWN)	{
			xStart = this.x + 38;
			yStart = this.y + 38;
			xMovedDis = 0;
			yMovedDis = 0;
		}
		else if(event.type == TouchEvent.TOUCH_DRAGGED)	{
			xMovedDis = (event.x - xStart);
			yMovedDis = (event.y - yStart);
			this.x = xStart + xMovedDis - 38;
			this.y = yStart + yMovedDis - 38;
			if(xMovedDis > 152 || xMovedDis < -152 || yMovedDis > 152 || yMovedDis < -152) {
				this.x = xStart - 38;
				this.y = yStart - 38;
				xMovedDis = 0;
				yMovedDis = 0;
			}
		}
		else if(event.type == TouchEvent.TOUCH_UP) {	
			if(xMovedDis != 0 || yMovedDis != 0) {
				this.x = xStart - 38;
				this.y = yStart - 38;
			}
			
			if ((xMovedDis <= 114 || xMovedDis >= -114 || yMovedDis <= 114 || yMovedDis >= -114) &&
				(xMovedDis <= -38 || xMovedDis >= 38 || yMovedDis <= -38 || yMovedDis >= 38)) {
				if(xMovedDis <= -38 && xStart - 114 >= 80) {
					this.x = xStart - 114;
				}
				else if(xMovedDis >= 38 && xStart + 38 < 80 + 76 * 5) {
					this.x = xStart + 38;
				}
				else {
					this.x = xStart - 38;
				}
				
				if(yMovedDis <= -38 && yStart - 114 >= 50) {
					this.y = yStart - 114;
				}
				else if(yMovedDis >= 38 && yStart + 38 < 50 + 76 * 5) {
					this.y = yStart + 38;
				}
				else {
					this.y = yStart - 38;
				}
				xEnd = this.x + 38;
				yEnd = this.y + 38;
				xMovedDis = 0;
				yMovedDis = 0;
			}
		}
	}

	public int getxMovedDis() {
		return xMovedDis;
	}

	public int getyMovedDis() {
		return yMovedDis;
	}
	
	public int getxStart()
	{
		return xStart;
	}
	
	public int getyStart()
	{
		return yStart;
	}
	
	public int getxEnd()
	{
		return xEnd;
	}
	
	public int getyEnd()
	{
		return yEnd;
	}
}
