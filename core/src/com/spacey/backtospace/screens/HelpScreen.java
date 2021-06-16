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
    final float firstLineY;

    public HelpScreen(GameClass game) {
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
        Gdx.gl.glClearColor(1, .4f, .2f, 1);
        //Gdx.gl.glClearColor(.35f, 0f, 1f, 1); nice purple
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float textX = Gdx.graphics.getWidth() * .25f;

        game.batch.begin();
        game.font.draw(game.batch, "CONTROLS", textX, getLineY(0));
        game.font.draw(game.batch, "Inventory: [1] Slot 1 [2] Slot 2 [3] Slot 3", textX, getLineY(3));
        game.font.draw(game.batch, "Movement: [UP/W] Up [DOWN/S] Down [LEFT/A] Left [RIGHT/D] Right", textX, getLineY(6));
        game.font.draw(game.batch, "            [LMB] Hold and move mouse", textX, getLineY(7));
        game.font.draw(game.batch, "[ENTER] Main Menu", textX, getLineY(14));
        game.batch.draw(new Texture("menu/wasd.png"), 660, 300, 300, 100);
        game.batch.draw(new Texture("menu/123.png"), 650, 430, 180, 65);
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