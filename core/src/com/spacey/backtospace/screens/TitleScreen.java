package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.spacey.backtospace.GameClass;

public class TitleScreen extends ScreenAdapter {

    GameClass game;

    public TitleScreen(GameClass game) {
        this.game = game;
        game.isPaused = false;
    }

    @Override
    public void show(){
        if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
            //mp3Sound.stop(id);
        }

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
        game.font.draw(game.batch, "__ BACK TO SPACE __", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .80f);
        game.font.draw(game.batch, "Level: ["+game.safe.level+"]     Coins: ["+game.safe.coins+"]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Task:  Find all parts and bring them back to your rocket to repair it.", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .71f);
        game.font.draw(game.batch, "                            (Im Hintergrund kommt noch Simons cooler SplashScreen hin, aber hab das File nicht)", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "[H] Tutorial/Help", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .36f);
        game.font.draw(game.batch, "[D] Design/Shop", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .32f);
        game.font.draw(game.batch, "[S] Settings", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .44f);
        game.font.draw(game.batch, "[C] Credits", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(game.batch, "->  [SPACE] PLAY  <-", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .28f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}