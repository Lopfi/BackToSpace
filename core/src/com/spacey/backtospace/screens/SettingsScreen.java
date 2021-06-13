package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Datasave;

public class SettingsScreen extends ScreenAdapter {
    GameClass game;
    public Datasave saver = new Datasave();

    public SettingsScreen(GameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        //load the music and play
        if (game.playMusic){
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.introSound.loop();
            game.introSound.setVolume(SoundId,game.playVolume);
            //mp3Sound.stop(id);
        }

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new TitleScreen(game));
                }
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                }
                if (keyCode == Input.Keys.M) {
                    game.playMusic = !game.playMusic;
                    saver.write("music", game.playMusic);
                    game.introSound.pause();
                    game.gameSound.pause();
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .4f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "EINSTELLUNGEN", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .77f);
        game.font.draw(game.batch, "Sound: [" + game.playMusic + "] Drücke >m< zum ändern", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .7f);
        game.font.draw(game.batch, "Drücke Leertaste um fortzufahren", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(game.batch, "Drücke Enter für den Hauptbildschirm", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}