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

    public CreditScreen(GameClass game) {
        this.game = game;
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
        Gdx.gl.glClearColor(1, .4f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Mitwirkende:", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .80f);
        game.font.draw(game.batch, "Bene        -Story", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Colin        -Code", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .71f);
        game.font.draw(game.batch, "Felisa        -??", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .67f);
        game.font.draw(game.batch, "Lisa           -??", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .63f);
        game.font.draw(game.batch, "Maarten    -??", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .59f);
        game.font.draw(game.batch, "Robin       -Code", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .55f);
        game.font.draw(game.batch, "Simon      -Design", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .51f);
        game.font.draw(game.batch, "Srishti      -??", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .47f);
        game.font.draw(game.batch, "Copyright @2021 ", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .28f);

        game.batch.draw(new Texture("menu/team.png"), 650, 250, 350, 350);

        game.font.draw(game.batch, "Drücke Enter für den Hauptbildschirm", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}