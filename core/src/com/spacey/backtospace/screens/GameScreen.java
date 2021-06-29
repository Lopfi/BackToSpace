package com.spacey.backtospace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.UI.Inventory;
import com.spacey.backtospace.Entity.UI.Item;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.UI.UI;
import com.spacey.backtospace.Helper.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ScreenAdapter;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.gameMap;
import com.spacey.backtospace.box2d.ContactListener;

import java.util.Arrays;


public class GameScreen extends ScreenAdapter {

    private final GameClass game;
    private Control control;

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Matrix4 screenMatrix;

    public gameMap gameMap;
    public Player player;
    public UI ui;

    private float stateTime;
    String PopUpMessage; //if its empty nothing will be shown, else it shows it
    public Fixture touchedFixture;


    public GameScreen(GameClass game) {
        this.game = game;
        camera = game.camera;
        batch = new SpriteBatch();
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        gameMap = new gameMap(game);
        player = new Player(new Vector3(game.safe.playerX, game.safe.playerY, 0), game);
        ui = new UI(game, control);
        game.box2d.world.setContactListener(new ContactListener(this));
        PopUpMessage = "";
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(control);
        if (player.texture != game.assets.manager.get("player/spaceman_walk" + String.valueOf(game.safe.currentSkin) + ".png", Texture.class)){
            Inventory temp = player.inventory;
            Vector3 temppos = player.pos;
            game.box2d.world.destroyBody(player.body);
            player = new Player(temppos, game);
            player.inventory = temp;
        }
        //load the music and play
        if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
            //mp3Sound.stop(id);
        }

        game.isPaused = false;
        control.reset();
        player.createBox(player.pos);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        if (touchedFixture != null) { // check if the player is currently touching something
            if (control.E) { // only continue if player is trying to pick something up
                for (int i = 0; i < gameMap.entities.size(); i++) { // find the entity of the touched fixture
                    Entity currentEntity = gameMap.entities.get(i);
                    if (currentEntity.getFixture() == touchedFixture) {
                        if (currentEntity.type == Enums.ENTITYTYPE.COIN) game.safe.coins ++;
                        else if (currentEntity.type == Enums.ENTITYTYPE.ROCKET) {
                            Enums.ENTITYTYPE[] required = new Enums.ENTITYTYPE[] {Enums.ENTITYTYPE.FUEL, Enums.ENTITYTYPE.SCREW, Enums.ENTITYTYPE.SCREWDRIVER};
                            if (player.inventory.has(required)) {
                                player.inventory.remove(required);
                                game.safe.level++;
                                game.setScreen(new EndScreen(game, true));
                            }
                            //if you lost the game do this: else game.setScreen(new EndScreen(game, false));
                            else PopUpMessage = "You forgot: " + player.inventory.missing(required);
                            break;
                        }
                        else if (currentEntity.type == Enums.ENTITYTYPE.LIFE) game.safe.life ++;
                        else if (!player.inventory.add(new Item(currentEntity.type, game))) break;
                        else if (currentEntity.type == Enums.ENTITYTYPE.TILE) break;
                        gameMap.deleteEntity(currentEntity); // delete the collider of the entity
                        break;
                    }
                }
            }
            touchedFixture = null; // reset the touched fixture
        }

        ui.update();

        if (!game.isPaused && !ui.pauseBtn.pressed) {
            player.update(control);
            camera.position.lerp(player.pos, .1f);
            camera.update();
        }

        if (ui.pauseBtn.pressed) {
            game.isPaused = !game.isPaused;
            //TODO make pause logic nicer --- ArE yOu SuRe AbOuT tHat ?
            game.safe.playerX = player.body.getPosition().x;
            game.safe.playerY = player.body.getPosition().y -.1f;
            game.safe.save();
        }

        if (control.Q || control.esc) {
            if (!game.isPaused) {
                game.safe.playerX = player.pos.x;
                game.safe.playerY = player.pos.y;
                game.safe.save();
                game.isPaused = true;
            }
        }

        if (game.isPaused) {
            if (control.Space) game.isPaused = false;
            if (control.B) {
                if (game.safe.playMusic) {
                    game.introSound.pause();
                    game.gameSound.pause();
                    long SoundId = game.introSound.loop();
                    game.introSound.setVolume(SoundId, game.safe.playVolume);
                    //mp3Sound.stop(id);
                }
                game.setScreen(new TitleScreen(game));
            }
            if (control.E) game.setScreen(new SettingsScreen(game));
            if (control.X) Gdx.app.exit();
        } else if (!PopUpMessage.isEmpty()){
            if (control.X) PopUpMessage = "";
        }

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.begin();

        gameMap.draw(batch, (Control.debug && !game.isPaused));
        if (!game.isPaused) player.drawAnimation(batch, stateTime); //idk if we want to hide the player but i think it should not animate in pause
        gameMap.drawEntities(batch); // draw entities over player

        //BELOW USES SCREEN COORDINATES INSTEAD OF MAP
        batch.setProjectionMatrix(screenMatrix);

        ui.draw(batch);
        player.inventory.draw(batch);
        if (!PopUpMessage.isEmpty()){
            ui.showMessage(batch, PopUpMessage + " [X] Close");
        }

        batch.end();

        if (!game.isPaused && !ui.pauseBtn.pressed) game.box2d.tick(camera, control);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        game.box2d.world.destroyBody(player.body);
    }
}