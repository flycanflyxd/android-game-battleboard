package com.battleBoard.battleBoardGame;

import java.util.LinkedList;
import java.util.List;

import com.battleBoard.framework.implementation.Graphics;

public class FloatTextManager {
	
	IBattleScreenForFloatText battleScreen = null;
	List<FloatText> floatTexts = new LinkedList<FloatText>(); 
	
	public FloatTextManager(IBattleScreenForFloatText battleScreen){
		this.battleScreen = battleScreen;
	}
	
	public void update(float deltaTime) {
		for(FloatText whichtext : floatTexts) {
			whichtext.update(deltaTime);
			if(whichtext.isDead()) {
				floatTexts.remove(whichtext);
			}
		}
	}
	
	public void paint() {
		Graphics graphics = battleScreen.getGraphics();
		for(FloatText whichtext : floatTexts) {
			graphics.drawText(whichtext.getText(), whichtext.getScreenPosition().x, whichtext.getScreenPosition().y, whichtext.getPaint());
		}
	}
	
	public void createFloatText(FloatText floatText) {
		floatTexts.add(floatText);
	}
}
