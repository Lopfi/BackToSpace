package com.spacey.backtospace.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Helper.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.ScreenAdapter;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Map;


public class GameScreen extends ScreenAdapter {

    GameClass game;

    OrthographicCamera camera;
    Control control;

    // Display Size
    private int displayW;
    private int displayH;

    float stateTime;

    Map map;
    Player player;

    SpriteBatch batch;

    public GameScreen(GameClass game) {
        this.game = game;
        game.font.getData().setScale(0.5f);
    }

    @Override
    public void show() {
        // CAMERA
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();

        // For 800x600 we will get 266*200
        int h = (int) (displayH/Math.floor(displayH/160));
        int w = (int) (displayW/(displayH/ (displayH/Math.floor(displayH/160))));

        camera = new OrthographicCamera(w,h);
        camera.zoom = 1.2f; //.65f

        // Used to capture Keyboard Input
        control = new Control(displayW, displayH, camera);
        Gdx.input.setInputProcessor(control);

        camera.position.x = 45; //start somewhat in the middle of the map
        camera.position.y = 45;
        camera.update();

        batch = new SpriteBatch();
        map = new Map(game.box2d);
        player = new Player(new Vector3(100, 100, 0), game.box2d);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        player.update(control);

        if(control.esc){
            game.setScreen(new SettingsScreen(game));
        }

        camera.position.lerp(player.pos, .1f);
        camera.update();

        //Gdx.app.log("POS", String.valueOf(camera.position));
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        map.draw(batch, control.debug);
        player.drawAnimation(batch, stateTime);

        if (control.debug){
            game.font.draw(batch, "x:"+Math.round(camera.position.x)+" y:"+Math.round(camera.position.y), camera.position.x - 50, camera.position.y -20);
        }
        batch.end();
        game.box2d.tick(camera, control);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        game.box2d.world.destroyBody(player.body);
        game.font.getData().setScale(1f);
    }
}