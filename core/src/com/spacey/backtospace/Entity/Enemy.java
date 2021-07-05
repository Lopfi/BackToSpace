package com.spacey.backtospace.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.UI.Inventory;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Animations;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

// the ENEMY? with a controller to handle movement inputs
public class Enemy extends Entity {

    private int speed;
    public int untilActive;
    public Inventory inventory;
    private boolean flipped;
    GameClass game;
    public Enemy(Vector3 pos, GameClass game) {
        super(game.assets.manager.get("enemys/slimeKing.png", Texture.class));
        this.game = game;
        this.pos = pos;
        type = Enums.ENTITYTYPE.ENEMY;
        animation = Animations.createAnimation(texture, 2, 2, 0.2f);
        width = width/2f;
        height = height/4;
        speed = 80;
        body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.DynamicBody, false);
        untilActive = 0;
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
        super.drawAnimation(batch, stateTime, flipped);
    }

    public void createBox(Vector3 pos){
        game.box2d.world.destroyBody(body);
        body = Box2DHelper.createBody(game.box2d.world, width, height + 4, pos, BodyDef.BodyType.DynamicBody, false);
    }
    public void setPos(float x, float y) {
        pos.x = x - width/2;
        pos.y = y - (height)/4;
    }
    public void update(float velocityX, float velocityy, boolean flip) {
        flipped = flip;
        body.setLinearVelocity(velocityX* speed, velocityy * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
    }
    public void moveRandom() {
        if(MathUtils.random(100) >= 95){//100-95 = 5% chance to do something, rembemer it gets called often
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
                update(speedy*dir, 0, (dir == -1));
            }
        }
    }
}