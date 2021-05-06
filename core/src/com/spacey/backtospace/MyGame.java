package com.spacey.backtospace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	// Display Size
	public int displayW;
	public int displayH;

	@Override
	public void create() {
		Assets.load();
		batch = new SpriteBatch();
		font = new BitmapFont();
		displayW = Gdx.graphics.getWidth();
		displayH = Gdx.graphics.getHeight();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render(); // important!
	}

	@Override
	public void dispose() {
	}

}