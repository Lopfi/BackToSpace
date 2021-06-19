package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.ownButton;

public class ShopScreen extends ScreenAdapter {

    GameClass game;

    ownButton btn = new ownButton();
    ownButton back = new ownButton();
    Stage stage = new Stage();
    public Integer fakecoins = 0;

    public ShopScreen(GameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        InputListener action = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON1", "Pressed Text Button");
                fakecoins++;
                return true;
            }
        };
        btn.create(stage, "Increase your coins (fake!) or press [c]", action, 400, 120, 240, 300);
        InputListener action2 = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON2", "Pressed Text Button");
                game.setScreen(new TitleScreen(game));
                return true;
            }
        };
        back.create(stage, "or click here", action2, 120, 60, 600, 150);
        stage.addListener(new InputListener() 
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode) 
            {
                if (keycode == Input.Keys.C){
                    fakecoins++;
                } else if (keycode == Input.Keys.ENTER){
                    game.setScreen(new TitleScreen(game));
                }
                //Gdx.app.log("Image ClickListener", "keyDown. keycode=" + keycode);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        game.batch.begin();
        game.font.draw(game.batch, "Current Coins: " + (game.coins + fakecoins), Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .79f);
        game.font.draw(game.batch, "THE SECRET SHOP (later u can spend real money)", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "[ENTER] To get back to the main menu.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}

 