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
    Boolean toChest = null;
    Integer[] transfer = {
            0, //modus
            null,//chestslot
            null, //inventarslot
    };
    Object[][] chestitems = {
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
    //0=no mode  1=itembar to chest  2= chest to itembar 3=DoTransaction//itemtype //itemcount
    Control control;
    GameClass game;
    BitmapFont font;

    public Chest(GameClass game, Control control) {
        super(game, game.assets.manager.get("ui/insidechest.png", Texture.class));
        this.control = control;
        this.game = game;

        pos.x = (Gdx.graphics.getWidth() / 5f); // divide by two because animated sprite sheet
        pos.y = (Gdx.graphics.getHeight() / 5f);
        width = (control.screenWidth / 6f) * 4;
        height = (control.screenHeight / 7f) * 5;
        font = new BitmapFont();
        font.setColor(Color.PURPLE);
        if (game.safe.chest != null) chestitems = game.safe.chest;
    }

    public ENTITYTYPE getChestItem(int slot) {
        return (ENTITYTYPE) chestitems[slot][0];
    }

    public int getChestItemCount(int slot) {
        return (int) chestitems[slot][1];
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
        ENTITYTYPE slotItem;
        if (player.inventory.items[transfer[2]] != null)
            slotItem = (ENTITYTYPE) player.inventory.items[transfer[2]].type;
        else slotItem = null;
        ENTITYTYPE chestItem = (ENTITYTYPE) chestitems[transfer[1]][0];
        Integer count = (Integer) chestitems[transfer[1]][1];

        if (slotItem == null && chestItem == null) {
            //Info: you cant exchange nothing with nothing
        } else if (chestItem == null && slotItem != null) {
            player.inventory.remove(transfer[2]);
            chestitems[transfer[1]][1] = 1;
            chestitems[transfer[1]][0] = slotItem;

        } else if (slotItem == null && chestItem != null) {
            player.inventory.add(new Item(chestItem, game), transfer[2]);
            if (count > 1) {
                chestitems[transfer[1]][1] = ((Integer) (chestitems[transfer[1]][1])) - 1;
                if (((Integer) (chestitems[transfer[1]][1])) == 0) chestitems[transfer[1]][0] = null;
            } else {
                chestitems[transfer[1]][1] = null;
                chestitems[transfer[1]][0] = null;
            }
        } else if (slotItem != null && chestItem != null) {
            if (chestItem == slotItem) {
                if (count > 1) {
                    if (toChest) player.inventory.remove(transfer[2]);
                    if (toChest) chestitems[transfer[1]][1] = ((Integer) (chestitems[transfer[1]][1])) + 1;
                    //else //Info why would you exchange the same item with the same item
                } else {
                    player.inventory.remove(transfer[2]);
                    chestitems[transfer[1]][1] = ((Integer) (chestitems[transfer[1]][1])) + 1;
                }
            } else {
                if (count > 1) {
                    //you cant exchange multiple items for a single item. LOL
                } else {
                    player.inventory.remove(transfer[2]);
                    chestitems[transfer[1]][0] = slotItem;
                    player.inventory.add(new Item(chestItem, game), transfer[2]);
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
        toChest = null;
    }

    public void setTransfer(int cSlot, int iSlot, int mode) {
        if (wait == 0) {
            transfer[0] = mode;
            transfer[1] = cSlot;
            transfer[2] = iSlot;
        }
    }

    public void editTransfer(int slot, int transferNum, int mode) {
        if (wait == 0) {
            transfer[0] = mode;
            transfer[transferNum] = slot;
        }
    }

    public void act(SpriteBatch batch, Player player) {
        draw(batch);
        Integer Mode = (Integer) transfer[0];
        String[] text = getDirection(Mode);

        font.getData().setScale(.95f);
        font.draw(batch, text[0], this.pos.x + (36 * (this.width / 57)), this.pos.y + (4 * ((this.height) / 36)));
        font.draw(batch, text[1], this.pos.x + (49 * (this.width / 57)), this.pos.y + (4 * (this.height / 36)));

        font.getData().setScale(1.4f);
        if (wait > 0) {
            font.draw(batch, "WAIT ...", pos.x, pos.y);
            wait--;
        }
        for (int i = 0; i < chestitems.length; i++) {
            ENTITYTYPE item = (ENTITYTYPE) chestitems[i][0];
            Integer count = (Integer) chestitems[i][1];
            if (count == null) count = 0;
            float x;
            float x2;
            float y;
            float y2;
            if (i >= chestitems.length / 2 && i != 0) {
                x = this.pos.x + ((5 + (13 * (i - chestitems.length / 2f))) * (this.width / 57));
                x2 = this.pos.x + ((4 + (13 * (i - chestitems.length / 2f))) * (this.width / 57));
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
                    for (int i = 0; i < chestitems.length; i++) {
                        if (chestitems[i][0] != null) {
                            if (chestitems[i][0] == player.inventory.items[x].type) {
                                player.inventory.remove(x);
                                chestitems[i][1] = ((int) chestitems[i][1]) + 1;
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
            game.safe.saveChest(chestitems);
        }

    }
}