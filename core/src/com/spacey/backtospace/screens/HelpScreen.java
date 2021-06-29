package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.UI.Button;

public class HelpScreen extends ScreenAdapter {

    GameClass game;
    Button back;
    Stage stage;
    public HelpScreen(GameClass game) {
        this.game = game;
        stage = new Stage();
    }
    @Override
    public void show() {
        InputListener action = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        };
        back = new Button(game, stage, "[ENTER] Back", action, 200, 65, Gdx.graphics.getWidth()/2-100, 75);
        stage.addListener(new InputListener() 
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode) 
            {
                if (keycode == Input.Keys.ENTER) {
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

        game.batch.begin();
        game.batch.draw(game.assets.manager.get("menu/HelpScreen.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /*final float firstLineY;
    firstLineY = Gdx.graphics.getHeight() * .80f;

    private float getLineY(float factor) {
        return firstLineY - (game.font.getLineHeight() + 10) * factor;
    }*/ //RIP NICE PEACE OF CODE MAYBE U WILL WORK ANYWHERE ELSE
}