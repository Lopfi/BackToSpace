package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Button extends Entity{

    public Button() {
        super();
        texture = new Texture("buttons/Bstart.png");
        height = 16;
        width = 32;
        pos.x = 0;
        pos.y = 0;
    }

    public void setButton(String path, int h, int w, int x, int y) {
        texture = new Texture(path);
        height = h;
        width = w;
        pos.x = x;
        pos.y = y;
    }
}
