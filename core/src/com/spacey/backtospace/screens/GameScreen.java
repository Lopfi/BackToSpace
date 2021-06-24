package com.spacey.backtospace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.Item;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.Structure;
import com.spacey.backtospace.Entity.UI.UI;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Datasave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ScreenAdapter;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.Map;
import com.spacey.backtospace.box2d.ContactListener;


public class GameScreen extends ScreenAdapter {

    private final GameClass game;
    public Datasave saver = new Datasave();
    private Control control;

    private final OrthographicCamera camera;
    private float stateTime;
    private SpriteBatch batch;
    private Matrix4 screenMatrix;

    private Map map;
    private Player player;
    private UI ui;

    private Structure stone;
    private Structure rocket;

    public Fixture touchedFixture;


    public GameScreen(GameClass game) {
        this.game = game;
        this.camera = game.camera;
        game.font.getData().setScale(0.5f);
    }

    @Override
    public void show() {
        // Used to capture Keyboard Input
        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        Gdx.input.setInputProcessor(control);
        batch = new SpriteBatch();
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, control.screenWidth, control.screenHeight));

        map = new Map(game);
        player = new Player(new Vector3(game.playerX, game.playerY, 0), game);
        ui = new UI(game, control);

        game.box2d.world.setContactListener(new ContactListener(this));

        stone = new Structure(Enums.STRUCTURETYPE.STONE, game, 300, 300);
        rocket = new Structure(Enums.STRUCTURETYPE.ROCKET, game, 459, 500);

        map.addEntity(stone);
        map.addEntity(rocket);

        Gdx.app.log("Player fixture", String.valueOf(player.getFixture()));

        //load the music and play
        if (game.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.playVolume);
            //mp3Sound.stop(id);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (touchedFixture != null) { // check if the player is currently touching something
            if (control.E) { // only continue if player is trying to pick something up
                for (int i = 0; i < map.entities.size(); i++) { // find the entity of the touched fixture
                    Entity currentEntity = map.entities.get(i);
                    if (currentEntity.getFixture() == touchedFixture) {
                        map.deleteEntity(currentEntity); // delete the collider of the entity
                        //TODO: check the type of the entity and add the right item
                        player.inventory.addItem(new Item(Enums.ITEMTYPE.STONE, game)); // add the item to the inv
                        break;
                    }
                }
            }
            touchedFixture = null; // reset the touched fixture
        }

        ui.update();

        if (ui.pauseBtn.pressed) game.isPaused = !game.isPaused;
        if (!game.isPaused && !ui.pauseBtn.pressed) {
            player.update(control);
            camera.position.lerp(player.pos, .1f);
            camera.update();
        }

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();

        map.draw(batch, (control.debug && !game.isPaused));

        if (!game.isPaused)
            player.drawAnimation(batch, stateTime);//idk if we want to hide the player but i think it should not animate in pause
        if (control.debug && !game.isPaused)
            game.font.draw(batch, "x:" + Math.round(camera.position.x) + " y:" + Math.round(camera.position.y), camera.position.x - 50, camera.position.y - 20);

        if (control.Q || control.esc) {
            if (!game.isPaused) {
                saver.write("playerx", player.pos.x);
                saver.write("playery", player.pos.y);
                game.playerX = player.pos.x;
                game.playerY = player.pos.y;
                game.isPaused = true;
            }
        }
        if (game.isPaused) {
            if (control.B) {
                //load the music and play
                if (game.playMusic) {
                    game.introSound.pause();
                    game.gameSound.pause();
                    long SoundId = game.gameSound.loop();
                    game.gameSound.setVolume(SoundId, game.playVolume);
                    //mp3Sound.stop(id);
                }
                game.setScreen(new TitleScreen(game));
            } else if (control.E) game.setScreen(new SettingsScreen(game));
            else if (control.X) Gdx.app.exit();
            else if (control.Space) game.isPaused = false;

        }

        //BELOW USES SCREEN COORDINATES INSTEAD OF MAP
        batch.setProjectionMatrix(screenMatrix);

        //Nicht löschen!! ===> für Bene für Montag
        //
        //Texture coin = new Texture("menu/coin.png");
        //batch.draw(coin, ?, ?, coin.getWidth()*game.uiScale, coin.getHeight()*game.uiScale);
        // Für Dialoge ==> "Hey you! Your Rocket Broke and now your lost in Space repair it ..."
        //Statusbar.create(batch, control.screenWidth, "Hello");

        ui.draw(batch);


        if (game.isPaused) {
            batch.draw(game.assets.manager.get("menu/options.png", Texture.class), control.screenWidth / 4, control.screenHeight / 5, (control.screenWidth / 4) * 2, (control.screenHeight / 5) * 3);
        }
        player.inventory.draw(batch);
        batch.end();

        if (!game.isPaused && !ui.pauseBtn.pressed) game.box2d.tick(camera, control);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        game.box2d.world.destroyBody(player.body);
        game.font.getData().setScale(1f);
    }
}