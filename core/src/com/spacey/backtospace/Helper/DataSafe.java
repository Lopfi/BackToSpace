package com.spacey.backtospace.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spacey.backtospace.GameClass;

// used to store game data locally
public class DataSafe {

    private Preferences prefs;

    public Boolean playMusic;
    public Float playVolume;

    public Integer coins;
    public Integer life;

    public Boolean showTask;
    public Boolean cutSzeneFinished;
    public Integer level;
    public Integer slot1;
    public Integer slot2;
    public Integer slot3;
    public Float playerX;
    public Float playerY;
    public String currentMusic;
    public Integer currentSkin;
    public Boolean skin1;
    public Boolean skin2;
    public Boolean music1; //seeMeRollin
    public Boolean music2; //?
    public String music1Path = "music/SeeMeRollin.mp3";
    public String music2Path = "music/?.mp3";
    public String standardmusicPath = "music/Standard.mp3";

    public DataSafe() {
        prefs = Gdx.app.getPreferences("GameData");
        if (!exists("Initialized")) initialize();
        load();
    }

    public void write(String key, String data) {
        prefs.putString(key, data);
        prefs.flush();
    }

    public void write(String key, Boolean data) {
        prefs.putBoolean(key, data);
        prefs.flush();
    }

    public void write(String key, Integer data) {
        prefs.putInteger(key, data);
        prefs.flush();
    }

    public void write(String key, Float data) {
        prefs.putFloat(key, data);
        prefs.flush();
    }

    public String readString(String key) {
        return prefs.getString(key);
    }

    public boolean readBoolean(String key) {
        return prefs.getBoolean(key);
    }

    public int readInteger(String key) {
        return prefs.getInteger(key);
    }

    public float readFloat(String key) {
        return prefs.getFloat(key);
    }

    public boolean exists(String key) {
        return prefs.contains(key);
    }

    public void clear() {
        prefs.clear();
        prefs.flush();
    }

    public void initialize() {
        Gdx.app.log("INFO", "Save not found creating new.");
        write("Initialized", true);
        write("cutszeneloaded", false);
        write("music", true);
        write("volume", .8f);
        write("coins", 0);
        write("level", 1);
        write("showtask", false);
        write("cutSzeneFinished", false);
        write("life", 2);
        write("currentMusic", standardmusicPath);
        write("currentSkin", 0);
        write("music1", false);
        write("music2", false);
        write("skin1", false);
        write("skin2", false);
        write("playerX", 100f);
        write("playerY", 100f);

        write("slot1", 0);
        write("slot2", 0);
        write("slot3", 0);
    }

    public void load() {
        playMusic = readBoolean("music");
        playVolume = readFloat("volume");
        coins = readInteger("coins");
        slot1 = readInteger("slot1");
        slot2 = readInteger("slot2");
        slot3 = readInteger("slot3");
        level = readInteger("level");
        showTask = readBoolean("showtask");
        cutSzeneFinished = readBoolean("cutSzeneFinished");
        life = readInteger("life");
        currentMusic = readString("currentMusic");
        currentSkin = readInteger("currentSkin");
        music1 = readBoolean("music1");
        music2 = readBoolean("music2");
        skin1 = readBoolean("skin1");
        skin2 = readBoolean("skin2");
        playerX = readFloat("playerX");
        playerY = readFloat("playerY");
    }

    public void save() {
        write("coins", coins);
        write("level", level);
        write("life", life);
        write("playerX", playerX);
        write("playerY", playerY);
        write("slot1", slot1);
        write("slot2", slot2);
        write("slot3", slot3);
    }

}