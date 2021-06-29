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
        "Unlock the Spaceship with the key", // then some info like uh ah u cant fly cuz its broken
        "Find     2x fuel     (1/4)",
        "Find   5x Screws     (2/4)",
        "Find 1x Screwdriver  (3/4)",
        "Now repair the ship  (4/4)",
        "Defend yourself from the anrgy aliens",
        "Follow the alien and generate Oxygen",
        "Help the Aliens rebuild their planet that you destroyed", //or they destroy earth
        "Get enough Oxygen & Food & Water for your Journey back",
        "Say Goodbye and come home, Safely."
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