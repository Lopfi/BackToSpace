package com.spacey.backtospace;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.Tile;
import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class Map {
    // TILES
    Texture ground0, ground1, ground2, ground3;
    Texture border0, border1, border2, border3, border4, border5, border6, border7;
    Texture devGrid;

    Texture[] ground;
    Texture[] border;

    int height;
    int width;

    final int borderWidth;

    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    
    public Map(GameClass game){
        height = 50;
        width = 50;
        borderWidth = 5;
        tiles = new ArrayList<>();
        entities = new ArrayList<>();
        getImages(game.assets.manager);
        setup_tiles();
        generateHitboxes(game.box2d);
    }

    public void draw(SpriteBatch batch, boolean debug) {
        for(Tile tile : tiles) {
            tile.draw(batch);
            if (debug) batch.draw(devGrid, tile.pos.x, tile.pos.y, tile.width, tile.height);
        }
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
                entities.remove(i);
                break;
            }
        }
    }
    
    private void setup_tiles(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile;
                if (is_border(x,y)) tile = new Tile(x, y, TILETYPE.BORDER, random_border());
                else tile = new Tile(x, y, TILETYPE.GROUND, random_ground());
                tiles.add(tile);
            }
        }
    }
    
    private Texture random_ground(){
        int random = MathUtils.random(20);
        if (random >= ground.length) return ground[0];
        return ground[random];
    }

    private Texture random_border(){
        int random = MathUtils.random(20);
        if (random >= border.length) return border[0];
        return border[random];
    }

    private boolean is_border(int x, int y) {
        return y <= borderWidth || x <= borderWidth || x >= width - borderWidth+1 || y >= height - borderWidth+1;
    }

    private void getImages(AssetManager manager){

        ground0 = manager.get("tiles/ground/ground0.png", Texture.class);
        ground1 = manager.get("tiles/ground/ground1.png", Texture.class);
        ground2 = manager.get("tiles/ground/ground2.png", Texture.class);
        ground3 = manager.get("tiles/ground/ground3.png", Texture.class);

        border0 = manager.get("tiles/space/space0.png", Texture.class);
        border1 = manager.get("tiles/space/space1.png", Texture.class);
        border2 = manager.get("tiles/space/space2.png", Texture.class);
        border3 = manager.get("tiles/space/space3.png", Texture.class);
        border4 = manager.get("tiles/space/space4.png", Texture.class);
        border5 = manager.get("tiles/space/space5.png", Texture.class);
        border6 = manager.get("tiles/space/space6.png", Texture.class);
        border7 = manager.get("tiles/space/space7.png", Texture.class);

        devGrid = manager.get("tiles/dev_grid.png", Texture.class);

        ground = new Texture[]{ground0, ground1, ground2, ground3};
        border = new Texture[]{border0, border1, border2, border3, border4, border5, border6, border7};
    }

    private void generateHitboxes(Box2DWorld box2D) {
        for(Tile tile : tiles){
                if(tile.isNotPassable()){
                    Box2DHelper.createBody(box2D.world, tile.width, tile.height, tile.pos, BodyDef.BodyType.StaticBody);
                }
        }
    }

    public void dispose() {
        for (Texture texture : ground) texture.dispose();
        for (Texture texture : border) texture.dispose();
    }
}
