package com.spacey.backtospace.desktop;

import com.badlogic.gdx.Files.FileType;
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
		config.addIcon("icon/icon128.png", FileType.Internal);
		config.addIcon("icon/icon32.png", FileType.Internal);
		config.addIcon("icon/icon16.png", FileType.Internal);
		config.pauseWhenMinimized = true;
		new LwjglApplication(new GameClass(), config);
	}
}
