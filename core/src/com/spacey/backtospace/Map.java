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
    Texture ground0, ground1, ground2, ground3;
    Texture border0, border1, border2, border3, border4, border5, border6, border7;
    Texture devGrid;

    Texture[] ground;
    Texture[] border;

    int height;
    int width;

    final int borderWidth;

    private ArrayList<Tile> tiles;
    
    public Map(Box2DWorld box2d){
        height = 50;
        width = 50;
        borderWidth = 5;
        tiles = new ArrayList<>();
        loadImages();
        setup_tiles();
        generateHitboxes(box2d);
    }

    public void draw(SpriteBatch batch, boolean debug) {
        for(Tile tile : tiles) {
            tile.draw(batch);
            if (debug) batch.draw(devGrid, tile.pos.x, tile.pos.y, tile.width, tile.height);
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

    private void loadImages(){
        // Source  https://opengameart.org/content/micro-tileset-overworld-and-dungeon
        // Example
        // http://opengameart.org/sites/default/files/styles/watermarked/public/Render_0.png
        ground0 = new Texture("tiles/ground/ground0.png");
        ground1 = new Texture("tiles/ground/ground1.png");
        ground2 = new Texture("tiles/ground/ground2.png");
        ground3 = new Texture("tiles/ground/ground3.png");

        border0 = new Texture("tiles/space/space0.png");
        border1 = new Texture("tiles/space/space1.png");
        border2 = new Texture("tiles/space/space2.png");
        border3 = new Texture("tiles/space/space3.png");
        border4 = new Texture("tiles/space/space4.png");
        border5 = new Texture("tiles/space/space5.png");
        border6 = new Texture("tiles/space/space6.png");
        border7 = new Texture("tiles/space/space7.png");

        devGrid = new Texture("tiles/dev_grid.png");

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
