package com.spacey.backtospace.Entity;

import java.util.Random;

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
                String[] options = {
                    "structures/stone1_small.png",
                    "structures/stone2_small.png",
                    "structures/stone3_small.png",
                    //didnt implemented the big ones cuz they are too big or there to many to big ones
                };
                int random = new Random().nextInt(options.length);
                texture = initTexture(game.assets.manager.get(options[random], Texture.class));
                break;
            case ROCKET:
                texture = initTexture(game.assets.manager.get("structures/rocket.png", Texture.class));
                break;
            case FUEL:
                texture = initTexture(game.assets.manager.get("structures/fuel.png", Texture.class));
                break;
            case SCREW:
                texture = initTexture(game.assets.manager.get("items/screw.png", Texture.class));
                break;
            case SCREWDRIVER:
                texture = initTexture(game.assets.manager.get("items/screwdriver.png", Texture.class));
                break;
            case KEY:
                texture = initTexture(game.assets.manager.get("items/key.png", Texture.class));
                break;
            case COIN:
                texture = initTexture(game.assets.manager.get("ui/coin.png", Texture.class));
                break;
            case CHEST:
                texture = initTexture(game.assets.manager.get("structures/chest.png", Texture.class));
                break;
            default:
                break;
        }

        pos = new Vector3(x, y,0);
        if (type == Enums.ENTITYTYPE.ROCKET) body = Box2DHelper.createBody(game.box2d.world, width, 36, pos, BodyDef.BodyType.StaticBody, false); // make it possible to walk behind rocket
        else if (type == Enums.ENTITYTYPE.STONE || type == Enums.ENTITYTYPE.CHEST) body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.StaticBody, false); //idk why u want to walk behind but ok
        else body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.StaticBody, true);
    }
}
