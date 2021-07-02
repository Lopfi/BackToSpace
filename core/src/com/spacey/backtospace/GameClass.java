package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacey.backtospace.Helper.AssetLoader;
import com.spacey.backtospace.Helper.DataSafe;
import com.spacey.backtospace.box2d.Box2DWorld;
import com.spacey.backtospace.screens.GameScreen;
import com.spacey.backtospace.screens.LoadingScreen;

public class GameClass extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Box2DWorld box2d;
    public AssetLoader assets;
    public OrthographicCamera camera;

    public DataSafe safe;

    public Boolean isPaused;
    public boolean chestmode;
    public Sound introSound;
    public Sound gameSound;

    public int displayH;
    public int displayW;

    public int h;
    public int w;

    public GameScreen gameScreen;

    @Override
    public void create () {
        assets = new AssetLoader();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        box2d = new Box2DWorld();
        safe = new DataSafe();

        isPaused = false;

        // Display Size
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();

        // 180*320
        h = (int) (displayH /Math.floor(displayH /160f)); //180
        w = (int) (displayW /(displayH / (displayH /Math.floor(displayH /160f)))); //320

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
        if (safe.playMusic){
            introSound = assets.manager.get("music/IntroMusic.mp3", Sound.class);
            String soundpath = new DataSafe().standardmusicPath;
            if (new DataSafe().readString("currentMusic") != "") {
                soundpath = new DataSafe().readString("currentMusic"); // we cant access datasafe here cuz its requested in loadingscreen lmao
            }
            gameSound = assets.manager.get(soundpath, Sound.class);
            introSound.play();
            long SoundId = introSound.loop();
            introSound.setVolume(SoundId, safe.playVolume);
            //mp3Sound.stop(id);
        }
    }
}
