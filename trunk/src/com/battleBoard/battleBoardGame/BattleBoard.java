package com.battleBoard.battleBoardGame;

/*import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;

 import android.util.Log;*/

import com.battleBoard.battleBoardGame.Screens.SplashLoadingScreen;
import com.battleBoard.framework.Screen;
import com.battleBoard.framework.implementation.AndroidGame;

public class BattleBoard extends AndroidGame {

	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			Assets.load(this);
			firstTimeCreate = false;
		}
		return new SplashLoadingScreen(this);
	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	@Override
	public void onResume() {
		super.onResume();

		Assets.theme.play();
	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.pause();
	}
}