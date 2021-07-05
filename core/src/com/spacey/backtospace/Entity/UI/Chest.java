package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Enums.ENTITYTYPE;
import com.badlogic.gdx.Input.Keys;

public class Chest extends UIElement {
    int wait = 0;
    boolean toChest = false;
    Integer[] transfer = {
            0, // Modus
            null,// Chestslot
            null, // Inventarslot
    };

    Object[][] chestItems = {
            //Enums.ENTITYTYPE, Integer
            {null, null},
            {null, null},
            {null, null},
            {null, null},
            {null, null},
            {null, null},
            {null, null},
            {null, null}
    };

    //0=no mode  1=itembar to chest 2=chest to itembar 3=DoTransaction
    Control control;
    GameClass game;
    BitmapFont font;

    public Chest(GameClass game, Control control) {
        super(game, game.assets.manager.get("ui/insidechest.png", Texture.class));
        pos.x = (Gdx.graphics.getWidth() / 5f); // divide by two because animated sprite sheet
        pos.y = (Gdx.graphics.getHeight() / 5f);
        width = (control.screenWidth / 6f) * 4;
        height = (control.screenHeight / 7f) * 5;
        this.control = control;
        this.game = game;
        font = new BitmapFont();
        font.setColor(Color.PURPLE);
        if (game.safe.chest != null) chestItems = game.safe.chest;
    }

    public ENTITYTYPE getChestItem(int slot) {
        Object[] OOitems = chestItems[slot];
        Object Oitem = OOitems[0];
        return (ENTITYTYPE) Oitem;
    }

    public int getchestItemcount(int cslot) {
        Object[] OOitems = chestItems[cslot];
        Object Ocount = OOitems[1];
        return (Integer) Ocount;
    }

    String[] getDirection(int mode) {
        String[] sv = {"???", "???"};
        if (mode == 1) {
            sv[0] = "INVENTAR";
            sv[1] = "CHEST";
        } else if (mode == 2) {
            sv[0] = "CHEST";
            sv[1] = "INVENTAR";
        }
        return sv;
    }

    public void transact(Player player) {
        if (wait != 0) return; //if still wait then dont do action
        ENTITYTYPE slotitem;
        if (player.inventory.items[transfer[2]] != null)
            slotitem = (ENTITYTYPE) player.inventory.items[transfer[2]].type;
        else slotitem = null;
        Object[] Ocheststuff = chestItems[transfer[1]];
        ENTITYTYPE chestitem = (ENTITYTYPE) Ocheststuff[0];
        int chestcount = (Integer) Ocheststuff[1];

        if (slotitem == null && chestitem == null) {
            //Info: you cant exchange nothing with nothing
        } else if (chestitem == null && slotitem != null) {
            player.inventory.remove(transfer[2]);
            chestItems[transfer[1]][1] = 1;
            chestItems[transfer[1]][0] = slotitem;

        } else if (slotitem == null && chestitem != null) {
            if (chestcount > 1) {
                player.inventory.add(new Item(chestitem, game), transfer[2]);
                chestItems[transfer[1]][1] = ((Integer) (chestItems[transfer[1]][1])) - 1;
                if (((Integer) (chestItems[transfer[1]][1])) == 0) chestItems[transfer[1]][0] = null;
            } else {
                player.inventory.add(new Item(chestitem, game), transfer[2]);
                chestItems[transfer[1]][1] = null;
                chestItems[transfer[1]][0] = null;
            }
        } else if (slotitem != null && chestitem != null) {
            if (chestitem == slotitem) {
                if (chestcount > 1) {
                    if (toChest) player.inventory.remove(transfer[2]);
                    if (toChest) chestItems[transfer[1]][1] = ((Integer) (chestItems[transfer[1]][1])) + 1;
                    //else //Info why would you exchange the same item with the same item
                } else {
                    player.inventory.remove(transfer[2]);
                    chestItems[transfer[1]][1] = ((Integer) (chestItems[transfer[1]][1])) + 1;
                }
            } else {
                if (chestcount > 1) {
                    //you cant exchange multiple items for a single item. LOL
                } else {
                    player.inventory.remove(transfer[2]);
                    chestItems[transfer[1]][0] = slotitem;
                    player.inventory.add(new Item(chestitem, game), transfer[2]);
                }
            }
        }
        transferReset();
        wait = 20;
    }

    public void transferReset() {
        transfer[0] = 0;
        transfer[1] = null;
        transfer[2] = null;
        toChest = false;
    }

    public void setTransfer(int cslot, int islot, int modus) {
        if (wait == 0) {
            transfer[0] = modus;
            transfer[1] = cslot;
            transfer[2] = islot;
        }
    }

    public void editTransfer(int slot, int transfernum, int modus) {
        if (wait == 0) {
            transfer[0] = modus;
            transfer[transfernum] = slot;
        }
    }

    public void act(SpriteBatch batch, Player player) {
        draw(batch);
        int Mode = (Integer) transfer[0];
        String[] text = getDirection(Mode);

        font.getData().setScale(.95f);
        font.draw(batch, text[0], this.pos.x + (36 * (this.width / 57)), this.pos.y + (4 * ((this.height) / 36)));
        font.draw(batch, text[1], this.pos.x + (49 * (this.width / 57)), this.pos.y + (4 * (this.height / 36)));

        font.getData().setScale(1.4f);
        if (wait > 0) {
            font.draw(batch, "WAIT ...", pos.x, pos.y);
            wait--;
        }
        for (int i = 0; i < chestItems.length; i++) {
            Object[] OOitems = chestItems[i];
            Object Oitem = OOitems[0];
            ENTITYTYPE item = (ENTITYTYPE) Oitem;
            Object Ocount = OOitems[1];
            Integer count = (Integer) Ocount;
            if (count == null) count = 0;
            float x;
            float x2;
            float y;
            float y2;
            if (i >= chestItems.length / 2 && i != 0) {
                x = this.pos.x + ((5 + (13 * (i - chestItems.length / 2f))) * (this.width / 57));
                x2 = this.pos.x + ((4 + (13 * (i - chestItems.length / 2f))) * (this.width / 57));
                y = this.pos.y + (10 * (this.height / 36));
                y2 = this.pos.y + (12 * (this.height / 36));
            } else {
                x = this.pos.x + ((5 + (13 * i)) * (this.width / 57));
                x2 = this.pos.x + ((4 + (13 * i)) * (this.width / 57));
                y = this.pos.y + (23 * (this.height / 36));
                y2 = this.pos.y + (25 * (this.height / 36));
            }
            font.draw(batch, "x" + count, x, y);
            if (item != null)
                batch.draw(new Item(item, game).texture, x2, y2, (10 * (this.width / 57)), (8 * (this.height / 36)));
        }

        if (Mode == 0) {
            if (control.isPressed(Keys.NUM_1)) editTransfer(0, 2, 1);
            if (control.isPressed(Keys.NUM_2)) editTransfer(1, 2, 1);
            if (control.isPressed(Keys.NUM_3)) editTransfer(2, 2, 1);
            if (control.isPressed(Keys.A)) editTransfer(0, 1, 2);
            if (control.isPressed(Keys.S)) editTransfer(1, 1, 2);
            if (control.isPressed(Keys.D)) editTransfer(2, 1, 2);
            if (control.isPressed(Keys.F)) editTransfer(3, 1, 2);
            if (control.isPressed(Keys.G)) editTransfer(4, 1, 2);
            if (control.isPressed(Keys.H)) editTransfer(5, 1, 2);
            if (control.isPressed(Keys.J)) editTransfer(6, 1, 2);
            if (control.isPressed(Keys.K)) editTransfer(7, 1, 2);

        } else if (Mode == 1) {
            if (control.isPressed(Keys.A)) editTransfer(0, 1, 3);
            if (control.isPressed(Keys.S)) editTransfer(1, 1, 3);
            if (control.isPressed(Keys.D)) editTransfer(2, 1, 3);
            if (control.isPressed(Keys.F)) editTransfer(3, 1, 3);
            if (control.isPressed(Keys.G)) editTransfer(4, 1, 3);
            if (control.isPressed(Keys.H)) editTransfer(5, 1, 3);
            if (control.isPressed(Keys.J)) editTransfer(6, 1, 3);
            if (control.isPressed(Keys.K)) editTransfer(7, 1, 3);
            toChest = true;

        } else if (Mode == 2) {
            if (control.isPressed(Keys.NUM_1)) editTransfer(0, 2, 3);
            if (control.isPressed(Keys.NUM_2)) editTransfer(1, 2, 3);
            if (control.isPressed(Keys.NUM_3)) editTransfer(2, 2, 3);
            toChest = false;

        } else if (Mode == 3) {
            transact(player);
        } else {
            transferReset();
        }
        if (control.isPressed(Keys.R) && wait == 0) {
            wait = 30;
            for (int x = 0; x < player.inventory.items.length; x++) {
                if (player.inventory.items[x] != null && player.inventory.items[x].type != null) {
                    for (int i = 0; i < chestItems.length; i++) {
                        if (chestItems[i][0] != null) {
                            if (chestItems[i][0] == player.inventory.items[x].type) {
                                player.inventory.remove(x);
                                chestItems[i][1] = ((int) chestItems[i][1]) + 1;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (control.isPressed(Keys.C)) {
            game.gameScreen.paused = false;
            game.gameScreen.chest = false;
            transferReset();

            //Save Operation
            game.safe.saveInventory(player.inventory);
            game.safe.saveChest(chestItems);
        }

    }
}