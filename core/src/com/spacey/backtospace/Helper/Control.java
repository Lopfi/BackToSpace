package com.spacey.backtospace.Helper;

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

    //INVENTORY
    public boolean slot1;
    public boolean slot2;
    public boolean slot3;
    public boolean esc;

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
    
    //PAUSE SCREEN KEYS
    public boolean Q;
    public boolean B;
    public boolean E;
    public boolean X;
    public boolean Space;

    // DEBUG
    public boolean debug;
    
    // SCREEN
    public int screenWidth;
    public int screenHeight;

    public Control(int screenWidth, int screenHeight, OrthographicCamera camera){
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    private void setMouseClickedPos(int screenX, int screenY){
        // Set mouse position (flip screen Y)
        mouse_click_pos.set(screenX, screenHeight - screenY);
        map_click_pos.set(get_map_coords(mouse_click_pos));
    }
        
    public Vector2 get_map_coords(Vector2 mouse_coords){
        Vector3 v3 = new Vector3(mouse_coords.x, screenHeight - mouse_coords.y, 0);
        this.camera.unproject(v3);
        return new Vector2(v3.x,v3.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                esc = true;
                break;
            case Keys.Q:
                Q = true;
                break;
            case Keys.B:
                B = true;
                break;
            case Keys.NUM_1:
                slot1 = true;
                break;
            case Keys.NUM_2:
                slot2 = true;
                break;
            case Keys.NUM_3:
                slot3 = true;
                break;
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
            case Keys.E:
                E = true;
                break;
            case Keys.X:
                X = true;
                break;
            case Keys.SPACE:
                Space = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                esc = false;
                break;
            case Keys.Q:
                Q = false;
                break;
            case Keys.B:
                B = false;
                break;
            case Keys.NUM_1:
                slot1 = false;
                break;
            case Keys.NUM_2:
                slot2 = false;
                break;
            case Keys.NUM_3:
                slot3 = false;
                break;
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
            case Keys.E:
                E = false;
                break;
            case Keys.X:
                X = false;
                break;
            case Keys.SPACE:
                Space = false;
                break;
            case Keys.BACKSPACE:
                debug = !debug;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

}