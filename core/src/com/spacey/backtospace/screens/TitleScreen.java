package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.GameClass;

public class TitleScreen extends ScreenAdapter {

    GameClass game;
    //Storytasks its basicly the mission you have to do! YAY :)
    public static String[] Tasks = {
        "Please remove your Data because you start at level 1",
        "Find the main body of your rocket and put it up", // für jeden schritt wird eine Schraube und der Schraubenzieher benötigt
        "Find and use a screwdriver with 1x screw and 1x body plate to repair the main body",
        "Find and attach the lower left fin with 1x screw to your rocket",
        "Find and attach 1x body plate with 1x screw to your rocket",
        "Find and attach the    nosecone with 1x screw to your rocket",
        "Find 2x fuel canisters to fill up your rocket",
        "Use the key to enter and start your rocket",
        "Say Goodbye and get back home, Safely."
    };
    public TitleScreen(GameClass game) {
        this.game = game;
        game.isPaused = false;
    }

    @Override
    public void show(){
        /*if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
            //mp3Sound.stop(id);
        }  THIS NEED TO BE REMOVE IN NEAR FUTURE*/

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(game.gameScreen);
                }
                if (keyCode == Input.Keys.C) {
                    game.setScreen(new CreditScreen(game));
                }
                if (keyCode == Input.Keys.S) {
                    game.setScreen(new SettingsScreen(game));
                }
                if (keyCode == Input.Keys.H) {
                    game.setScreen(new HelpScreen(game));
                }
                if (keyCode == Input.Keys.D) {
                    game.setScreen(new ShopScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(game.assets.manager.get("menu/background.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "__ BACK TO SPACE __", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .83f);
        game.font.getData().setScale(1);
        game.font.draw(game.batch, "Level: ["+game.safe.level+"]     Coins: ["+game.safe.coins+"]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Task:  " + Tasks[game.safe.level], Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .71f);
        game.font.draw(game.batch, "[H] Tutorial/Help", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .36f);
        game.font.draw(game.batch, "[D] Design/Shop", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .32f);
        game.font.draw(game.batch, "[S] Settings", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .44f);
        game.font.draw(game.batch, "[C] Credits", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(game.batch, "->  [SPACE] PLAY  <-", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}