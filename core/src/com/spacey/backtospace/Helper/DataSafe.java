package com.spacey.backtospace.Helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.UI.Inventory;
import com.spacey.backtospace.Entity.UI.Item;
import com.spacey.backtospace.Helper.Enums.ENTITYTYPE;

// used to store game data locally
public class DataSafe {
    GameClass game;
    private Preferences prefs;

    public Boolean playMusic;
    public Float playVolume;
    public int coins;
    public int life;
    
    public int itemCount;
    public Item[] items;
    public Object[][] chest;

    public Boolean showTask;
    public Boolean cutSzeneFinished;
    public int level;
    public Float playerX;
    public Float playerY;
    public String currentMusic;
    public int currentSkin;
    public Boolean skin1;
    public Boolean skin2;
    public Boolean music1; //seeMeRollin
    public Boolean music2; //?
    public String music1Path = "music/SeeMeRollin.mp3";
    public String music2Path = "music/?.mp3";
    public String standardmusicPath = "music/Standard.mp3";

    public DataSafe(GameClass game) {
        this.game = game;
        prefs = Gdx.app.getPreferences("GameData");
        if (!exists("Initialized")) initialize();
        load();
    }

    //String Save Operations on Objects
    public Object string2obj( String s ) {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois;
        Object o;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            o  = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
        return o;
    }
    public String obj2string( Serializable o ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream( baos );
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }

    public void write(String key, String data) {
        prefs.putString(key, data);
        prefs.flush();
    }

    public void write(String key, Boolean data) {
        prefs.putBoolean(key, data);
        prefs.flush();
    }

    public void write(String key, int data) {
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
        write("music", false);
        write("volume", .8f);
        write("coins", 0);
        write("level", 1);
        write("showtask", true);
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
        if (exists("invitems")) prefs.remove("invitems");
        if (exists("chestitems")) prefs.remove("chestitems");
    }

    public void load() {
        playMusic = readBoolean("music");
        playVolume = readFloat("volume");
        coins = readInteger("coins");
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
    }
    public void saveInventory(Inventory inv){
        //Integer itemCount = inv.itemCount;
        Object[] oinv = new Object[inv.items.length];

        for (int i = 0; i < inv.items.length; i++) {
            if (inv.items[i] == null){
                oinv[i] = ((ENTITYTYPE)null);
            } else {
                oinv[i] = ((ENTITYTYPE)inv.items[i].type);
            }
        }
        String iobj = obj2string(oinv);
        write("invitems", iobj);
    }
    public void saveChest(Object[][] chest){
        String cobj = obj2string(chest);
        write("chestitems", cobj);
    }
    public void loadChestInventory(){
        Object oobj = string2obj(readString("chestitems"));
        Object[][] cobj = ((Object[][]) oobj);

        if (exists("invitems")){
            itemCount = 0;
            Object iitems = string2obj(readString("invitems"));
            Object[] oiitems = ((Object[]) iitems);
            Item[] fitems = new Item[3];
            for (int i = 0; i < oiitems.length; i++) {
                if (((ENTITYTYPE)oiitems[i]) != null){
                    fitems[i] = ((Item) new Item(((ENTITYTYPE)oiitems[i]), game));
                    itemCount++;
                } else {
                    fitems[i] = null;
                }
            }
            items = fitems;
        } else {
            items = null;
            itemCount = 0;
        }
        chest = cobj;
    }
    public HashMap getInGame(){
        HashMap<Enums.ENTITYTYPE, Integer> hmap = new HashMap<Enums.ENTITYTYPE, Integer>();
        hmap.put(Enums.ENTITYTYPE.FUEL, 0);
        hmap.put(Enums.ENTITYTYPE.FUEL, 0);
        hmap.put(Enums.ENTITYTYPE.SCREW, 0);
        hmap.put(Enums.ENTITYTYPE.SCREWDRIVER, 0);
        hmap.put(Enums.ENTITYTYPE.KEY, 0);
        hmap.put(Enums.ENTITYTYPE.PLATE, 0);
        hmap.put(Enums.ENTITYTYPE.NOSECONE, 0);
        hmap.put(Enums.ENTITYTYPE.FIN, 0);
        hmap.put(Enums.ENTITYTYPE.STONE, 0);

        if (chest != null && chest.length > 0){
            for (int i = 0; i < chest.length; i++) {
                if(chest[i][0] == null) continue;
                //Gdx.app.log((Enums.ENTITYTYPE)(chest[i][0]) + ":", ""+(Integer)(chest[i][1]));
                hmap.put((Enums.ENTITYTYPE)(chest[i][0]), hmap.get((Enums.ENTITYTYPE)(chest[i][0])) + (Integer)(chest[i][1]));
            }
        }
        if (items != null && items.length > 0){
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) continue;
                //Gdx.app.log(items[i].type + ":", "1");
                hmap.put(items[i].type, hmap.get(items[i].type) + 1);
            }
        }

        /*Iterator iterator = hmap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            Gdx.app.log("=>", "key is: "+ mentry.getKey() + " & Value is: "+mentry.getValue());
        }   THIS IS JUST TO PRINT OUT THE KEYS AND NUMBERS OF THE ALREADY SPAWNED ENTITIES*/
        return hmap;
    }

}