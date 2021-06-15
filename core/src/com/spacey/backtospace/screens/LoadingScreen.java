package com.spacey.backtospace.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.AssetLoad;
import com.spacey.backtospace.Helper.Datasave;

public class LoadingScreen extends ScreenAdapter {
    public AssetLoad station = new AssetLoad();
    GameClass game;
    public LoadingScreen(GameClass game) {
        this.game = game;
    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    @Override
    public void show(){
        Gdx.app.log("INFO", "Started Loading");
        //Check if standard values need to be set
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        Datasave saver;
        saver = new Datasave();
        if (!saver.exists("init")){
            Gdx.app.log("INFO", "Save not found creating new.");
            saver.write("init", true);
            saver.write("music", true);
            saver.write("volume", 1);
            saver.write("coins", 0);
            saver.write("level", 1);

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
        game.level = saver.readInteger("level");

        Gdx.app.log("INFO", "Finished Loading");
        station.queueAddImages();
    }

    @Override
    public void hide(){
        //if we idk need to clear something after the screen ends
        Gdx.input.setInputProcessor(null);
        //shapeRenderer.dispose();
    }



    @Override
    public void render(float delta) {
        if(station.manager.update()) {
        //music play logic
        game.introSound = station.manager.get("music/IntroMusic.mp3", Sound.class);
        game.gameSound = station.manager.get("music/GameMusic.mp3", Sound.class);

        if (game.playMusic){
            game.introSound.play();
            long SoundId = game.introSound.loop();
            game.introSound.setVolume(SoundId,game.playVolume);
            //mp3Sound.stop(id);
        }
        game.setScreen(new TitleScreen(game));
        }
         // display loading information
        Gdx.gl.glClearColor(.05f, .15f, .35f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(380, 500, 200f, 20);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(380, 500, 200f*station.manager.getProgress(), 20);
        shapeRenderer.end();
        game.batch.begin();
        game.batch.draw(new Texture("menu/loading.png"), 650, 350, 200, 200);
        game.font.draw(game.batch, "LADEN:     (" + (station.manager.getProgress()*100) + "%)...", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Zu Laden: ["+station.manager.getQueuedAssets() +"x]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .6f);
        game.batch.end();
        
    }
}