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
                texture = manager.get("items/Wood.png", Texture.class);
                break;
            case STONE:
                texture = manager.get("items/Stone.png", Texture.class);
                break;
            case ENGINE:
                break;
            case HAMMER:
                break;
            default:
                break;
        }
    }
    public void draw(Batch batch, int slot, float width, int invslots, float scale, float y){
        Float middle = (Gdx.graphics.getWidth() / 2) - ((width/2)) + (width/invslots*slot) - (4*slot);
        batch.draw(texture, middle, y, texture.getWidth() / scale, texture.getHeight() / scale);
    }
}