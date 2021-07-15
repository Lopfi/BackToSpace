package com.spacey.backtospace.Entity;

import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

public class Structure extends Entity{

    public TextureRegion[] region;
    private GameClass game;

    public Structure(Enums.ENTITYTYPE type, GameClass game, Vector2 pos) {
        super();
        this.type = type;
        this.game = game;
        this.pos = new Vector3(pos, 0);

        switch (type) {
            case PLATE:
                texture = initTexture(game.assets.manager.get("structures/plate.png", Texture.class));
                break;
            case NOSECONE:
                texture = initTexture(game.assets.manager.get("structures/nosecone.png", Texture.class));
                break;
            case FIN:
                texture = initTexture(game.assets.manager.get("structures/fin.png", Texture.class));
                break;
            case STONE:
                String[] options = {
                    "structures/stone1_small.png",
                    "structures/stone2_small.png",
                    "structures/stone3_small.png",
                    //didnt implemented the big ones cuz they are too big or there to many to big ones
                };
                int random = new Random().nextInt(options.length);
                texture = initTexture(game.assets.manager.get(options[random], Texture.class));
                body = Box2DHelper.createBody(game.box2d.world, width, height, this.pos, BodyDef.BodyType.StaticBody, false); //idk why u want to walk behind but ok
                break;
            case ROCKET:
                texture = game.assets.manager.get("structures/rocket_sheet.png", Texture.class);
                int cols = 5;
                int rows = 1;
                TextureRegion[][] tmp = TextureRegion.split(texture,texture.getWidth() / cols,texture.getHeight() / rows);

                region = new TextureRegion[cols * rows];
                int index = 0;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        region[index++] = tmp[i][j];
                    }
                }

                body = Box2DHelper.createBody(game.box2d.world, region[0].getRegionWidth(), 36, this.pos, BodyDef.BodyType.StaticBody, false); // make it possible to walk behind rocket
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
            case LIFE:
                texture = initTexture(game.assets.manager.get("ui/heart.png", Texture.class));
                break;
            case CHEST:
                texture = initTexture(game.assets.manager.get("structures/chest.png", Texture.class));
                body = Box2DHelper.createBody(game.box2d.world, width, height, this.pos, BodyDef.BodyType.StaticBody, false);
                break;
            default:
                break;
        }

        if (type != Enums.ENTITYTYPE.ROCKET && type != Enums.ENTITYTYPE.STONE && type != Enums.ENTITYTYPE.CHEST) body = Box2DHelper.createBody(game.box2d.world, width, height, this.pos, BodyDef.BodyType.StaticBody, true);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (type != Enums.ENTITYTYPE.ROCKET) super.draw(batch);
        else {
            int index = game.safe.level-1;
            if (index >= region.length)  index = region.length-1;
            height = region[index].getRegionHeight();
            width = region[index].getRegionWidth();
            batch.draw(region[index], pos.x, pos.y, width, height);
        }
    }
}
