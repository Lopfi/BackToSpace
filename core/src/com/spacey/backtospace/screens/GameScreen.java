package com.spacey.backtospace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spacey.backtospace.Entity.Enemy;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.UI.Item;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Entity.UI.UI;
import com.spacey.backtospace.Helper.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.gameMap;
import com.spacey.backtospace.box2d.ContactListener;


public class GameScreen extends ScreenAdapter {

    private final GameClass game;
    private Control control;

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Matrix4 screenMatrix;

    public gameMap gameMap;
    public Player player;
    public Enemy enemy1;
    public UI ui;
    private Texture background;
    public boolean paused;
    public boolean chest;

    private float stateTime;
    String PopUpMessage; //if its empty nothing will be shown, else it shows it
    public Fixture touchedFixture;


    public GameScreen(GameClass game) {
        this.game = game;
        game.safe.loadChestInventory();
        camera = game.camera;
        batch = new SpriteBatch();
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        gameMap = new gameMap(game);
        player = new Player(new Vector3(game.safe.playerX, game.safe.playerY, 0), game);
        enemy1 = new Enemy(new Vector3(300, 100, 0), game);
        ui = new UI(game, control, player);
        game.box2d.world.setContactListener(new ContactListener(this));
        PopUpMessage = "";
        background = game.assets.manager.get("tiles/background.png", Texture.class);
        chest = false;
        paused = false;
        chest = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
        player.updateSkin();
        //load the music and play
        if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
        }
        paused = false;
        chest = false;
        control.reset();
        player.createBox(player.pos);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (ui.pauseBtn.pressed && !chest) {
            paused = !paused;
            game.safe.playerX = player.body.getPosition().x;
            game.safe.playerY = player.body.getPosition().y - .1f;
            game.safe.save();
            game.safe.saveInventory(player.inventory);
        }

        if (paused) {
            if (!chest) {
                if (control.isPressed(Keys.SPACE)) paused = false;
                if (control.isPressed(Keys.B)) game.setScreen(new TitleScreen(game));
                if (control.isPressed(Keys.E)) game.setScreen(new SettingsScreen(game));
                if (control.isPressed(Keys.X)) Gdx.app.exit();
            }
        } else {
            if (!ui.pauseBtn.pressed && !chest) {
                player.update(control);
                enemy1.moveRandom();
                camera.position.lerp(player.pos, .1f);
                camera.update();
            }

            if (!chest && (control.isPressed(Keys.Q) || control.isPressed(Keys.ESCAPE))) {
                game.safe.playerX = player.body.getPosition().x;
                game.safe.playerY = player.body.getPosition().y - .1f;
                game.safe.save();
                game.safe.saveInventory(player.inventory);
                paused = true;
            }

            if (enemy1.untilActive > 1) {//Logic to make the enemy inactive shortly
                enemy1.untilActive--;
            } else if (enemy1.untilActive == 1) {
                enemy1.body.setActive(true);
                enemy1.untilActive = 0;
            }

            if (!PopUpMessage.isEmpty() && control.isPressed(Keys.X)) PopUpMessage = "";
        }

        ui.update();

        if (touchedFixture != null && !paused) { // check if the player is currently touching something
            if (touchedFixture == enemy1.getFixture()) {
                game.safe.life--;
                enemy1.update(0, 0, false);
                enemy1.body.setActive(false);
                enemy1.untilActive = 70;
                if (game.safe.life == 0) {
                    game.safe.initialize();
                    game.safe.load();
                    game.safe.save();
                    game.setScreen(new EndScreen(game, false));
                }
            }
            if (control.isPressed(Keys.E) || Gdx.input.isButtonPressed(Buttons.RIGHT)) { // only continue if player is trying to pick something up
                for (int i = 0; i < gameMap.entities.size(); i++) { // find the entity of the touched fixture
                    Entity currentEntity = gameMap.entities.get(i);
                    if (currentEntity.getFixture() == touchedFixture) {
                        if (currentEntity.type == Enums.ENTITYTYPE.COIN) game.safe.coins ++;
                        else if (currentEntity.type == Enums.ENTITYTYPE.CHEST) {
                            PopUpMessage = ""; //make sure its not displayed
                            paused = true;
                            chest = true;
                            break;
                        }
                        else if (currentEntity.type == Enums.ENTITYTYPE.ROCKET) {
                            if (player.inventory.has(Enums.requiredItems[game.safe.level])) {
                                player.inventory.remove(Enums.requiredItems[game.safe.level]);
                                game.safe.level++;
                                if (game.safe.level >= Enums.tasks.length-1) {
                                    game.safe.level = 1;
                                    game.setScreen(new EndScreen(game, true));
                                }
                                touchedFixture = null;
                            }
                            //if you lost the game do this: else game.setScreen(new EndScreen(game, false));
                            else PopUpMessage = "You need: " + player.inventory.missing(Enums.requiredItems[game.safe.level]);
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
        }

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();

        batch.draw(background, -200, -200);

        gameMap.draw(batch, (Control.debug && !paused));
        if (!paused) {
            player.drawAnimation(batch, stateTime); //idk if we want to hide the player but i think it should not animate in pause
            enemy1.drawAnimation(batch, stateTime);
        }
        gameMap.drawEntities(batch); // draw entities over player //sounds dumb but idk

        //BELOW USES SCREEN COORDINATES INSTEAD OF MAP
        batch.setProjectionMatrix(screenMatrix);

        ui.draw(batch);
        player.inventory.draw(batch);
        if (!PopUpMessage.isEmpty()) ui.showMessage(batch, PopUpMessage + " [X] Close");

        batch.end();

        if (!paused) game.box2d.tick(camera, control);
    }

    @Override
    public void hide() {
        game.box2d.world.destroyBody(player.body);
        if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
        }
    }
}