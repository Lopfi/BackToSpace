package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    Button btn2;
    Button btn3;
    Button btn4;
    Button back;

    Stage stage = new Stage();
    public ShopScreen(GameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        InputListener action = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON1", "Pressed Text Button");
                //game.coins++;
                return true;
            }
        };
        btn1 = new Button(game, stage, "[H] 8 coins", action, 150, 50, 400, 60);

        InputListener action2 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON1", "Pressed Text Button");
                //game.coins++;
                return true;
            }
        };
        btn2 = new Button(game, stage, " >Select< ", action2, 150, 50, swidth/4*1-50, 330);

        InputListener action3 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON1", "Pressed Text Button");
                btn2.remove();
                return true;
            }
        };
        btn3 = new Button(game, stage, "Buy 20 coins", action3, 150, 50, swidth/4*2-50, 330);

        InputListener action4 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON1", "Pressed Text Button");
                //game.coins++;
                return true;
            }
        };
        btn4 = new Button(game, stage, "Buy 30 coins", action4, 150, 50, swidth/4*3-50, 330);

        InputListener backbtn = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON2", "Pressed Text Button");
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
                if (keycode == Input.Keys.C){
                    //fakeCoins++;
                } else if (keycode == Input.Keys.F){
                    game.safe.life = game.safe.life + 1;
                } else if (keycode == Input.Keys.ENTER){
                    game.setScreen(new TitleScreen(game));
                }
                //Gdx.app.log("Image ClickListener", "keyDown. keycode=" + keycode);
                return true;
            }
        });

        playershop1 = new Player(new Vector3(swidth/4*1, 420, 0), game);
        playershop1.height = playershop1.height*4;
        playershop1.width = playershop1.width*4;

        playershop2 = new Player(new Vector3(swidth/4*2, 420, 0), game, game.assets.manager.get("player/Spaceman_walk2.png", Texture.class));
        playershop2.height = playershop2.height*4;
        playershop2.width = playershop2.width*4;

        playershop3 = new Player(new Vector3(swidth/4*3, 420, 0), game, game.assets.manager.get("player/Spaceman_walk3.png", Texture.class));
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
        game.font.draw(game.batch, "Current Coins: " + (game.safe.coins), Gdx.graphics.getWidth() * .7f, Gdx.graphics.getHeight() * .85f);

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

 