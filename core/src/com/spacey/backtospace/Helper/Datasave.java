package com.spacey.backtospace.Helper;

import com.badlogic.gdx.Gdx;
public class Datasave {

com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("GameData");

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

}