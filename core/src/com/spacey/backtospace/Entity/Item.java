package com.spacey.backtospace.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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
            case WOOD:
                texture = manager.get("items/Wood.png", Texture.class);
            case STONE:
                texture = manager.get("items/Stone.png", Texture.class);
        }
    }
}