package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;

import java.util.Arrays;

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

    public boolean add(Item item) {
        if (itemCount < items.length){
            items[itemCount] = item;
            itemCount++;
            return true;
        }
        return false;
    }

    public boolean add(Item item, int slot) {
        if (items[slot] == null){
            items[slot] = item;
            itemCount++;
            return true;
        }
        return false;
    }

    public boolean remove(Item item) {
        if (itemCount > 0) {
            for (int i = 0; i < items.length; i++) {
                if(items[i] != null && items[i].type == item.type) {
                    items[i] = null;
                    itemCount--;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean remove(Enums.ENTITYTYPE type) {
        if (itemCount > 0) {
            for (int i = 0; i < items.length; i++) {
                if(items[i] != null && items[i].type == type) {
                    items[i] = null;
                    itemCount--;
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean remove(int slot) {
        if (items[slot] != null){
            items[slot] = null;
            itemCount--;
            return true;
        }
        return false;
    }

    public Boolean remove(Enums.ENTITYTYPE[] items) {
        for(Enums.ENTITYTYPE i: items) {
            if (!remove(i)) return false;
        }
        return true;
    }

    private Enums.ENTITYTYPE[] types() {
        Enums.ENTITYTYPE[] itemTypes = new Enums.ENTITYTYPE[itemCount];
        for (int i = 0; i < itemCount; i++) {
            itemTypes[i] = items[i].type;
        }
        return itemTypes;
    }

    public Boolean has(Enums.ENTITYTYPE[] required) {
        return Arrays.asList(types()).containsAll(Arrays.asList(required));
    }

    public void clear() {
        for (int i = 0; i < itemCount; i++) {
            items[i] = null;
            itemCount = 0;
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