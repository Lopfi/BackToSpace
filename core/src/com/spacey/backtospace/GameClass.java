package com.spacey.backtospace;

import com.spacey.backtospace.map.Tile;
import com.spacey.backtospace.map.Map;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;
public class GameClass extends ApplicationAdapter {
    OrthographicCamera camera;
    Control control;
    SpriteBatch batch;

    //Inventory [empty = 0 // fuel = 1 // fire = 2 // hammer = 3 //...]
    public int[] inv = {2, 1, 3};

    // Display Size
    private int displayW;
    private int displayH;

    //YOU WANT DEVMODE? YESS!!
    public boolean devmode = true;

    // For Movement
    int direction_x, direction_y;
    int speed = 1;

    float stateTime;

    int i;
    
    // Island
    Map map;

    Player player;
    Button button;
    private BitmapFont font; // for writing
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
        
        map = new Map(devmode);
        player = new Player();
        button = new Button();
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
            
        if(control.LMB) {
            int x = Math.round(control.mouse_click_pos.x);
            int y = Math.round(control.mouse_click_pos.y);
            int normx = 700;
            int normy = 405;
            if (Math.abs((Math.abs(x)-normx)) >= Math.abs((Math.abs(y)-normy))){
                if(x < 675){
                    direction_x = -1;
                } else if(x > 688){
                    direction_x = 1;
                }
            } else {
                if(y > 400){
                    direction_y = 1 ;
                } else if (y < 420) {
                    direction_y = -1 ;
                }
            }
        } else {//use keys only if mouse is not clicked else u get double speed
            if(control.down)  direction_y = -1 ;
            if(control.up)    direction_y = 1 ;
            if(control.left)  direction_x = -1;
            if(control.right) direction_x = 1;
        }

        if(control.esc){
            //change to pause menu
        }
        if(control.slot1) inv[0] = 0;
        if(control.slot2) inv[1] = 0;
        if(control.slot3) inv[2] = 0;

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

        //Developer Mode draw x/y camera position
        if (devmode){
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(1, 1);
        font.draw(batch, "x:"+Math.round(camera.position.x)+" y:"+Math.round(camera.position.y), camera.position.x - 50, camera.position.y -20);
        }
        for (int i = 0; i < 3; i++) {
            String img;
            if (inv[i] == 1){
                img = "buttons/Ifuel.png";
            } else if (inv[i] == 2){
                img = "buttons/Ifire.png";
            } else if (inv[i] == 3){
                img = "buttons/Ihammer.png";
            } else {
                img = "buttons/Iempty.png";
            }
            button.setButton(img, 10, 10, (Math.round(camera.position.x)-10)+i*10, Math.round(camera.position.y-58));
            button.draw(batch);
        }
        button.setButton("buttons/Bpause-klein.png", 10, 5, (Math.round(camera.position.x)-10)+3*10, Math.round(camera.position.y-58));
        button.draw(batch);//idk how to calc the last one i googled like 15min but i dont care now

        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
    }
}
