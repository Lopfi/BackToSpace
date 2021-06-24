package com.spacey.backtospace.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.Helper.Enums;

public class Entity {
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public Animation<TextureRegion> animation;
    public Body body;
    public Enums.ENTITYTYPE type;

    public Entity(){
        pos = new Vector3();
    }

    public Texture initTexture(Texture texture) {
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        return texture;
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime, boolean flipped) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        if (flipped) currentFrame.flip(!currentFrame.isFlipX(), false);
        else currentFrame.flip(currentFrame.isFlipX(), false);
        batch.draw(currentFrame, pos.x, pos.y, width, height);
    }

    public Fixture getFixture() {
        if (body == null) return null;
        return  body.getFixtureList().get(0);
    }

}
