package com.battleBoard.battleBoardGame;

import com.battleBoard.framework.Image;
import com.battleBoard.framework.Music;
//import com.battleBoard.framework.Sound;

public class Assets {
	
	public static Image menuImg, splashImg, backgroundImg, characterImg, blockImg;
	//public static Sound click;
	public static Music theme;
	
	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		theme = sampleGame.getAudio().createMusic("RainyMemory.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
	
}
