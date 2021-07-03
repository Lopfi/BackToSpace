package com.spacey.backtospace.Entity.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.Structure;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.Helper.Enums.ENTITYTYPE;
import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Chest extends UIElement{
    Integer[] transfer = {
        Integer.valueOf(0), //modus
        null,//chestslot
        null, //inventarslot
    };
    Object[][] chestitems = {
        {
            //Enums.ENTITYTYPE, Integer
            ENTITYTYPE.SCREW, 1
        },
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
        pos.x = (Gdx.graphics.getWidth() / 5); // divide by two because animated sprite sheet
        pos.y = (Gdx.graphics.getHeight() / 5);
        width = (control.screenWidth / 6f) * 4;
        height = (control.screenHeight / 7f) * 5;
        this.control = control;
        this.game = game;
        font = new BitmapFont();
        font.setColor(Color.PURPLE);
    }

    public ENTITYTYPE getchestItem(int cslot){
        Object OOitems[] = chestitems[cslot];
        Object Oitem = OOitems[0];
        ENTITYTYPE item = (ENTITYTYPE) Oitem;
        return item;
    }
    public Integer getchestItemcount(int cslot){
        Object OOitems[] = chestitems[cslot];
        Object Ocount = OOitems[1];
        Integer count = (Integer) Ocount;
        return count;
    }

    String[] getDirection(int mode){
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

    public void transact(Player player){
        ENTITYTYPE slotitem;
        if (player.inventory.items[transfer[2]] != null) slotitem = (ENTITYTYPE) player.inventory.items[transfer[2]].type;
        else slotitem = null;
        Object Ocheststuff[] = chestitems[transfer[1]];
        ENTITYTYPE chestitem = (ENTITYTYPE) Ocheststuff[0];
        Integer chestcount = (Integer) Ocheststuff[1];

        if (slotitem == null && chestitem == null) {
            //Info: you cant exchange nothing with nothing
        } else if (chestitem == null && slotitem != null){
            player.inventory.remove(transfer[2]);
            chestitems[transfer[1]][1] = Integer.valueOf(1);
            chestitems[transfer[1]][0] = slotitem;

        } else if (slotitem == null && chestitem != null){
            if (chestcount > 1){
                player.inventory.add(new Item(chestitem, game), transfer[2]);
                chestitems[transfer[1]][1] = ((Integer)(chestitems[transfer[1]][1]))-1;
                if (((Integer)(chestitems[transfer[1]][1]))-1 == 0) chestitems[transfer[1]][0] = null;
            } else {
                player.inventory.add(new Item(chestitem, game), transfer[2]);
                chestitems[transfer[1]][1] = null;
                chestitems[transfer[1]][0] = null;
            }
        } else if (slotitem != null && chestitem != null) {
                if (chestitem == slotitem){
                    player.inventory.remove(transfer[2]);
                    chestitems[transfer[1]][1] = ((Integer)(chestitems[transfer[1]][1]))+1;
                } else {
                    player.inventory.remove(transfer[2]);
                    chestitems[transfer[1]][0] = slotitem;
                    player.inventory.add(new Item(chestitem, game), transfer[2]);
                }
        }
        Gdx.app.log("tag", slotitem +"//"+ chestitem + " - " + chestcount);
        transferReset();
    }

    public void transferReset(){
        transfer[0] = Integer.valueOf(0);
        transfer[1] = null;
        transfer[2] = null;
    }
    public void setTransfer(int cslot, int islot, int modus){
        transfer[0] = modus;
        transfer[1] = cslot;
        transfer[2] = islot;
    }
    public void editTransfer(int slot, int transfernum, int modus){
        transfer[0] = modus;
        transfer[transfernum] = slot;
    }

    public void act(SpriteBatch batch, Player player){
        draw(batch);
        Integer Mode = (Integer) transfer[0];
        String[] text = getDirection(Mode);

        font.getData().setScale(.95f);
        font.draw(batch, text[0], this.pos.x + (36*(this.width/57)), this.pos.y + (4*((this.height)/36)));
        font.draw(batch, text[1], this.pos.x + (49*(this.width/57)), this.pos.y + (4*(this.height/36)));

        font.getData().setScale(1.4f);
        //font.draw(batch, "This is a Information", pos.x, pos.y);
        for (int i = 0; i < chestitems.length; i++) {
            Object OOitems[] = chestitems[i];
            Object Oitem = OOitems[0];
            ENTITYTYPE item = (ENTITYTYPE) Oitem;
            Object Ocount = OOitems[1];
            Integer count = (Integer) Ocount;
            if (count == null) count = 0;
            if (i >= chestitems.length/2 && i != 0){
                float x = this.pos.x + ((5+(13*(i-chestitems.length/2)))*(this.width/57));
                float x2 = this.pos.x + ((4+(13*(i-chestitems.length/2)))*(this.width/57));
                float y = this.pos.y + (10*(this.height/36));
                float y2 = this.pos.y + (12*(this.height/36));
                font.draw(batch, "x" + count, x, y);
                if (item != null) batch.draw(new Item(item, game).texture, x2, y2, (10*(this.width/57)), (8*(this.height/36)));
            } else {
                float x = this.pos.x + ((5+(13*i))*(this.width/57));
                float x2 = this.pos.x + ((4+(13*i))*(this.width/57));
                float y = this.pos.y + (23*(this.height/36));
                float y2 = this.pos.y + (25*(this.height/36));
                font.draw(batch, "x" + count, x, y);
                if (item != null) batch.draw(new Item(item, game).texture, x2, y2, (10*(this.width/57)), (8*(this.height/36)));
            }
        }

            if (Mode == 0){
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

            } else if (Mode == 1){
                if (control.isPressed(Keys.A)) editTransfer(0, 1, 3);
                if (control.isPressed(Keys.S)) editTransfer(1, 1, 3);
                if (control.isPressed(Keys.D)) editTransfer(2, 1, 3);
                if (control.isPressed(Keys.F)) editTransfer(3, 1, 3);
                if (control.isPressed(Keys.G)) editTransfer(4, 1, 3);
                if (control.isPressed(Keys.H)) editTransfer(5, 1, 3);
                if (control.isPressed(Keys.J)) editTransfer(6, 1, 3);
                if (control.isPressed(Keys.K)) editTransfer(7, 1, 3);

            } else if (Mode == 2){
                if (control.isPressed(Keys.NUM_1)) editTransfer(0, 2, 3);
                if (control.isPressed(Keys.NUM_2)) editTransfer(1, 2, 3);
                if (control.isPressed(Keys.NUM_3)) editTransfer(2, 2, 3);

            } else if (Mode == 3){
                transact(player);
            } else {
                transferReset();
            }

            System.out.println(transfer[0] +"-"+ transfer[1] +"-"+ transfer[2]);

            if (control.isPressed(Keys.R)) transferReset();
            if (control.isPressed(Keys.C)) {
                game.isPaused = false;
                game.chestmode = false;
                transfer[0] = 0;
                transfer[1] = null;
                transfer[2] = null;
            }

    }
}