package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.DataSafe;

public class SettingsScreen extends ScreenAdapter {
    GameClass game;
    public Boolean musicMode = false;
    public Boolean deleteMode; //if u want to remove all the data
    final float firstLineY;
    public long SoundId;

    public SettingsScreen(GameClass game) {
        this.game = game;
        deleteMode = false;
        firstLineY = Gdx.graphics.getHeight() * .80f;
    }

    @Override
    public void show() {
        //load the music and play
        if (game.safe.playMusic){
            game.introSound.pause();
            game.gameSound.pause();
            SoundId = game.introSound.loop();
            game.introSound.setVolume(SoundId,game.safe.playVolume);
            //mp3Sound.stop(id);
        }

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {
                if (!deleteMode && !musicMode) {
                    if (keyCode == Input.Keys.ENTER) {
                        game.setScreen(new TitleScreen(game));
                    } else if (keyCode == Input.Keys.SPACE ||keyCode == Input.Keys.ESCAPE) {
                        game.setScreen(game.gameScreen);

                    } else if (keyCode == Input.Keys.UP) {
                        if(game.safe.playVolume < 1) game.safe.playVolume = ((int)(game.safe.playVolume*10) + (int)(.1f*10)) / 10f;
                        if (game.safe.playMusic){
                            game.introSound.setVolume(SoundId,game.safe.playVolume);
                        }
                        game.safe.write("volume", game.safe.playVolume);
    
                    } else if (keyCode == Input.Keys.DOWN) {
                        if(game.safe.playVolume > 0) game.safe.playVolume = ((int)(game.safe.playVolume*10) - (int)(.1f*10)) / 10f;
                        if (game.safe.playMusic){
                            game.introSound.setVolume(SoundId,game.safe.playVolume);
                        }
                        game.safe.write("volume", game.safe.playVolume);
    
                    } else if (keyCode == Input.Keys.T) {
                        game.safe.showTask = !game.safe.showTask;
                        game.safe.write("showtask", game.safe.showTask);
                    } else if (keyCode == Input.Keys.M) {
                        if (game.safe.playMusic){
                            game.safe.write("music", false);
                            game.introSound.pause();
                            game.gameSound.pause();
                        } else {
                            if (game.gameSound == null){
                                musicMode = true;
                                game.assets.loadMusic();
                            } else {
                                game.safe.write("music", true);
                                game.introSound.play();
                                long SoundId = game.introSound.loop();
                                game.introSound.setVolume(SoundId,game.safe.playVolume);
                            }
                        }
                        game.safe.playMusic = !game.safe.playMusic;

                    }  else if (keyCode == Input.Keys.A) {
                        game.safe.coins = 999999;
                        game.safe.life = 1;
                        game.safe.save();
                        
                    } else if (keyCode == Input.Keys.NUM_0 || keyCode == Input.Keys.O) {
                        game.safe.coins = 0;
                        game.safe.save();
                    }
                } 
                if (keyCode == Input.Keys.R && !musicMode) {
                    deleteMode = true;

                } 
                if (keyCode == Input.Keys.N && !musicMode) {
                    deleteMode = false;

                } 
                if (keyCode == Input.Keys.Y && !musicMode && deleteMode) {
                    game.safe.clear();
                    Gdx.app.exit();
                    //close game cuz else we get value errors to play the standard values need to be set on start
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
            game.font.draw(game.batch, "[Y] YES DELETE EVERYTHING AND CLOSE", textX, getLineY(6));
        } else if (musicMode){
        if(game.assets.manager.update()) {
            //music play logic
            game.safe.write("music", true);
            musicMode = false;
            if (game.safe.playMusic){ //IF YOU CAN READ, DONT DELETE THIS BECAUSE U NEED TO SAVE THE SOUNDID WHEN PLAYING TO CHANGE THE VOLUME!
                game.introSound = game.assets.manager.get("music/IntroMusic.mp3", Sound.class);
                String soundpath = game.safe.standardmusicPath;
                if (game.safe.readString("currenMusic") != "") {
                    soundpath = game.safe.readString("currenMusic"); // we cant access datasafe here cuz its requested in loadingscreen lmao
                }
                game.gameSound = game.assets.manager.get(soundpath, Sound.class);

                game.introSound.pause();
                game.gameSound.pause();
                SoundId = game.introSound.loop();
                game.introSound.setVolume(SoundId,game.safe.playVolume);
            }
        }
        game.font.draw(game.batch, "LOADING MUSIC FILES..", textX, getLineY(3));
        game.font.draw(game.batch, "Please wait a second (" + Math.round(game.assets.manager.getProgress()*100) + "%)", textX, getLineY(5));
        }
        else {// TODO: ADD BUTTON SUPPORT HERE @robin i can do that if u want
        game.font.draw(game.batch, "Music:        <" + game.safe.playMusic + "> [M] to toggle", textX, Gdx.graphics.getHeight() * .7f);
        game.font.draw(game.batch, "Volume:     <" + Math.round(game.safe.playVolume*10) + "> [UP][DOWN] to change", textX, Gdx.graphics.getHeight() * .66f);
        game.font.draw(game.batch, "GameTask: <" + game.safe.showTask + "> [T] to toggle", textX, Gdx.graphics.getHeight() * .62f);
        if (Control.debug) game.font.draw(game.batch, "Money:    <" + game.safe.coins + "> [A][O] All or zerO", textX, Gdx.graphics.getHeight() * .45f);
        game.font.draw(game.batch, "[R] Reset game data", textX, Gdx.graphics.getHeight() * .35f);
        game.font.draw(game.batch, "[SPACE/ESC] Play Game", textX, Gdx.graphics.getHeight() * .28f);
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