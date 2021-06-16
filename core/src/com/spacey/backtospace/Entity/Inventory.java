package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;

public class Inventory extends Entity{

    public Item[] items;
    public int itemCount;

    public Inventory(int slots, GameClass game) {
        super();
        items = new Item[slots];
        itemCount = 0;
        texture = game.assets.manager.get("player/Itembar.png", Texture.class);
        width = texture.getWidth() * game.uiScale;
        height = texture.getHeight() * game.uiScale;
        pos.x = Gdx.graphics.getWidth() / 2f - width / 2f;
        pos.y = 40;
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