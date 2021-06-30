package com.spacey.backtospace;

import java.util.*;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.Structure;
import com.spacey.backtospace.Entity.Tile;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class gameMap {
    // TILES
    Texture ground0, ground1, ground2, ground3, ground4;
    Texture border;
    Texture devGrid;

    Texture[] ground;

    int height;
    int width;

    final int borderWidth;

    private ArrayList<Tile> tiles;
    public ArrayList<Entity> entities;

    private Box2DWorld box2d;
    private GameClass game;
    
    public gameMap(GameClass game){
        this.game = game;
        height = 50;
        width = 50;
        borderWidth = 1;
        tiles = new ArrayList<>();
        entities = new ArrayList<>();
        getImages(game.assets.manager);
        setup_tiles();
        box2d = game.box2d;
        generateHitboxes(box2d);
        spawnEntities();
    }

    public void draw(SpriteBatch batch, boolean debug) {
        for(Tile tile : tiles) {
            tile.draw(batch);
            if (debug) batch.draw(devGrid, tile.pos.x, tile.pos.y, tile.width, tile.height);
        }
    }

    public void drawEntities(SpriteBatch batch) {
        //Entitys zeichnen
        for(Entity entity : entities) {
            entity.draw(batch);
        }
    }

    public void addEntity(Entity newEntity) {
        entities.add(newEntity);
    }

    public void deleteEntity(Entity oldEntity) {
        for(int i = 0; i < entities.size(); i++ ){
            if(entities.get(i) == oldEntity){
                box2d.world.destroyBody(entities.get(i).body);
                entities.remove(i);
                return;
            }
        }
    }

    private void spawnEntities() {
        HashMap<Enums.ENTITYTYPE, Integer> thingsToSpawn = new HashMap<>();
        thingsToSpawn.put(Enums.ENTITYTYPE.FUEL, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.SCREW, 2);
        thingsToSpawn.put(Enums.ENTITYTYPE.SCREWDRIVER, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.ROCKET, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.KEY, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.COIN, 4);
        thingsToSpawn.put(Enums.ENTITYTYPE.STONE, 30);

        Set<Map.Entry<Enums.ENTITYTYPE, Integer>> set = thingsToSpawn.entrySet();
        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            for (int i = 0; i < (int) entry.getValue(); i++) {
                Vector2 pos;
                Entity newEntity = null;
                do {
                    if (newEntity != null) deleteEntity(newEntity);
                    pos = randomPos();
                    newEntity = new Structure((Enums.ENTITYTYPE) entry.getKey(), game, pos.x, pos.y);
                    addEntity(newEntity);
                } while (is_borderCoordinates( (int) (pos.x + newEntity.width), (int) (pos.y - newEntity.height)));
            }
        }
    }

    private Vector2 randomPos() {
        int x, y;
        do {
            x = MathUtils.random((height * 16));
            y = MathUtils.random((height * 16));
        } while(is_borderCoordinates(x, y));
        return  new Vector2(x, y);
    }

    private void setup_tiles(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile;
                if (is_border(x,y)) tile = new Tile(x, y, TILETYPE.BORDER, border);
                else tile = new Tile(x, y, TILETYPE.GROUND, random_ground());
                tiles.add(tile);
            }
        }
    }
    
    private Texture random_ground(){
        int chance = MathUtils.random(100);
        int random = MathUtils.random(ground.length-1);
        if (chance >= 35) return ground[0];
        if (chance >= 2) return ground[random];
        return ground4;
    }

    private boolean is_border(int x, int y) {
        return y < borderWidth || x < borderWidth || x >= width - borderWidth || y >= height - borderWidth;
    }

    private boolean is_borderCoordinates(int x, int y) {
        int borderWidth = this.borderWidth * 16;
        return y <= borderWidth || x <= borderWidth || x >= width * 16 - borderWidth+1 || y >= height * 16- borderWidth+1;
    }

    private void getImages(AssetManager manager){

        ground0 = manager.get("tiles/ground/ground0.png", Texture.class);
        ground1 = manager.get("tiles/ground/ground1.png", Texture.class);
        ground2 = manager.get("tiles/ground/ground2.png", Texture.class);
        ground3 = manager.get("tiles/ground/ground3.png", Texture.class);
        ground4 = manager.get("tiles/ground/ground4.png", Texture.class);

        border = manager.get("tiles/empty.png", Texture.class);
        devGrid = manager.get("tiles/dev_grid.png", Texture.class);

        ground = new Texture[]{ground0, ground1, ground2, ground3};
    }

    private void generateHitboxes(Box2DWorld box2D) {
        for(Tile tile : tiles){
                if(tile.isNotPassable()){
                    Box2DHelper.createBody(box2D.world, tile.width, tile.height, tile.pos, BodyDef.BodyType.StaticBody, false);
                }
        }
    }
}