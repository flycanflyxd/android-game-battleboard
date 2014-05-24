package com.battleBoard.battleBoardGame;

import android.graphics.Bitmap;
import com.battleBoard.framework.Music;

public class Assets {	
	public static Bitmap menuImg, splashImg, backgroundImg, characterImg, blockImg;

	public static Music theme;
	
	public static void load(BattleBoard sampleGame) {
		theme = sampleGame.getAudio().createMusic("RainyMemory.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
}
