package com.spacey.backtospace.Helper;

import com.badlogic.gdx.Gdx;
import com.spacey.backtospace.GameClass;

public class Datasave extends GameClass{

com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("GameData");

public void write(String key, String data){
    prefs.putString(key, data);
}
public void write(String key, Boolean data){
    prefs.putBoolean(key, data);
}
public void write(String key, Integer data){
    prefs.putInteger(key, data);
}    
public String readString(String key){
    String Sdata = prefs.getString(key);
    return Sdata;
}
public Boolean readBoolean(String key){
    Boolean Bdata = prefs.getBoolean(key);
    return Bdata;
}
public Integer readInteger(String key){
    Integer Idata = prefs.getInteger(key);
    return Idata;
}
public void safe(){
    prefs.flush();
}

}