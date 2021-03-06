package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import java.util.ArrayList;
import java.util.Arrays;

// store and display items
public class Inventory extends UIElement{

    public Item[] items;
    public int itemCount;

    public Inventory(int slots, GameClass game) {
        super(game, game.assets.manager.get("ui/inventory.png", Texture.class));
        items = new Item[slots];
        if (game.safe.items != null) items = game.safe.items;//prevent nullpointerexeption
        itemCount = ((int)game.safe.itemCount);
        pos.x = (Gdx.graphics.getWidth() / 2f) - (width / 2); // divide by two because animated sprite sheet
        pos.y = 15;
    }

    public boolean add(Item item) {
        if (itemCount < items.length){
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    items[i] = item;
                    itemCount++;
                    return true;
                }
            }
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

    public Boolean remove(Enums.ENTITYTYPE[] types) {
        for(Enums.ENTITYTYPE i: types) {
            if (i != Enums.ENTITYTYPE.SCREWDRIVER) if (!remove(i)) return false;
        }
        return true;
    }

    private Enums.ENTITYTYPE[] types() {
        Enums.ENTITYTYPE[] itemTypes = new Enums.ENTITYTYPE[items.length];
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) itemTypes[i] = items[i].type;
        }
        return itemTypes;
    }

    public Boolean has(Enums.ENTITYTYPE[] required) {
        return Arrays.asList(types()).containsAll(Arrays.asList(required));
    }

    public Boolean has(Enums.ENTITYTYPE required) {
        return Arrays.asList(types()).contains(required);
    }

    public void clear() {
        for (int i = 0; i < itemCount; i++) {
            items[i] = null;
            itemCount = 0;
        }
    }

    public String missing(Enums.ENTITYTYPE[] required) {
        ArrayList<Enums.ENTITYTYPE> missing = new ArrayList<>();
        for (Enums.ENTITYTYPE type : required) if (!has(type)) missing.add(type);

        StringBuilder out = new StringBuilder();
        for (Enums.ENTITYTYPE type : missing) out.append(type.toString()).append(",");

        return out.toString();
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