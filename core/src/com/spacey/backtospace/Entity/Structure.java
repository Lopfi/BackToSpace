package com.spacey.backtospace.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

public class Structure extends Entity{

    public Enums.STRUCTURETYPE type;

    public Structure(Enums.STRUCTURETYPE type, GameClass gameClass, float x, float y) {
        super();
        this.type = type;
        switch (type) {
            case WOOD:
                texture = gameClass.assets.manager.get("structures/Wood.png", Texture.class);
                break;
            case STONE:
                texture = gameClass.assets.manager.get("structures/Stone.png", Texture.class);
                break;

                case ROCKET:
                texture = gameClass.assets.manager.get("structures/Rocket.png", Texture.class);
                break;
            default:
                break;
        }

        width = texture.getWidth();
        height = texture.getHeight();

        pos.x = x;
        pos.y = y;

        body = Box2DHelper.createBody(gameClass.box2d.world, width, height, pos, BodyDef.BodyType.StaticBody);
    }
}
