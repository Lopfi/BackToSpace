package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.box2d.Box2DWorld;
import com.spacey.backtospace.screens.TitleScreen;
import com.spacey.backtospace.Helper.Datasave;

public class GameClass extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Box2DWorld box2d;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        box2d = new Box2DWorld();
        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
    /*Datasave safer;
    safer = new Datasave();

    safer.write("key", "hey");
    String yourdata = safer.readString("key");*/

}
