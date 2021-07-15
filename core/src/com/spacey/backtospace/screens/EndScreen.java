package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.spacey.backtospace.GameClass;

public class EndScreen extends ScreenAdapter {

    GameClass game;
    Boolean won;

    public EndScreen(GameClass game, Boolean won) {
        this.game = game;
        this.won = won;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    if (won) game.setScreen(new TitleScreen(game));
                    else Gdx.app.exit();
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        game.batch.begin();
        if (won){
            Gdx.gl.glClearColor(0, .6f, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.font.draw(game.batch, "YOU WON", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
            game.font.draw(game.batch, "You came back to Earth and this will stay a historical event.", Gdx.graphics.getWidth() * .4f, Gdx.graphics.getHeight() * .4f);
        } else {
            Gdx.gl.glClearColor(.7f, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.font.draw(game.batch, "MISSION FAILED TRAGICALLY", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
            game.font.draw(game.batch, "Sadly you did not survive. No one will be sad because you are so bad.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .4f);
        }
        game.font.draw(game.batch, "Press [ENTER] to get back or something", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);    
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}