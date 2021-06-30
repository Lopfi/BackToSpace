package com.spacey.backtospace.Entity;

import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity {

    private TILETYPE tileType;
    
    public Tile(float x, float y, TILETYPE tileType, Texture texture){
        super(texture);
        type = Enums.ENTITYTYPE.TILE;
        this.tileType = tileType;
        pos.x = x * width;
        pos.y = y * height;
    }

    public boolean isNotPassable() {
        return tileType == TILETYPE.BORDER;
    }
    
}
