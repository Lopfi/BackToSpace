package com.spacey.backtospace.Entity;

import com.spacey.backtospace.Helper.Enums;

public class Inventory extends Entity{

    public Enums.ITEMTYPE[] items;
    public int itemCount;

    public Inventory(int slots) {
        items = new Enums.ITEMTYPE[slots];
        itemCount = 0;
    }

    public void addItem(Enums.ITEMTYPE item) {
        if (itemCount < items.length){
            items[itemCount] = item;
            itemCount++;
        }
    }

    public void removeItem(Enums.ITEMTYPE item) {
        if (itemCount > 0) {
            for (int i = 0; i < itemCount; i++) {
                if(items[i] == item) {
                    items[i] = null;
                    itemCount--;
                    return;
                }
            }
        }
    }

    public void draw() {

    }
}
