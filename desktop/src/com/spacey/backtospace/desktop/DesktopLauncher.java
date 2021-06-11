package com.spacey.backtospace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spacey.backtospace.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Back To Space";
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new GameClass(), config);
	}
}
