package com.spacey.backtospace.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

public class Structure extends Entity{


    public Structure(Enums.ENTITYTYPE type, GameClass game, float x, float y) {
        super();

        this.type = type;

        switch (type) {
            case STONE:
                texture = initTexture(game.assets.manager.get("structures/Stone3_small.png", Texture.class));
                break;
            case ROCKET:
                texture = initTexture(game.assets.manager.get("structures/Rocket.png", Texture.class));
                break;
            case FUEL:
                texture = initTexture(game.assets.manager.get("structures/Fuel.png", Texture.class));
                break;
            case SCREW:
                texture = initTexture(game.assets.manager.get("items/Screw.png", Texture.class));
                break;
            case SCREWDRIVER:
                texture = initTexture(game.assets.manager.get("items/Screwdriver.png", Texture.class));
                break;
            case COIN:
                texture = initTexture(game.assets.manager.get("menu/coin.png", Texture.class));
                break;
            default:
                break;
        }

        pos = new Vector3(x, y,0);
        if (type == Enums.ENTITYTYPE.ROCKET) body = Box2DHelper.createBody(game.box2d.world, width, 36, pos, BodyDef.BodyType.StaticBody); // make it possible to walk behind rocket
        else body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.StaticBody);
    }
}
