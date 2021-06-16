package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.GameClass;

public class CreditScreen extends ScreenAdapter {
    GameClass game;
    final float firstLineY;

    public CreditScreen(GameClass game) {
        this.game = game;
        firstLineY = Gdx.graphics.getHeight() * .80f;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new TitleScreen(game));
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.7f, .2f, .02f, 1);
        //OLD: Gdx.gl.glClearColor(1, .4f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float textX = Gdx.graphics.getWidth() * .25f;
        game.batch.begin();
        game.font.draw(game.batch, "Credits:", textX, getLineY(0));
        game.font.draw(game.batch, "Bene        -Story", textX, getLineY(1));
        game.font.draw(game.batch, "Colin        -Code", textX, getLineY(2));
        game.font.draw(game.batch, "Felisa        -??", textX, getLineY(3));
        game.font.draw(game.batch, "Lisa           -??", textX, getLineY(4));
        game.font.draw(game.batch, "Maarten    -??", textX, getLineY(5));
        game.font.draw(game.batch, "Robin       -Code", textX, getLineY(6));
        game.font.draw(game.batch, "Simon      -Design", textX, getLineY(7));
        game.font.draw(game.batch, "Srishti      -??", textX, getLineY(8));
        game.font.draw(game.batch, "Copyright @2021 ", textX, getLineY(9));

        game.batch.draw(game.assets.manager.get("menu/team.png", Texture.class), 650, 250, 350, 350);

        game.font.draw(game.batch, "[ENTER] Main Menu", textX, getLineY(11));
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