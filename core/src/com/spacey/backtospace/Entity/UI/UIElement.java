package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.graphics.Texture;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.GameClass;

public class UIElement extends Entity {

    float scale;

    public UIElement(GameClass game) {
        super();
        scale = game.uiScale;
    }

    @Override
    public Texture initTexture(Texture texture) {
        this.height = texture.getHeight() * scale;
        this.width = texture.getWidth() * scale;
        return texture;
    }
}
