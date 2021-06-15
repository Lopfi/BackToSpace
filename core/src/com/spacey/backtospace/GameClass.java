package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
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
    //Create values to be accessible everywhere in the game
    public Sound introSound;
    public Sound gameSound;
    public Boolean playMusic;
    public Integer playVolume;

    public Integer coins;
    public Integer level;
    public Integer slot1;
    public Integer slot2;
    public Integer slot3;
    public Float playerx;
    public Float playery;
    @Override
    public void create () {
        assets = new AssetLoader();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        box2d = new Box2DWorld();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
