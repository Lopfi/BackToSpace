package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Helper {

    public static Animation<TextureRegion> getAnimationFromSpriteSheet(String path, int cols, int rows, float duration) {
        // Load the sprite sheet as a Texture
        Texture spriteSheet = new Texture(Gdx.files.internal(path));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / cols,
                spriteSheet.getHeight() / rows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] textureRegions = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                textureRegions[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        return new Animation<TextureRegion>((duration * 33.34f), textureRegions);
    }
}
