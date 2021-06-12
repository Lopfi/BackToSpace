package com.spacey.backtospace.map;

import com.spacey.backtospace.Enums.TILETYPE;
import com.spacey.backtospace.Entity;
import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity {
    public int row;
    public int col;
    public TILETYPE type;
    
    public Tile(float x, float y, int size, TILETYPE type, Texture texture){
        super();
        pos.x = x*size;
        pos.y = y*size;
        this.size = size;
        width = size;
        height = size;
        this.texture = texture;
        this.row = (int) x;
        this.col = (int) y;
        this.type = type;
    }

    public String details(){
        return "x: " + pos.x + " y: " + pos.y + " row: " + row + " col: " + col + " type: " + type.toString();
    }

    public boolean is_ground() {
        return type == TILETYPE.GROUND;
    }
    
    public boolean is_border() {
        return type == TILETYPE.BORDER;
    }

    public boolean isNotPassable() {
        return is_border();
    }
    
}
