package com.spacey.backtospace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.UI.Item;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.Structure;
import com.spacey.backtospace.Entity.UI.UI;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.DataSafe;
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
    private final Control control;

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Matrix4 screenMatrix;

    private Map map;
    private Player player;
    private UI ui;

    private Structure stone;
    private Structure rocket;

    private float stateTime;

    public Fixture touchedFixture;


    public GameScreen(GameClass game) {
        this.game = game;
        this.camera = game.camera;

        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        batch = new SpriteBatch();
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, control.screenWidth, control.screenHeight));

        map = new Map(game);
        player = new Player(new Vector3(game.playerX, game.playerY, 0), game);
        ui = new UI(game, control);

        game.box2d.world.setContactListener(new ContactListener(this));

        stone = new Structure(Enums.ENTITYTYPE.STONE, game, 300, 300);
        rocket = new Structure(Enums.ENTITYTYPE.ROCKET, game, 459, 500);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);

        map.addEntity(stone);
        map.addEntity(rocket);

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
                        player.inventory.addItem(new Item(currentEntity.type, game)); // add the item to the inv
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

        if (control.Q || control.esc) {
            if (!game.isPaused) {
                game.safe.write("playerx", player.pos.x);
                game.safe.write("playery", player.pos.y);
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
            else if (control.Space || control.esc) game.isPaused = false;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();

        map.draw(batch, (control.debug && !game.isPaused));

        if (!game.isPaused) player.drawAnimation(batch, stateTime); //idk if we want to hide the player but i think it should not animate in pause


        //BELOW USES SCREEN COORDINATES INSTEAD OF MAP
        batch.setProjectionMatrix(screenMatrix);

        ui.draw(batch);
        player.inventory.draw(batch);

        batch.end();

        if (!game.isPaused && !ui.pauseBtn.pressed) game.box2d.tick(camera, control);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        game.box2d.world.destroyBody(player.body);
    }
}