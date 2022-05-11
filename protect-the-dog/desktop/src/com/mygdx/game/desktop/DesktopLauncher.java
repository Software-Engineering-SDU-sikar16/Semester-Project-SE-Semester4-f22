package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TiledMap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gaem";
		config.width = 960;
		config.height = 640;
		config.resizable = false;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new TiledMap(), config);
	}
}
