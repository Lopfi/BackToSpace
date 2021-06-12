package com.spacey.backtospace;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.Tile;
import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class Map {
    // TILES
    Texture grass_01, grass_02, grass_03, grass_04;
    Texture water_01, water_02, water_03, water_04;
    Texture devGrid;

    Texture[] ground;
    Texture[] border;

    int height;
    int width;

    private ArrayList<Tile> tiles;
    
    public Map(Box2DWorld box2d){
        tiles = new ArrayList<Tile>();
        height = 50;
        width = 50;
        loadImages();
        setup_tiles();
        generateHitboxes(box2d);
    }

    public void draw(SpriteBatch batch, boolean debug) {
        for(Tile tile : tiles) {
            tile.draw(batch);
            if (debug) batch.draw(devGrid, tile.pos.x, tile.pos.y, 8, 8);
        }
    }
    
    private void setup_tiles(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile;
                if (is_border(x,y)) tile = new Tile(x, y,8, TILETYPE.BORDER, random_water());
                else tile = new Tile(x,y, 8, TILETYPE.GROUND, random_grass());
                tiles.add(tile);
            }
        }
    }
    
    private Texture random_grass(){
        int random = MathUtils.random(20);
        if (random >= ground.length) return ground[0];
        return ground[random];
    }

    private Texture random_water(){
        int random = MathUtils.random(20);
        if (random >= border.length) return border[0];
        return border[random];
    }

    private boolean is_border(int x, int y) {
        return y == 0 || x == 0 || x == width - 1 || y == height - 1;
    }

    private void loadImages(){
        // Source  https://opengameart.org/content/micro-tileset-overworld-and-dungeon
        // Example
        // http://opengameart.org/sites/default/files/styles/watermarked/public/Render_0.png
        grass_01 = new Texture("8x8/grass/grass_01.png");
        grass_02 = new Texture("8x8/grass/grass_02.png");
        grass_03 = new Texture("8x8/grass/grass_03.png");
        grass_04 = new Texture("8x8/grass/grass_04.png");
        
        water_01 = new Texture("8x8/water/water_01.png");
        water_02 = new Texture("8x8/water/water_02.png");
        water_03 = new Texture("8x8/water/water_03.png");
        water_04 = new Texture("8x8/water/water_04.png");

        devGrid = new Texture("dev-grid.png");

        ground = new Texture[]{grass_01, grass_02, grass_03, grass_04};
        border = new Texture[]{water_01, water_02, water_03, water_04};
    }

    private void generateHitboxes(Box2DWorld box2D) {
        for(Tile tile : tiles){
                if(tile.isNotPassable()){
                    Box2DHelper.createBody(box2D.world, tile.size, tile.size, tile.pos, BodyDef.BodyType.StaticBody);
                }
        }
    }

    private void drawDevGrid() {

    }

    public void dispose() {
        for (Texture texture : ground) texture.dispose();
        for (Texture texture : border) texture.dispose();
    }
}