package com.spacey.backtospace.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spacey.backtospace.GameClass;

// used to store game data locally
public class DataSafe {

Preferences prefs;

public DataSafe(GameClass game) {
    prefs = Gdx.app.getPreferences("GameData");
    if (!exists("intialized")) initialize();
    load(game);
}

public void write(String key, String data){
    prefs.putString(key, data);
    prefs.flush();
}
public void write(String key, Boolean data){
    prefs.putBoolean(key, data);
    prefs.flush();
}
public void write(String key, Integer data){
    prefs.putInteger(key, data);
    prefs.flush();
} 
public void write(String key, Float data){
    prefs.putFloat(key, data);
    prefs.flush();
}    
public String readString(String key){
    return prefs.getString(key);
}

public boolean readBoolean(String key){
    return prefs.getBoolean(key);
}

public int readInteger(String key){
    return prefs.getInteger(key);
}

public float readFloat(String key){
    return prefs.getFloat(key);
}

public boolean exists(String key){
    return prefs.contains(key);
}

public void clear(){
    prefs.clear();
    prefs.flush();
}

public void initialize() {
    Gdx.app.log("INFO", "Save not found creating new.");
    write("intialized", true);
    write("music", true);
    write("volume", .8f);
    write("coins", 0);
    write("level", 1);
    write("life", 3);
    write("playerx", 100f);
    write("playery", 100f);

    write("slot1", 0);
    write("slot2", 0);
    write("slot3", 0);
}

public void load(GameClass game) {
    game.playMusic = readBoolean("music");
    game.playVolume = readFloat("volume");
    game.coins = readInteger("coins");
    game.slot1 = readInteger("slot1");
    game.slot2 = readInteger("slot2");
    game.slot3 = readInteger("slot3");
    game.level = readInteger("level");
    game.life = readInteger("life");
    game.playerX = readFloat("playerx");
    game.playerY = readFloat("playery");
}

}