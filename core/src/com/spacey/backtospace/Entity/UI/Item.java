package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;

// store and display different item types in the inventory
public class Item extends UIElement {

    public Enums.ENTITYTYPE type;

    public Item(Enums.ENTITYTYPE type, GameClass game) {
        super(game);
        this.type = type;
        switch (type) {
            case PLATE:
                texture = initTexture(game.assets.manager.get("items/plate.png", Texture.class));
                break;
            case NOSECONE:
                texture = initTexture(game.assets.manager.get("items/nosecone.png", Texture.class));
                break;
            case FIN:
                texture = initTexture(game.assets.manager.get("items/fin.png", Texture.class));
                break;
            case FUEL:
                texture = initTexture(game.assets.manager.get("items/fuel.png", Texture.class));
                break;
            case STONE:
                texture = initTexture(game.assets.manager.get("items/stone.png", Texture.class));
                break;
            case SCREW:
                texture = initTexture(game.assets.manager.get("items/screw.png", Texture.class));
                break;
            case SCREWDRIVER:
                texture = initTexture(game.assets.manager.get("items/screwdriver.png", Texture.class));
                break;
            case KEY:
                texture = initTexture(game.assets.manager.get("items/key.png", Texture.class));
                break;
            default:
                break;
        }
    }
    public void drawInInv(SpriteBatch batch, int slot, float invX, float invY){
        // TODO make nicer and more dynamic again
        float separator = 3 * scale; // the space between two slots
        float slotWidth = 10 * scale;
        pos.x = invX + separator + slot * (separator + slotWidth);    //float x = invX + (4* scale) + (((width - (4*scale))/invSlots)*slot);
        pos.y = invY + 2 * scale;
        draw(batch);
    }
}