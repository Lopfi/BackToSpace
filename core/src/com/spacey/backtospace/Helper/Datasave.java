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
    String Sdata = prefs.getString(key, "");
    return Sdata;
}
public Boolean readBoolean(String key){
    Boolean Bdata = prefs.getBoolean(key, false);
    return Bdata;
}
public Integer readInteger(String key){
    Integer Idata = prefs.getInteger(key, 0);
    return Idata;
}
public Float readFloat(String key){
    Float Idata = prefs.getFloat(key, 0f);
    return Idata;
}
public boolean exists(String key){
    boolean is = prefs.contains(key);
    return is;
}
public void clear(){
    prefs.clear();
    prefs.flush();
}

}