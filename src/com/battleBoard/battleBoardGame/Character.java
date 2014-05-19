package com.battleBoard.battleBoardGame;

import com.battleBoard.framework.Input.TouchEvent;

public class Character extends Sprite
{
	private int xStart = 0;
	private int yStart = 0;
	private int xMovedDis = 0;
	private int yMovedDis = 0;
	
	public Character(int x, int y)
	{
		super(x, y);
	}

	public void update(TouchEvent event)
	{
		
		if(event.type == TouchEvent.TOUCH_DOWN)
		{
			xStart = this.x;
			yStart = this.y;
			xMovedDis = 0;
			yMovedDis = 0;
		}
		else if(event.type == TouchEvent.TOUCH_DRAGGED)
		{
			xMovedDis = (event.x - xStart - 38);
			yMovedDis = (event.y - yStart - 38);
			this.x = xStart + xMovedDis;
			this.y = yStart + yMovedDis;
			if(xMovedDis > 114 || xMovedDis < -114 || yMovedDis > 114 || yMovedDis < -114)
			{
				this.x = xStart;
				this.y = yStart;
				xMovedDis = 0;
				yMovedDis = 0;
			}
		}
		else if(event.type == TouchEvent.TOUCH_UP)
		{
			if(xMovedDis != 0 || yMovedDis != 0)
			{
				this.x = xStart;
				this.y = yStart;
			}
			xMovedDis = event.x - xStart -38;
			yMovedDis = event.y - yStart -38;
			if ((xMovedDis <= 114 || xMovedDis >= -114 || yMovedDis <= 114 || yMovedDis >= -114) &&
				(xMovedDis <= -38 || xMovedDis >= 38 || yMovedDis <= -38 || yMovedDis >= 38))
			{
				if(xMovedDis <= -38)
				{
					this.x = xStart - 76;
				}
				else if(xMovedDis >= 38)
				{
					this.x = xStart + 76;
				}
				else
				{
					this.x = xStart;
				}
				
				if(yMovedDis <= -38)
				{
					this.y = yStart - 76;
				}
				else if(yMovedDis >= 38)
				{
					this.y = yStart + 76;
				}
				else
				{
					this.y = yStart;
				}
			}
		}
	}

	public int getxMovedDis() {
		return xMovedDis;
	}

	public int getyMovedDis() {
		return yMovedDis;
	}	
}
