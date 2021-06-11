package com.spacey.backtospace;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    public Vector2 pos;
    public Texture texture;
    public float width;
    public float height;
    public int size;
    public Animation<TextureRegion> animation;
    
    float dir_x = 0;
    float dir_y = 0;
    
    public Entity(){
        pos = new Vector2();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, pos.x, pos.y, width, height);
    }

}
