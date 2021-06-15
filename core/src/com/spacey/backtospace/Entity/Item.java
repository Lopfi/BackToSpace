package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.Helper.Enums;

public class Item extends Entity{

    public Enums.ITEMTYPE type;

    public Item(Enums.ITEMTYPE type) {
        super();
        this.type = type;
        switch (type) {
            case FUEL:
                //texture = new Texture("items/Fuel.png");
            case WOOD:
                texture = new Texture("items/Wood.png");
            case STONE:
                texture = new Texture("items/Stone.png");
        }
    }
}