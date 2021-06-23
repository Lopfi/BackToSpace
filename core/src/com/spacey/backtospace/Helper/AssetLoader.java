package com.spacey.backtospace.Helper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.Arrays;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();
	
    public void loadAssets(boolean music){
        File dir = new File(System.getProperty("user.dir"));
        loadFromDir(dir, music);
    }

    public void loadMusic() {
        File dir = new File(System.getProperty("user.dir") + "/music");
        loadFromDir(dir, true);
    }

    private void loadFromDir(File dir, boolean music) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.isDirectory()) loadFromDir(child, music);
                else loadFromPath(child.getAbsolutePath(), music);
            }
        }
    }

    private void loadFromPath(String path, boolean music) {
        Gdx.app.log("path", Arrays.toString(path.split("\\.")));
        String type = path.split("\\.")[1];
        String name = path.split("assets\\\\")[1];
        if (type.equals("png")) {
            manager.load(name, Texture.class);
        }
        else if (type.equals("mp3") && music) {
            manager.load(name, Sound.class);
        }
    }
}





