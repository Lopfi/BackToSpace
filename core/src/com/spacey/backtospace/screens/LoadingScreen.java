package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Datasave;

public class LoadingScreen extends ScreenAdapter {

    GameClass game;
    public LoadingScreen(GameClass game) {
        this.game = game;
    }

    @Override
    public void show(){
        Gdx.app.log("INFO", "Started Loading");
        //Check if standard values need to be set
        game.introSound = Gdx.audio.newSound(Gdx.files.internal("music/IntroMusic.mp3"));
        game.gameSound = Gdx.audio.newSound(Gdx.files.internal("music/GameMusic.mp3"));
        Datasave saver;
        saver = new Datasave();
        if (!saver.exists("init")){
            Gdx.app.log("INFO", "Save not found creating new.");
            saver.write("init", true);
            saver.write("music", true);
            saver.write("volume", 1);
            saver.write("coins", 0);

            saver.write("slot1", 0);
            saver.write("slot2", 0);
            saver.write("slot3", 0);
        }
        game.playMusic = saver.readBoolean("music");
        game.playVolume = saver.readInteger("volume");
        game.coins = saver.readInteger("coins");
        game.slot1 = saver.readInteger("slot1");
        game.slot2 = saver.readInteger("slot2");
        game.slot3 = saver.readInteger("slot3");
        Gdx.app.log("INFO", "Finished Loading");
        //music play logic
        if (game.playMusic){
            game.introSound.play();
            long SoundId = game.introSound.loop();
            game.introSound.setVolume(SoundId,game.playVolume);
            //mp3Sound.stop(id);
        }
        game.setScreen(new TitleScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "LOADING ...", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .80f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}