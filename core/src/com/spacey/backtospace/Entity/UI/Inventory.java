package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;

// store and display items
public class Inventory extends UIElement{

    public Item[] items;
    public int itemCount;

    public Inventory(int slots, GameClass game) {
        super(game, game.assets.manager.get("ui/inventory.png", Texture.class));
        items = new Item[slots];
        itemCount = 0;
        pos.x = (Gdx.graphics.getWidth() / 2f) - (width / 2); // divide by two because animated sprite sheet
        pos.y = 15;
    }

    public boolean addItem(Item item) {
        if (itemCount < items.length){
            items[itemCount] = item;
            itemCount++;
            return true;
        }
        return false;
    }

    public boolean addItem(Item item, int slot) {
        if (items[slot] == null){
            items[slot] = item;
            itemCount++;
            return true;
        }
        return false;
    }

    public void removeItem(Item item) {
        if (itemCount > 0) {
            for (int i = 0; i < items.length; i++) {
                if(items[i] != null && items[i].type == item.type) {
                    items[i] = null;
                    itemCount--;
                    return;
                }
            }
        }
    }
    public void removeItem(int slot) {
        if (items[slot] != null){
            items[slot] = null;
            itemCount--;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                items[i].drawInInv(batch, i, pos.x, pos.y);
            }
        }
    }
}