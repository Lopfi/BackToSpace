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
                this.texture = this.initTexture(manager.get("items/Wood10x8.png", Texture.class));
                break;
            case STONE:
                this.texture = this.initTexture(manager.get("items/Stone10x8.png", Texture.class));
                break;
            case ENGINE:
                break;
            case HAMMER:
                break;
            default:
                break;
        }
    }
    public void drawInInv(Batch batch, int slot, float width, int invSlots, float scale, float x, float y){
        float middle = x + (4*scale) + (((width - (4*scale))/invSlots)*slot);
        batch.draw(texture, middle, y + (3*scale), width * scale, height * scale);
    }
}