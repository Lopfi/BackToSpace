package com.spacey.backtospace;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.Structure;
import com.spacey.backtospace.Entity.Tile;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.Helper.Enums.ENTITYTYPE;
import com.spacey.backtospace.Helper.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class gameMap {
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
    public ArrayList<Entity> entities;

    private Box2DWorld box2d;
    private GameClass game;
    
    public gameMap(GameClass game){
        this.game = game;
        height = 50;
        width = 50;
        borderWidth = 5;
        tiles = new ArrayList<>();
        entities = new ArrayList<>();
        getImages(game.assets.manager);
        setup_tiles();
        box2d = game.box2d;
        generateHitboxes(box2d);
        spawnEntities();
        spawnEntitiesDelayed();
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

    private void spawn(ENTITYTYPE type){
        Vector2 pos;
        Entity newEntity = null;
        do {
            if (newEntity != null) deleteEntity(newEntity);
            pos = randomPos();
            newEntity = new Structure(type, game, pos.x, pos.y);
            addEntity(newEntity);
        } while (is_borderCoordinates( (int) (pos.x + newEntity.width), (int) (pos.y - newEntity.height)));
    }

    private void spawnEntities() {
        HashMap<Enums.ENTITYTYPE, Integer> thingsToSpawn = new HashMap<Enums.ENTITYTYPE, Integer>();
        thingsToSpawn.put(Enums.ENTITYTYPE.ROCKET, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.KEY, 1);
        thingsToSpawn.put(Enums.ENTITYTYPE.STONE, 25);

        Set set = thingsToSpawn.entrySet();
        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            for (int i = 0; i < (int) entry.getValue(); i++) {
                spawn((Enums.ENTITYTYPE) entry.getKey());
            }
        }
    }
    private void spawnEntitiesDelayed() {
        final Map<Enums.ENTITYTYPE, Integer[]> spawnmap = new HashMap<Enums.ENTITYTYPE, Integer[]>();
        //first integer = how many should spawn // secound what chances they have
        Integer coinsToSpawn;
        if (8 - game.safe.coins <= 0) coinsToSpawn = 0;
        else coinsToSpawn = 8 - game.safe.coins; // make sure only 8 coins spawn and if u have more than 8 dont spawn them :p

        spawnmap.put(Enums.ENTITYTYPE.COIN, new Integer[]{coinsToSpawn, 18});
        Gdx.app.log("CoinsSpawning", coinsToSpawn + "x");

        spawnmap.put(Enums.ENTITYTYPE.FUEL, new Integer[]{2, 70});
        spawnmap.put(Enums.ENTITYTYPE.SCREW, new Integer[]{6, 99});
        spawnmap.put(Enums.ENTITYTYPE.SCREWDRIVER, new Integer[]{2, 38});

        final Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(
            new TimerTask() {
                Map<Enums.ENTITYTYPE, Integer[]> map = spawnmap;

                @Override
                public void run() {
                    Integer leftToSpawn = 0;
                    Integer randomNumber = new Random().nextInt(100-0) + 0;
                    for (Map.Entry<Enums.ENTITYTYPE, Integer[]> entry : map.entrySet()) {
                        Gdx.app.log("Spawn", entry.getKey() + "/" + Arrays.toString(entry.getValue()));

                        Integer[] temp = entry.getValue();
                        if (temp[0] > 0 && randomNumber <= temp[1]){//if to spawn is more than 0 and if chance is given to spawn
                            temp[0]--;
                            map.put(entry.getKey(), temp); // to save the minus
                            spawn((Enums.ENTITYTYPE) entry.getKey()); //spawn it
                        }
                        leftToSpawn = leftToSpawn + temp[0];
                    }
                    if (leftToSpawn <= 0) { // if 
                        myTimer.cancel();
                        myTimer.purge();
                    }
                    Gdx.app.log("Spawning left", "" + leftToSpawn);
                }
            },
            15 * 1000,  // after 15 secounds first spawn
            4* 1000     // then spawn every 4 sec
        );
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

    private boolean is_borderCoordinates(int x, int y) {
        int borderWidth = this.borderWidth * 16;
        return y <= borderWidth || x <= borderWidth || x >= width * 16 - borderWidth+1 || y >= height * 16- borderWidth+1;
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
