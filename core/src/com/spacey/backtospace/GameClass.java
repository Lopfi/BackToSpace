package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.box2d.Box2DWorld;
import com.spacey.backtospace.screens.LoadingScreen;
import com.spacey.backtospace.Helper.Datasave;

public class GameClass extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Box2DWorld box2d;
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
    @Override
    public void create () {
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
    /*saver.write("key", "hey");
    String yourdata = saver.readString("key");*/
}
