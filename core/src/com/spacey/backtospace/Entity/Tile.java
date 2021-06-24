package com.spacey.backtospace.Entity;

import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity {

    private TILETYPE type;
    
    public Tile(float x, float y, TILETYPE type, Texture texture){
        super();
        this.texture = initTexture(texture);
        this.type = type;
        pos.x = x * width;
        pos.y = y * height;
    }

    public boolean isNotPassable() {
        return type == TILETYPE.BORDER;
    }
    
}
