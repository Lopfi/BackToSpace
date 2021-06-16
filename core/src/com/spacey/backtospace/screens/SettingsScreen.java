package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Datasave;

public class SettingsScreen extends ScreenAdapter {
    GameClass game;
    public Boolean musicmode = false; //if u want to remove all the data
    public Datasave saver;
    public Boolean deleteMode;
    final float firstLineY;

    public SettingsScreen(GameClass game) {
        this.game = game;
        deleteMode = false;
        saver = new Datasave();
        firstLineY = Gdx.graphics.getHeight() * .80f;
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

                if (keyCode == Input.Keys.ENTER && !deletemode && !musicmode) {
                    game.setScreen(new TitleScreen(game));
                }
                if (keyCode == Input.Keys.SPACE && !deletemode && !musicmode) {
                    game.setScreen(new GameScreen(game));
                }
                if (keyCode == Input.Keys.M && !deletemode && !musicmode) {
                    if (game.playMusic){
                        saver.write("music", !game.playMusic);
                        game.introSound.pause();
                        game.gameSound.pause();
                    } else {
                        if (game.gameSound == null){
                            musicmode = true;
                            game.assets.loadAssets(true);
                        } else {
                            saver.write("music", !game.playMusic);
                            game.introSound.play();
                            long SoundId = game.introSound.loop();
                            game.introSound.setVolume(SoundId,game.playVolume);
                        }
                    }
                    game.playMusic = !game.playMusic;
                }
                if (keyCode == Input.Keys.F && !musicmode) {
                    if(deletemode){
                        deletemode = false;
                    } else {
                        deletemode = true;
                    }
                }
                if (keyCode == Input.Keys.Y && !musicmode) {
                    if(deletemode){
                        saver.clear();
                        Gdx.app.exit();
                        //close game cuz else we get value errors to play the standard values need to be set on start
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .4f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float textX = Gdx.graphics.getWidth() * .25f;

        game.batch.begin();

        game.font.draw(game.batch, "SETTINGS", textX, getLineY(0));
        if (deleteMode){
            game.font.draw(game.batch, "!! WARNING !!", textX, getLineY(2));
            game.font.draw(game.batch, "Are you sure you want to delete all game date?", textX, getLineY(3));
            game.font.draw(game.batch, "[N] NO", textX, getLineY(5));
            game.font.draw(game.batch, "[Y] YES", textX, getLineY(6));
        } else if (musicmode){
        if(game.assets.manager.update()) {
            //music play logic
            if (game.playMusic){
                game.introSound = game.assets.manager.get("music/IntroMusic.mp3", Sound.class);
                game.gameSound = game.assets.manager.get("music/GameMusic.mp3", Sound.class);
                game.introSound.play();
                long SoundId = game.introSound.loop();
                game.introSound.setVolume(SoundId,game.playVolume);
                //mp3Sound.stop(id);
            }
            musicmode = false;
        }
        game.font.draw(game.batch, "LADE MUSIK DATEIEN..", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .56f);
        game.font.draw(game.batch, "Dies kann etwas dauern! (" + Math.round(game.assets.manager.getProgress()*100) + "%)", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .58f);
        game.font.draw(game.batch, "Bitte warte ein paar Sekunden du solltest automatisch zurück geleitet werden", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .32f);
    }
        else {
        game.font.draw(game.batch, "Music: <" + game.playMusic + "> [M] to change", textX, Gdx.graphics.getHeight() * .7f);
        game.font.draw(game.batch, "[D] Reset game data", textX, Gdx.graphics.getHeight() * .35f);
        game.font.draw(game.batch, "[SPACE/ESC] Continue", textX, Gdx.graphics.getHeight() * .28f);
        game.font.draw(game.batch, "[ENTER] Main Menu", textX, Gdx.graphics.getHeight() * .25f);
        }
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private float getLineY(float factor) {
        return firstLineY - (game.font.getLineHeight() + 10) * factor;
    }

}