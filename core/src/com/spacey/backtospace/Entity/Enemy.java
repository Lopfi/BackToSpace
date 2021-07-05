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
    private boolean flipped;
    GameClass game;
    public Enemy(Vector3 pos, GameClass game) {
        super(game.assets.manager.get("enemys/slimeKing.png", Texture.class));
        this.game = game;
        this.pos = pos;
        type = Enums.ENTITYTYPE.ENEMY;
        animation = Animations.createAnimation(texture, 2, 2, 0.2f);
        width = width/2;
        height = height/2;
        speed = 80;
        body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.DynamicBody, false);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        super.drawAnimation(batch, stateTime, flipped);
    }

    public void createBox(Vector3 pos){
        game.box2d.world.destroyBody(body);
        body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.DynamicBody, false);
    }

    public void setPos(float x, float y) {
        pos.x = x - width/2;
        pos.y = y - (height)/2;
    }

    public void update(float velocityX, float velocityY, boolean flip) {
        flipped = flip;
        body.setLinearVelocity(velocityX* speed, velocityY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/2;
    }

    public void moveRandom() {
        float speedy = (MathUtils.random(10)/10f);
        if(MathUtils.random(1) == 0){
            //y-axe
            int dir = 1;
            if(MathUtils.random(1) == 0) dir = -1;
            update(0, speedy*dir, (dir == -1));
        } else {
            //x-axe
            int dir = 1;
            if(MathUtils.random(1) == 0) dir = -1;
            update(0, speedy*dir, (dir == -1));
        }
    }

}