package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;

public class Inventory extends Entity{

    public Item[] items;
    public int itemCount;
    float scale;

    public Inventory(int slots, GameClass game) {
        super();
        scale = game.uiScale;
        items = new Item[slots];
        itemCount = 0;
        texture = game.assets.manager.get("player/Itembar.png", Texture.class);
        width = texture.getWidth() * scale;
        height = texture.getHeight() * scale;
        pos.x = (Gdx.graphics.getWidth() / 2f)- (width/2);
        pos.y = 15;
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

    public void removeItem(Item item, boolean onlyone) {
        if (itemCount > 0) {
            for (int i = 0; i < items.length; i++) {
                if(items[i] != null && items[i].type == item.type) {
                    items[i] = null;
                    itemCount--;
                    if (onlyone) break;
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
                items[i].draw(batch, i, width, items.length, scale, pos.x, pos.y);
            }
        }
    }
}