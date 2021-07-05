package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

// most important parent class for everything with a position and a texture
public class Entity {
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public Animation<TextureRegion> animation;
    public Body body;
    public Enums.ENTITYTYPE type;
    public GameClass game;
    public boolean flipped;

    public Entity(){
        pos = new Vector3();
    }

    public Entity(Texture texture){
        pos = new Vector3();
        this.texture = initTexture(texture);
    }

    public Texture initTexture(Texture texture) {
        height = texture.getHeight();
        width = texture.getWidth();
        return texture;
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        if (flipped) currentFrame.flip(!currentFrame.isFlipX(), false);
        else currentFrame.flip(currentFrame.isFlipX(), false);
        batch.draw(currentFrame, pos.x, pos.y, width, height);
    }

    public Fixture getFixture() {
        if (body == null) return null;
        return  body.getFixtureList().get(0);
    }

    public void createBox(Vector3 pos){
        game.box2d.world.destroyBody(body);
        body = Box2DHelper.createBody(game.box2d.world, width, height + 4, pos, BodyDef.BodyType.DynamicBody, false);
    }

}
