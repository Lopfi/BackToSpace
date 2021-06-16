package com.spacey.backtospace.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory extends Entity{

    public Item[] items;
    public int itemCount;

    public Inventory(int slots, AssetManager manager) {
        super();
        items = new Item[slots];
        itemCount = 0;
        texture = manager.get("player/Itembar.png", Texture.class);
        width = texture.getWidth();
        height = texture.getHeight();
    }

    public void addItem(Item item) {
        if (itemCount < items.length){
            items[itemCount] = item;
            itemCount++;
        }
    }

    public void addItem(Item item, int slot) {
        if (items[slot] == null){
            items[slot] = item;
            itemCount++;
        }
    }

    public void removeItem(Item item) {
        if (itemCount > 0) {
            for (int i = 0; i < items.length; i++) {
                if(items[i] == item) {
                    items[i] = null;
                    itemCount--;
                    return;
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (Item item : items) {
            if (item != null) {
                item.draw(batch);
            }
        }
    }
}