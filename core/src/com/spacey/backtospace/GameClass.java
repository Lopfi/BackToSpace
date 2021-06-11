package com.spacey.backtospace;

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


    // For Movement
    int direction_x, direction_y;
    int speed = 1;

    float stateTime;

    int i;
    
    // Island
    Map map;

    Player player;

    @Override
    public void create () {
        batch = new SpriteBatch();

        i=0;
        
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
        
        map = new Map();
        player = new Player();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time


        // GAME LOGIC
        // Reset the direction values
        direction_x=0;
        direction_y=0;
            
        if(control.down)  direction_y = -1 ;
        if(control.up)    direction_y = 1 ;
        if(control.left)  direction_x = -1;
        if(control.right) direction_x = 1;
            
        camera.position.x += direction_x * speed;
        camera.position.y += direction_y * speed;
        camera.update();

        player.pos.x = camera.position.x;
        player.pos.y = camera.position.y;
        //Gdx.app.log("POS", String.valueOf(camera.position));

        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        for(Tile tile : map.tiles) tile.draw(batch);
        player.drawAnimation(batch, stateTime);
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
    }
}
