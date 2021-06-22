package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacey.backtospace.Helper.AssetLoader;
import com.spacey.backtospace.box2d.Box2DWorld;
import com.spacey.backtospace.screens.LoadingScreen;

public class GameClass extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Box2DWorld box2d;
    public AssetLoader assets;
    public OrthographicCamera camera;

    //Create values to be accessible everywhere in the game
    public Boolean isPaused = false;
    public Sound introSound;
    public Sound gameSound;
    public Boolean playMusic;
    public Float playVolume;

    public Integer coins;
    public Integer life;

    public Integer level;
    public Integer slot1;
    public Integer slot2;
    public Integer slot3;
    public Float playerX;
    public Float playerY;

    public int displayH;
    public int displayW;
    public float uiScale; // needed for static ui elements to scale them up

    @Override
    public void create () {
        assets = new AssetLoader();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        box2d = new Box2DWorld();
        // Display Size
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();

        // For 800x600 we will get 266*200
        int h = (int) (displayH /Math.floor(displayH /160f)); //180
        int w = (int) (displayW /(displayH / (displayH /Math.floor(displayH /160f)))); //320

        uiScale = (float) displayH / h;

        camera = new OrthographicCamera(w, h);
        camera.zoom = 1.2f; //1.2f
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public void startMusic() {
        if (this.playMusic){
            this.introSound = this.assets.manager.get("music/IntroMusic.mp3", Sound.class);
            this.gameSound = this.assets.manager.get("music/GameMusic.mp3", Sound.class);
            this.introSound.play();
            long SoundId = this.introSound.loop();
            this.introSound.setVolume(SoundId,this.playVolume);
            //mp3Sound.stop(id);
        }
    }
}
