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

    public Integer level;
    public Integer slot1;
    public Integer slot2;
    public Integer slot3;
    public Float playerX;
    public Float playerY;

    public DataSafe(GameClass game) {
        prefs = Gdx.app.getPreferences("GameData");
        if (!exists("initialized")) initialize();
        load(game);
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
        write("initialized", true);
        write("music", true);
        write("volume", .8f);
        write("coins", 0);
        write("level", 1);
        write("life", 3);
        write("playerX", 100f);
        write("playerY", 100f);

        write("slot1", 0);
        write("slot2", 0);
        write("slot3", 0);
    }

    public void load(GameClass game) {
        playMusic = readBoolean("music");
        playVolume = readFloat("volume");
        coins = readInteger("coins");
        slot1 = readInteger("slot1");
        slot2 = readInteger("slot2");
        slot3 = readInteger("slot3");
        level = readInteger("level");
        life = readInteger("life");
        playerX = readFloat("playerX");
        playerY = readFloat("playerY");
    }

}