package com.spacey.backtospace.Entity;

import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity {
    public int row;
    public int col;
    private final TILETYPE type;
    
    public Tile(float x, float y, TILETYPE type, Texture texture){
        super();
        this.texture = texture;
        width = texture.getWidth();
        height = texture.getHeight();
        pos.x = x*width;
        pos.y = y*height;
        this.row = (int) x;
        this.col = (int) y;
        this.type = type;
    }

    public String details(){
        return "x: " + pos.x + " y: " + pos.y + " row: " + row + " col: " + col + " type: " + type.toString();
    }

    public boolean isNotPassable() {
        return type == TILETYPE.BORDER;
    }
    
}
