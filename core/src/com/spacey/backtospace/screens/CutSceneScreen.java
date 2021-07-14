package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.UI.Button;
import com.spacey.backtospace.Entity.UI.UI;
import com.badlogic.gdx.math.MathUtils;
import com.spacey.backtospace.Helper.Control;

public class CutSceneScreen extends ScreenAdapter {

    GameClass game;
    Button back;
    Stage stage;
    private Texture background;
    private Texture rocket;
    private Texture asteroid;
    UI ui;
    private float timer;
    public CutSceneScreen(GameClass game) {
        this.game = game;
        stage = new Stage();
        this.ui = game.gameScreen.ui;
        timer = 0;
    }
    @Override
    public void show() {
        background = game.assets.manager.get("screens/background.png", Texture.class);
        rocket = game.assets.manager.get("structures/rocket.png", Texture.class);
        asteroid = game.assets.manager.get("structures/asteroid.png", Texture.class);
        InputListener action = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        };
        back = new Button(game, stage, "[ENTER] Back", action, 200, 65, Gdx.graphics.getWidth()-245, Gdx.graphics.getHeight()-80);
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
        timer = timer + (1/60) + Gdx.graphics.getDeltaTime(); //secounds elapsed since start of screen
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Control.debug) game.font.draw(game.batch, "Time: " + Math.round(timer), 10, Gdx.graphics.getHeight()-40);
        if (timer < 10f){
            game.batch.draw(rocket, Gdx.graphics.getWidth()/2f+50, (40+timer*50));
            ui.showMessage(game.batch, "Somewhere in Space ...");
        }
        if (timer >= 10f && timer < 18f){
            game.batch.draw(rocket, Gdx.graphics.getWidth()/2f-50, (1040-timer*50));
            ui.showMessage(game.batch, "... while all Systems are working ...");
        }
        if (timer >= 18f && timer < 25f){
                for (int i = 0; i < 10; i++) {
                    game.batch.draw(asteroid, MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()));
                }

            game.batch.draw(rocket, Gdx.graphics.getWidth()/2f-50, 540);
            ui.showMessage(game.batch, "... your ship collides with some asteroides");
        }
        if (timer >= 25f){
            background = game.assets.manager.get("screens/landing.png", Texture.class); //change background
            game.batch.draw(rocket, Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/5f);
            ui.showMessage(game.batch, "Your ship is broken on an unkown planet. Try to get home [" + (35-(int)(timer)) + "]");
        }
        if (timer >= 35f){
            game.setScreen(new TitleScreen(game));
        }

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