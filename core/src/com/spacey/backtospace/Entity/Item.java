package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.Helper.Enums;

public class Item extends Entity{

    public Enums.ITEMTYPE type;

    public Item(Enums.ITEMTYPE type) {
        this.type = type;
        switch (type) {
            case FUEL:
        }
    }
}
