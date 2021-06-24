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
        Gdx.gl.glClearColor(.7f, .2f, .02f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float textX = Gdx.graphics.getWidth() * .25f;

        // TODO replace with single image
        game.batch.begin();
        game.font.draw(game.batch, "CONTROLS", textX, getLineY(0));
        game.font.draw(game.batch, "Inventory: [1]=1.Slot   [2]=2.Slot   [3]=3.Slot", textX, getLineY(3));
        game.font.draw(game.batch, "Movement: [UP/W]=Up    [LEFT/A]=Left", textX, getLineY(6));
        game.font.draw(game.batch, "           [DOWN/S]=Down   [RIGHT/D]=Right", textX, getLineY(7));
        game.font.draw(game.batch, "Mouse Move: [LMB] Hold and move mouse", textX, getLineY(9));
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