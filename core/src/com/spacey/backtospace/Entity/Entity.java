package com.spacey.backtospace.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity {
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public int size;
    public Animation<TextureRegion> animation;
    public Body body;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, pos.x, pos.y, width, height);
    }

}
