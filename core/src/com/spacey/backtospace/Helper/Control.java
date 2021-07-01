package com.spacey.backtospace.Helper;

import java.util.ArrayList;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// handles all inputs from keyboard and mouse
public class Control extends InputAdapter implements InputProcessor {
    // CAMERA
    OrthographicCamera camera;

    // DIRECTIONS
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    // MOUSE
    public boolean  LMB;
    public boolean  RMB;
    public boolean  processed_click;
    public Vector2  mouse_click_pos = new Vector2();
    public Vector2  map_click_pos = new Vector2();
    public Vector2  mouse_pos = new Vector2();
    
    //ArrayList
    ArrayList<Integer> pressedKeys = new ArrayList<>();

    // DEBUG
    public static boolean debug;
    
    // SCREEN
    public int screenWidth;
    public int screenHeight;

    public Control(int screenWidth, int screenHeight, OrthographicCamera camera){
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void reset() {
        pressedKeys = new ArrayList<>(); 
        down = false;
        up = false;
        left = false;
        right = false;
    }

    private void setMouseClickedPos(int screenX, int screenY){
        // Set mouse position (flip screen Y)
        mouse_click_pos.set(screenX, screenHeight - screenY);
        map_click_pos.set(get_map_coords(mouse_click_pos));
    }
        
    public Vector2 get_map_coords(Vector2 mouse_coords){
        Vector3 v3 = new Vector3(mouse_coords.x, screenHeight - mouse_coords.y, 0);
        camera.unproject(v3);
        return new Vector2(v3.x,v3.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!pressedKeys.contains(keycode)) pressedKeys.add(keycode);
        switch (keycode) {
            case Keys.DOWN:
            case Keys.S:
                down = true;
                break;
            case Keys.UP:
            case Keys.W:
                up = true;
                break;
            case Keys.LEFT:
            case Keys.A:
                left = true;
                break;
            case Keys.RIGHT:
            case Keys.D:
                right = true;
                break;
        }
        return false;
    }

    public boolean isPressed(int keycode){
        return pressedKeys.contains(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (pressedKeys.contains(keycode)) pressedKeys.remove(Integer.valueOf(keycode));
        switch (keycode) {
            case Keys.DOWN:
            case Keys.S:
                down = false;
                break;
            case Keys.UP:
            case Keys.W:
                up = false;
                break;
            case Keys.LEFT:
            case Keys.A:
                left = false;
                break;
            case Keys.RIGHT:
            case Keys.D:
                right = false;
                break;
            case Keys.BACKSPACE:
                debug = !debug;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = true;
        } else if (pointer == 0 && button == 0){
            RMB = true;
        }

        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = false;
            processed_click = false;
        } else if (pointer == 0 && button == 0){
            RMB = false;
        }

        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouse_pos.x = screenX;
        mouse_pos.y = screenY;
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

}