package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.spacey.backtospace.Entity.UI.Button;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;

import java.util.Set;

public class TitleScreen extends ScreenAdapter {

    GameClass game;
    Control control;
    SpriteBatch batch;

    private final Matrix4 screenMatrix;

    Button startBtn;
    Button tutorialBtn;
    Button settingsBtn;
    Button shopBtn;
    Button creditsBtn;
    Button quitBtn;

    //Story tasks its basically the mission you have to do! YAY :)
    public static String[] Tasks = {
        "Please remove your Data because you start at level 1",
        "Find the main body of your rocket and put it up", // für jeden schritt wird eine Schraube und der Schraubenzieher benötigt der wird aber nicht verbraucht
        "Find and use a screwdriver with 1x screw and 1x body plate to repair the main body",
        "Find and attach the lower left fin with 1x screw to your rocket",
        "Find and attach 1x body plate with 1x screw to your rocket",
        "Find and attach the    nosecone with 1x screw to your rocket",
        "Find 2x fuel canisters to fill up your rocket",
        "Use the key to enter and start your rocket",
        "Say Goodbye and get back home, Safely."
    };

    public TitleScreen(GameClass game) {
        this.game = game;
        game.isPaused = false;
        batch = game.batch;

        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));


        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.camera);

        startBtn = new Button(game, control, game.assets.manager.get("ui/buttons/start.png", Texture.class), true, 100, 420);
        tutorialBtn = new Button(game, control, game.assets.manager.get("ui/buttons/help.png", Texture.class), true, 100, 340);
        settingsBtn = new Button(game, control, game.assets.manager.get("ui/buttons/settings.png", Texture.class), true, 100, 260);
        creditsBtn = new Button(game, control, game.assets.manager.get("ui/buttons/credits.png", Texture.class), true, 100, 180);
        quitBtn = new Button(game, control, game.assets.manager.get("ui/buttons/quit.png", Texture.class), true, 100, 100);
        shopBtn = new Button(game, control, game.assets.manager.get("ui/buttons/shop.png", Texture.class), true, 1200, 20);
    }

    @Override
    public void show(){
        /*if (game.safe.playMusic) {
            game.introSound.pause();
            game.gameSound.pause();
            long SoundId = game.gameSound.loop();
            game.gameSound.setVolume(SoundId, game.safe.playVolume);
            //mp3Sound.stop(id);
        }  THIS NEED TO BE REMOVE IN NEAR FUTURE*/

        /*
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(game.gameScreen);
                }
                if (keyCode == Input.Keys.C) {
                    game.setScreen(new CreditScreen(game));
                }
                if (keyCode == Input.Keys.S) {
                    game.setScreen(new SettingsScreen(game));
                }
                if (keyCode == Input.Keys.H) {
                    game.setScreen(new HelpScreen(game));
                }
                if (keyCode == Input.Keys.D) {
                    game.setScreen(new ShopScreen(game));
                }
                return true;
            }
        }); */
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startBtn.update();
        tutorialBtn.update();
        settingsBtn.update();
        shopBtn.update();
        creditsBtn.update();
        quitBtn.update();

        if (startBtn.pressed) game.setScreen(game.gameScreen);
        if (tutorialBtn.pressed) game.setScreen(new HelpScreen(game));
        if (settingsBtn.pressed) game.setScreen(new SettingsScreen(game));
        if (shopBtn.pressed) game.setScreen(new ShopScreen(game));
        if (creditsBtn.pressed) game.setScreen(new CreditScreen(game));
        if (quitBtn.pressed) Gdx.app.exit();

        batch.begin();
        batch.draw(game.assets.manager.get("screens/background.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(game.assets.manager.get("screens/backtospace.png", Texture.class), Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .77f, 400, 140);
        game.font.draw(batch, "Level: ["+game.safe.level+"]     Coins: ["+game.safe.coins+"]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .71f);
        game.font.draw(batch, "Task:  " + Tasks[game.safe.level], Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(batch, "[H] Tutorial/Help", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .36f);
        game.font.draw(batch, "[D] Design/Shop", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .32f);
        game.font.draw(batch, "[S] Settings", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .44f);
        game.font.draw(batch, "[C] Credits", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(batch, "->  [SPACE] PLAY  <-", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .25f);

        batch.setProjectionMatrix(screenMatrix);
        startBtn.draw(batch);
        tutorialBtn.draw(batch);
        settingsBtn.draw(batch);
        shopBtn.draw(batch);
        creditsBtn.draw(batch);
        quitBtn.draw(batch);

        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}