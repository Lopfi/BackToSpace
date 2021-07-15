package com.spacey.backtospace.Helper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

// handles all the asset loading
public class AssetLoader {

    public AssetManager manager = new AssetManager();
    private String extendedPath = checkPath();

    public String checkPath(){ // Automatic Check if the Visualstudio asset Directory exists, if not use intelliJ ones
        if (new File(System.getProperty("user.dir") + "\\core\\assets").exists()) return "\\core\\assets";
        else return "";
        //For VisualStudio = "\\core\\assets" 
        //For IntelliJ = "" For production = "\\assets"
    }
    
    // loads all assets from the standard location
    public void loadAssets(boolean music){
        Gdx.app.log("INFO", "Loading from:" + System.getProperty("user.dir") + extendedPath);
        File dir = new File(System.getProperty("user.dir") + extendedPath);
        loadFromDir(dir, music);
    }

    // only loads the music files from /music
    public void loadMusic() {
        File dir = new File(System.getProperty("user.dir") + extendedPath + "/music");
        loadFromDir(dir, true);
    }

    // loads all assets from a specific directory
    private void loadFromDir(File dir, boolean music) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.isDirectory()) loadFromDir(child, music);
                else loadFromPath(child.getAbsolutePath(), music);
            }
        }
    }

    // loads a single asset from a specific path
    private void loadFromPath(String path, boolean music) {
        String type = path.split("\\.")[1];
        String name = path.split("assets\\\\")[1];
        if (path.contains("ignore")) {
            //ignore the ignore folder
            return;
        } else if (type.equals("png")) {
            manager.load(name, Texture.class);
        } else if (type.equals("mp3") && music) {
            manager.load(name, Sound.class);
        }
    }
}





