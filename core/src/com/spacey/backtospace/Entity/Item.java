package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.spacey.backtospace.Entity.UI.UIElement;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;

public class Item extends UIElement {

    public Enums.ITEMTYPE type;

    public Item(Enums.ITEMTYPE type, GameClass game) {
        super(game);
        this.type = type;
        switch (type) {
            case FUEL:
                //texture = new Texture("items/Fuel.png");
                break;
            case WOOD:
                this.texture = this.initTexture(game.assets.manager.get("items/Wood10x8.png", Texture.class));
                break;
            case STONE:
                this.texture = this.initTexture(game.assets.manager.get("items/Stone10x8.png", Texture.class));
                break;
            case ENGINE:
                break;
            case HAMMER:
                break;
            default:
                break;
        }
    }
    public void drawInInv(Batch batch, int slot, float invX, float invY){
        int separator = 3 * 4;
        int slotWidth = 10 * 4;
        float x = invX + separator + slot * (separator + slotWidth);    //float x = invX + (4* scale) + (((width - (4*scale))/invSlots)*slot);
        batch.draw(texture, x, invY + 2 * 4, width, height);
    }
}