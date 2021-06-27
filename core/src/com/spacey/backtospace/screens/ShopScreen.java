package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.UI.Button;

public class ShopScreen extends ScreenAdapter {

    GameClass game;
    float stateTime;
    float swidth = Gdx.graphics.getWidth();
    public Player playershop1;
    public Player playershop2;
    public Player playershop3;
    
    Button btn1;
    Button back;

    Button btn2;
    Button btn3;
    Button btn4;

    Button buybtn2;
    Button buybtn3;
    Button buybtn4;

    Stage stage = new Stage();
    public ShopScreen(GameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (game.safe.life < 5 && game.safe.coins >= 8){
            InputListener action = new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if (game.safe.life < 5 && game.safe.coins >= 8){
                        game.safe.life++;
                        game.safe.coins = game.safe.coins - 8;
                        game.safe.save();
                        if (game.safe.life == 5) btn1.remove();
                    } else {
                        btn1.remove();
                    }
                    return true;
                }
            };
            btn1 = new Button(game, stage, "[H] 8 coins", action, 150, 50, 400, 60);
        }
        InputListener action2 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.safe.currentSkin = 0;
                game.safe.write("currentSkin", 0);
                return true;
            }
        };
        btn2 = new Button(game, stage, "[1] Select", action2, 150, 50, swidth/4*1-20, 330);
        
        InputListener action3 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if  (game.safe.skin1){
                    game.safe.currentSkin = 1;
                    game.safe.write("currentSkin", 1);
                } else {
                    Label style = btn3.getLabel();
                    if (game.safe.coins >= 20){
                        game.safe.coins = game.safe.coins - 20;
                        game.safe.write("skin1", true);
                        game.safe.save();
                        game.safe.skin1 = true;
                        style.setText("[2] Select");
                        btn3.setLabel(style);
                    } else {
                        style.setText("[2] get " + (20 - game.safe.coins) + " coins");
                        btn3.setLabel(style);
                    }
                }
                return true;
            }
        };
        if  (game.safe.skin1) btn3 = new Button(game, stage, "[2] Select", action3, 150, 50, swidth/4*2-20, 330);
        else btn3 = new Button(game, stage, "[2] 20 coins", action3, 150, 50, swidth/4*2-20, 330);

        InputListener action4 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if  (game.safe.skin2){
                    game.safe.currentSkin = 2;
                    game.safe.write("currentSkin", 2);
                } else {
                    Label style = btn4.getLabel();
                    if (game.safe.coins >= 35){
                        game.safe.coins = game.safe.coins - 35;
                        game.safe.write("skin2", true);
                        game.safe.save();
                        game.safe.skin2 = true;
                        style.setText("[3] Select");
                        btn4.setLabel(style);
                    } else {
                        style.setText("[3] get " + (20 - game.safe.coins) + " coins");
                        btn4.setLabel(style);
                    }
                }
                return true;
            }
        };
        if  (game.safe.skin1) btn4 = new Button(game, stage, "[3] Select", action4, 150, 50, swidth/4*3-20, 330);
        else btn4 = new Button(game, stage, "[3] 35 coins", action4, 150, 50, swidth/4*3-20, 330);

        InputListener backbtn = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        };
        back = new Button(game, stage, "[ENTER] Return to Titlescreen", backbtn, 280, 80, 900, 100);
        stage.addListener(new InputListener() 
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode) 
            {
                if (keycode == Input.Keys.NUM_1){
                    game.safe.currentSkin = 0;
                    game.safe.write("currentSkin", 0);
                } else if (keycode == Input.Keys.NUM_2){
                    if  (game.safe.skin1){
                        game.safe.currentSkin = 1;
                        game.safe.write("currentSkin", 1);
                    } else {
                        Label style = btn3.getLabel();
                        if (game.safe.coins >= 20){
                            game.safe.coins = game.safe.coins - 20;
                            game.safe.write("skin1", true);
                            game.safe.save();
                            game.safe.skin1 = true;
                            style.setText("[2] Select");
                            btn3.setLabel(style);
                        } else {
                            style.setText("[2] get " + (20 - game.safe.coins) + " coins");
                            btn3.setLabel(style);
                        }
                    }
                } else if (keycode == Input.Keys.NUM_3){
                    if  (game.safe.skin2){
                        game.safe.currentSkin = 2;
                        game.safe.write("currentSkin", 2);
                    } else {
                        Label style = btn4.getLabel();
                        if (game.safe.coins >= 35){
                            game.safe.coins = game.safe.coins - 35;
                            game.safe.write("skin2", true);
                            game.safe.save();
                            game.safe.skin2 = true;
                            style.setText("[3] Select");
                            btn4.setLabel(style);
                        } else {
                            style.setText("[3] get " + (35 - game.safe.coins) + " coins");
                            btn4.setLabel(style);
                        }
                    }
                } else if (keycode == Input.Keys.H){
                    if (game.safe.life < 5 && game.safe.coins >= 8){
                    game.safe.life++;
                    game.safe.coins = game.safe.coins - 8;
                    game.safe.save();
                    if (game.safe.life == 5) btn1.remove();
                    } else {
                        btn1.remove();
                    }
                } else if (keycode == Input.Keys.ENTER){
                    game.setScreen(new TitleScreen(game));
                }
                //Gdx.app.log("Image ClickListener", "keyDown. keycode=" + keycode);
                return true;
            }
        });

        playershop1 = new Player(new Vector3(swidth/4*1, 420, 0), game, game.assets.manager.get("player/Spaceman_walk0.png", Texture.class));
        playershop1.height = playershop1.height*4;
        playershop1.width = playershop1.width*4;

        playershop2 = new Player(new Vector3(swidth/4*2, 420, 0), game, game.assets.manager.get("player/Spaceman_walk1.png", Texture.class));
        playershop2.height = playershop2.height*4;
        playershop2.width = playershop2.width*4;

        playershop3 = new Player(new Vector3(swidth/4*3, 420, 0), game, game.assets.manager.get("player/Spaceman_walk2.png", Texture.class));
        playershop3.height = playershop3.height*4;
        playershop3.width = playershop3.width*4;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        game.batch.begin();
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "__THE SHOP:__", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .88f);
        game.batch.draw(game.assets.manager.get("menu/herz.png", Texture.class), 240, 50, 200, 200);
        game.font.draw(game.batch, "(" + (game.safe.life) + "x)", 310, 150);
        game.font.getData().setScale(1);
        game.font.draw(game.batch, "Current Coins: " + (game.safe.coins), Gdx.graphics.getWidth() * .7f, Gdx.graphics.getHeight() * .86f);
        if (game.safe.life >= 5) game.font.draw(game.batch, "you cant buy more", 420, 80);
        if (game.safe.coins < 8) game.font.draw(game.batch, "youre too poor :(", 420, 80);
        game.font.draw(game.batch, "[ACTIVE]", swidth/4*(game.safe.currentSkin+1), 400);

        stateTime += Gdx.graphics.getDeltaTime();
        playershop1.drawAnimation(game.batch, stateTime);
        playershop2.drawAnimation(game.batch, stateTime);
        playershop3.drawAnimation(game.batch, stateTime);
        
        game.batch.end();
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}

 