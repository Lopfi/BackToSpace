package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;

public class Item extends Entity{

    public Enums.ITEMTYPE type;

    public Item(Enums.ITEMTYPE type, AssetManager manager) {
        super();
        this.type = type;
        switch (type) {
            case FUEL:
                //texture = new Texture("items/Fuel.png");
                break;
            case WOOD:
                texture = manager.get("items/Wood10x8.png", Texture.class);
                break;
            case STONE:
                texture = manager.get("items/Stone10x8.png", Texture.class);
                break;
            case ENGINE:
                break;
            case HAMMER:
                break;
            default:
                break;
        }
    }
    public void draw(Batch batch, int slot, float width, int invslots, float scale, float x, float y){
        float middle = x + (3*scale) + (((width - (3*scale))/invslots)*slot);
        batch.draw(texture, middle, y+8, texture.getWidth() * scale, texture.getHeight() * scale);
    }
}