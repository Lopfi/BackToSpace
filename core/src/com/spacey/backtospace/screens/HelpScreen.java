package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.GameClass;

public class HelpScreen extends ScreenAdapter {

    GameClass game;
    public HelpScreen(GameClass game) {
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
        //Gdx.gl.glClearColor(.35f, 0f, 1f, 1); nice purple
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "STEUERUNG", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "[Inventar] 1.Platz = (1), 2.Platz = (2)..", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "[Bewegung] Pfeiltasten & WASD", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .50f);
        game.font.draw(game.batch, "      oder Maus gedrückt halten", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .47f);
        game.font.draw(game.batch, "Drücke Enter für den Hauptbildschirm", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.draw(new Texture("menu/wasd.png"), 660, 300, 300, 100);
        game.batch.draw(new Texture("menu/123.png"), 650, 430, 180, 65);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}