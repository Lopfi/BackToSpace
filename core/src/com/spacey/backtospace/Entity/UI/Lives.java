package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacey.backtospace.GameClass;

public class Lives extends UIElement{

    GameClass game;

    public Lives(GameClass game) {
        super(game);
        this.game = game;
        this.texture = this.initTexture(game.assets.manager.get("menu/herz.png", Texture.class));

    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < game.life; i++) {
            batch.draw(texture, i * width, Gdx.graphics.getHeight()-height, width, height);
        }
    }
}
