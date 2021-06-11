package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player extends Entity{

    public Player() {
        super();
        texture = new Texture("slimeKing.png");
        animation = Animations.createAnimation(texture, 2, 2);
        height = 20;
        width = 16;
    }

    public void updateDirection(int x, int y) {

    }
}
