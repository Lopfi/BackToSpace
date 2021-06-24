package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.GameClass;

public class UIElement extends Entity {

    public float scale;

    public UIElement(GameClass game) {
        super();
        scale = game.uiScale;
    }

    public UIElement(GameClass game, Texture texture) {
        super();
        scale = game.uiScale;
        this.texture = initTexture(texture);
    }

    @Override
    public Texture initTexture(Texture texture) {
        height = texture.getHeight() * scale;
        width = texture.getWidth() * scale;
        return texture;
    }
}
