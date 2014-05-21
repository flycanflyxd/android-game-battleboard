package com.battleBoard.battleBoardGame;

import com.battleBoard.framework.Image;
import com.battleBoard.framework.Music;

public class Assets {	
	public static Image menuImg, splashImg, backgroundImg, characterImg, blockImg;

	public static Music theme;
	
	public static void load(SampleGame sampleGame) {
		theme = sampleGame.getAudio().createMusic("RainyMemory.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
}
