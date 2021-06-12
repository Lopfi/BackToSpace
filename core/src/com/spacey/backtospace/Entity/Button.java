package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.Entity.Entity;

public class Button extends Entity {

    public Button(Texture texture, Vector3 pos) {
        super();
        this.texture = texture;
        height = this.texture.getHeight();
        width = this.texture.getWidth();
        this.pos = pos;
    }
}
