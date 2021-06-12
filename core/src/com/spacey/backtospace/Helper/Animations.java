package com.spacey.backtospace.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animations {

    public static Animation<TextureRegion> createAnimation(Texture texture, int cols, int rows) {

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / cols,
                texture.getHeight() / rows);

        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        return new Animation<TextureRegion>(0.15f, frames);
    }
}
