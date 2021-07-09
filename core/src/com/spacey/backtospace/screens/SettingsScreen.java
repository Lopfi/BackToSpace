package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.UI.Button;
import com.spacey.backtospace.Helper.Control;

public class SettingsScreen extends ScreenAdapter {
    GameClass game;
    public Boolean musicMode = false;
    public Boolean deleteMode; //if u want to remove all the data
    final float firstLineY;
    public long SoundId;
    float textX = Gdx.graphics.getWidth() * .25f;

    Control control;
    SpriteBatch batch;
    private final Matrix4 screenMatrix;
    
    Button home;
    Button play;

    public SettingsScreen(GameClass game) {
        this.game = game;
        deleteMode = false;
        firstLineY = Gdx.graphics.getHeight() * .88f;

        batch = game.batch;
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.camera);

        home = new Button(game, control, game.assets.manager.get("ui/buttons/home.png", Texture.class), true, textX*2, Gdx.graphics.getHeight() * .10f);
        play = new Button(game, control, game.assets.manager.get("ui/buttons/start.png", Texture.class), true, textX/2, Gdx.graphics.getHeight() * .10f);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .6f, .44f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!deleteMode && !musicMode) {
            if (control.isPressed(Keys.ENTER)) {
                game.setScreen(new TitleScreen(game));
            } else if (control.isPressed(Keys.C)) {
                game.safe.cutSzeneFinished = false;
                game.setScreen(new CutSceneScreen(game));
            } else if (control.isPressed(Keys.SPACE) ||control.isPressed(Keys.ESCAPE)) {
                game.setScreen(game.gameScreen);

            } else if (control.isPressed(Keys.UP)) {
                if(game.safe.playVolume < 1) game.safe.playVolume = ((int)(game.safe.playVolume*10) + (int)(.1f*10)) / 10f;
                if (game.safe.playMusic){
                    game.introSound.setVolume(SoundId,game.safe.playVolume);
                }
                game.safe.write("volume", game.safe.playVolume);

            } else if (control.isPressed(Keys.DOWN)) {
                if(game.safe.playVolume > 0) game.safe.playVolume = ((int)(game.safe.playVolume*10) - (int)(.1f*10)) / 10f;
                if (game.safe.playMusic){
                    game.introSound.setVolume(SoundId,game.safe.playVolume);
                }
                game.safe.write("volume", game.safe.playVolume);

            } else if (control.isPressed(Keys.T)) {
                game.safe.showTask = !game.safe.showTask;
                game.safe.write("showtask", game.safe.showTask);
            } else if (control.isPressed(Keys.M)) {
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
                        //game.introSound.play(); if music not loaded
                        SoundId = game.introSound.loop();
                        game.introSound.setVolume(SoundId,game.safe.playVolume);
                    }
                }
                game.safe.playMusic = !game.safe.playMusic;

            }  else if (control.isPressed(Keys.A)) {
                game.safe.coins = 999999;
                game.safe.life = 1;
                game.safe.save();

            } else if (control.isPressed(Keys.NUM_0) || control.isPressed(Keys.O)) {
                game.safe.coins = 0;
                game.safe.save();
            }
        }
        if (control.isPressed(Keys.R) && !musicMode) deleteMode = true;
        if (control.isPressed(Keys.N) && !musicMode) deleteMode = false;
        if (control.isPressed(Keys.Y) && !musicMode && deleteMode) {
            game.safe.clear();
            Gdx.app.exit();
            //close game cuz else we get value errors to play the standard values need to be set on start
        }

        batch.begin();
        batch.draw(game.assets.manager.get("screens/gears.png", Texture.class), Gdx.graphics.getWidth() -200, Gdx.graphics.getHeight() -200, 150, 150);
        game.font.getData().setScale(2);
        game.font.draw(batch, "SETTINGS:", textX, getLineY(0));
        game.font.getData().setScale(1);
        if (deleteMode){
            game.font.draw(batch, "!! WARNING !!", textX, getLineY(2));
            game.font.draw(batch, "Are you sure you want to delete all game date?", textX, getLineY(3));
            game.font.draw(batch, "[N] NO", textX, getLineY(5));
            game.font.draw(batch, "[Y] YES DELETE EVERYTHING AND CLOSE", textX, getLineY(6));
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
        game.font.draw(batch, "LOADING MUSIC FILES..", textX, getLineY(3));
        game.font.draw(batch, "Please wait a second (" + Math.round(game.assets.manager.getProgress()*100) + "%)", textX, getLineY(5));
        }
        else {// TODO: ADD BUTTON SUPPORT HERE @robin i can do that if u want
        play.update();
        home.update();
    
        //if (home.pressed) game.setScreen(game.gameScreen);
        //if (play.pressed) game.setScreen(new TitleScreen(game));
        game.font.draw(batch, "Music:        <" + game.safe.playMusic + "> [M] to toggle", textX, Gdx.graphics.getHeight() * .7f);
        game.font.draw(batch, "Volume:     <" + Math.round(game.safe.playVolume*10) + "> [UP][DOWN] to change", textX, Gdx.graphics.getHeight() * .66f);
        game.font.draw(batch, "GameTask: <" + game.safe.showTask + "> [T] to toggle", textX, Gdx.graphics.getHeight() * .62f);
        game.font.draw(batch, "Press [C] to play the Cutszene again", textX, Gdx.graphics.getHeight() * .38f);
        if (Control.debug) game.font.draw(batch, "Money:    <" + game.safe.coins + "> [A][O] All or zerO", textX, Gdx.graphics.getHeight() * .45f);
        game.font.draw(batch, "[R] Reset game data", textX, Gdx.graphics.getHeight() * .35f);
        game.font.draw(batch, "[SPACE/ESC]", textX/2, Gdx.graphics.getHeight() * .075f);
        game.font.draw(batch, "[ENTER]", textX*2, Gdx.graphics.getHeight() * .075f);
        
        if (control.isPressed(Keys.SPACE) || play.pressed) {
            game.setScreen(game.gameScreen);

        } 
        //control.keyDown(66)
        if (control.isPressed(Keys.ENTER) || home.pressed) {
            game.setScreen(new TitleScreen(game));

        }

        batch.setProjectionMatrix(screenMatrix);
        play.draw(batch);
        home.draw(batch);    
    }
        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private float getLineY(float factor) {
        return firstLineY - (game.font.getLineHeight() + 10) * factor;
    }

}