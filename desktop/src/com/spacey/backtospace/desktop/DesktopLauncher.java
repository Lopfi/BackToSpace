package com.spacey.backtospace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spacey.backtospace.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Back To Space";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Game(), config);
	}
}
