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

public class CutSceneScreen extends ScreenAdapter {

    GameClass game;
    Button back;
    Stage stage;
    public CutSceneScreen(GameClass game) {
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

        if (!game.safe.cutSzeneFinished){
            game.safe.write("cutSzeneFinished", true);
        } else {
            game.setScreen(new TitleScreen(game));
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.7f, .2f, .02f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "CUT SZENE? BILDER ODER OBJECTS?", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        game.batch.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
}