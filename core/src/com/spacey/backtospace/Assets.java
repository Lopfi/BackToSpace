package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture textureBack;
    public static Sprite spriteBack;

    public static Animation<TextureRegion> kingSlime;

    public static void load() {
        kingSlime = Helper.getAnimationFromSpriteSheet("enemies/kingSlime.png", 2, 2, 1);
        textureBack = new Texture(Gdx.files.internal("map.png"));
    }
}
