package com.spacey.backtospace.Helper;

import java.util.ArrayList;

// defines different types to be used elsewhere
public class Enums {
    
    public enum TILETYPE {
        GROUND,
        BORDER
    }

    public enum ENTITYTYPE {
        TILE,
        PLAYER,
        ENEMY,
        ROCKET,
        STONE,
        FUEL,
        SCREWDRIVER,
        SCREW,
        KEY,
        COIN,
        LIFE,
        PLATE,
        FIN,
        NOSECONE,
        CHEST
    }

    public static String[] tasks = {
            "Please remove your Data because you start at level 1",
            // für jeden Schritt wird eine Schraube und der Schraubenzieher benötigt der Schraubenzieher wird aber nicht verbraucht
            "Find and use 1x screwdriver with 1x screw and 1x body plate to repair the main body",
            "Find and attach the lower left fin with 1x screw to your rocket",
            "Find and attach 1x body plate with 1x screw to your rocket",
            "Find and attach the nosecone with 1x screw to your rocket",
            "Find 2x fuel canisters to fill up your rocket",
            "Move the chest to the alien or take it with your rocket ",
            "Use the key to enter and start your rocket",
            "Say Goodbye and get back home, Safely."
    };

    public static ENTITYTYPE[][] requiredItems = {
            {},
            {ENTITYTYPE.PLATE, ENTITYTYPE.SCREW, ENTITYTYPE.SCREWDRIVER},
            {ENTITYTYPE.FIN, ENTITYTYPE.SCREW, ENTITYTYPE.SCREWDRIVER},
            {ENTITYTYPE.PLATE, ENTITYTYPE.SCREW, ENTITYTYPE.SCREWDRIVER},
            {ENTITYTYPE.NOSECONE, ENTITYTYPE.SCREW, ENTITYTYPE.SCREWDRIVER},
            {ENTITYTYPE.FUEL, ENTITYTYPE.FUEL},
            {ENTITYTYPE.KEY},
    };

}
