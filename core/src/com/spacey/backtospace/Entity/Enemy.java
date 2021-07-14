package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Animations;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

public class Enemy extends Entity {

    private int speed;
    public int untilActive;
    private float lastMove;

    private int dirX;
    private int dirY;

    public Enemy(Vector3 pos, GameClass game) {
        super(game.assets.manager.get("enemys/slimeKing" + MathUtils.random(2)+ ".png", Texture.class));
        this.game = game;
        this.pos = pos;
        type = Enums.ENTITYTYPE.ENEMY;
        animation = Animations.createAnimation(texture, 2, 2, 0.2f);
        width = width/2f;
        height = height/2;
        speed = 80;
        body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.DynamicBody, false);
        untilActive = 0;
        lastMove = 0;
    }

    @Override
    public void drawAnimation(SpriteBatch batch, float stateTime) {
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
        super.drawAnimation(batch, stateTime);
    }

    public void setPos(float x, float y) {
        pos.x = x - width/2;
        pos.y = y - (height)/2;
    }

    public void update(float velocityX, float velocityY, boolean flip) {
        flipped = flip;
        body.setLinearVelocity(velocityX* speed, velocityY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
    }

    public void update(float stateTime) {
        if (lastMove <= stateTime + 1f) move(stateTime);
    }

    private void move(float stateTime) {
        lastMove = stateTime;
        //int dir = MathUtils.random(3);
        body.setLinearVelocity(dirX*speed, dirY*speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
        if (dirX < 0) flipped = true;
        if (dirX > 0) flipped = false;
    }

    public void moveRandom() {
        if(MathUtils.random(100) >= 95){//100-95 = 5% chance to do something, rembemer it gets called often
            float speedy = (MathUtils.random(10)/10f);
            if(MathUtils.random(1) == 0){
                //y-axis
                int dir = 1;
                if(MathUtils.random(1) == 0) dir = -1;
                update(0, speedy*dir, (dir == -1));
            } else {
                //x-axis
                int dir = 1;
                if(MathUtils.random(1) == 0) dir = -1;
                update(speedy*dir, 0, (dir == -1));
            }
        }
    }
}