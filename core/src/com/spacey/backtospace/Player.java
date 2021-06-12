package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class Player extends Entity{

    private int speed;

    public Player(Vector3 pos, Box2DWorld box2d) {
        super();
        texture = new Texture("slimeKing.png");
        animation = Animations.createAnimation(texture, 2, 2);
        height = 20;
        width = 16;
        speed = 30;

        body = Box2DHelper.createBody(box2d.world, width, height/2, pos, BodyDef.BodyType.DynamicBody);
    }

    public void update(Control control) {
        int dirX=0;
        int dirY=0;

        if(control.LMB) {
            int x = Math.round(control.mouse_click_pos.x);
            int y = Math.round(control.mouse_click_pos.y);
            int normx = 700;
            int normy = 405;
            if (Math.abs((Math.abs(x) - normx)) >= Math.abs((Math.abs(y) - normy))) {
                if (x < 675) {
                    dirX = -1;
                } else if (x > 688) {
                    dirX = 1;
                }
            } else {
                if (y > 400) {
                    dirY = 1;
                } else if (y < 420) {
                    dirY = -1;
                }
            }
        }
        else{
                if (control.down) dirY = -1;
                if (control.up) dirY = 1;
                if (control.left) dirX = -1;
                if (control.right) dirX = 1;
            }

        body.setLinearVelocity(dirX * speed, dirY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;
    }
}
