package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.spacey.backtospace.Entity.UI.Button;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Enums;

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

    public TitleScreen(GameClass game) {
        this.game = game;
        game.isPaused = false;
        batch = game.batch;

        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        control = new Control(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.camera);
        int buttonX = Gdx.graphics.getWidth()/2-200;

        startBtn = new Button(game, control, game.assets.manager.get("ui/buttons/start.png", Texture.class), true, buttonX, 370);
        tutorialBtn = new Button(game, control, game.assets.manager.get("ui/buttons/help.png", Texture.class), true, buttonX, 290);
        settingsBtn = new Button(game, control, game.assets.manager.get("ui/buttons/settings.png", Texture.class), true, buttonX, 210);
        creditsBtn = new Button(game, control, game.assets.manager.get("ui/buttons/credits.png", Texture.class), true, buttonX, 130);
        quitBtn = new Button(game, control, game.assets.manager.get("ui/buttons/quit.png", Texture.class), true, buttonX, 50);
        shopBtn = new Button(game, control, game.assets.manager.get("ui/buttons/shop.png", Texture.class), true, buttonX*2+100, 50);

    }

    @Override
    public void show(){
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

        if (control.isPressed(Keys.SPACE) || startBtn.pressed) game.setScreen(game.gameScreen);
        if (control.isPressed(Keys.C) || creditsBtn.pressed) game.setScreen(new CreditScreen(game));
        if (control.isPressed(Keys.E) || settingsBtn.pressed) game.setScreen(new SettingsScreen(game));
        if (control.isPressed(Keys.H) || tutorialBtn.pressed) game.setScreen(new HelpScreen(game));
        if (control.isPressed(Keys.Q) || quitBtn.pressed) Gdx.app.exit();
        if (control.isPressed(Keys.S) || shopBtn.pressed) game.setScreen(new ShopScreen(game));

        batch.begin();
        batch.draw(game.assets.manager.get("screens/background.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(game.assets.manager.get("screens/backtospace.png", Texture.class), Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .77f, 400, 140);
        game.font.draw(batch, "Level: ["+game.safe.level+"]     Coins: ["+game.safe.coins+"]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .71f);
        game.font.draw(batch, "Task:  " + Enums.tasks[game.safe.level], Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .65f);

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