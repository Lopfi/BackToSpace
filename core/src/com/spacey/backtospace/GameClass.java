package com.spacey.backtospace;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.box2d.Box2DWorld;
import com.spacey.backtospace.map.Tile;
import com.spacey.backtospace.map.Map;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameClass extends ApplicationAdapter {
    OrthographicCamera camera;
    Control control;
    SpriteBatch batch;

    // Display Size
    private int displayW;
    private int displayH;

    float stateTime;

    Map map;
    Player player;
    Box2DWorld box2D;

    @Override
    public void create () {
        batch = new SpriteBatch();

        // CAMERA
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();
        
        // For 800x600 we will get 266*200
        int h = (int) (displayH/Math.floor(displayH/160));
        int w = (int) (displayW/(displayH/ (displayH/Math.floor(displayH/160))));
        
        camera = new OrthographicCamera(w,h);
        camera.zoom = .65f; //.65f
        
        // Used to capture Keyboard Input
        control = new Control(displayW, displayH, camera);
        Gdx.input.setInputProcessor(control);

        camera.position.x = 45; //start somewhat in the middle of the map
        camera.position.y = 45;
        camera.update();

        box2D = new Box2DWorld();
        map = new Map(box2D);
        player = new Player(new Vector3(5, 5, 0), box2D);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        player.update(control);

        camera.position.lerp(player.pos, .1f);
        camera.update();

        //Gdx.app.log("POS", String.valueOf(camera.position));

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        for(Tile tile : map.tiles) tile.draw(batch);
        player.drawAnimation(batch, stateTime);
        batch.end();

        box2D.tick(camera, control);
    }
	
    @Override
    public void dispose () {
        batch.dispose();
    }
}
