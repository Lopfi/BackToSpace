package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Enums;

// display different elements of the ui in the game-screen
public class UI extends UIElement{
    public Button pauseBtn;
    private GameClass game;
    private Control control;
    private UIElement pauseScreen;
    private UIElement lives;
    private UIElement coins;
    public String textFieldText;
    Chest chest;
    Player player;

    public UI(GameClass game, Control control, Player player) {
        super(game);
        this.game = game;
        this.control = control;
        this.player = player;
        lives = new UIElement(game, game.assets.manager.get("ui/heart.png", Texture.class));
        coins = new UIElement(game, game.assets.manager.get("ui/coin.png", Texture.class));
        coins.height = ((coins.height/scale)/2) * scale;
        coins.width = ((coins.width/scale)/2) * scale;
        pauseScreen = new UIElement(game, game.assets.manager.get("ui/pause.png", Texture.class));
        pauseScreen.pos.x = control.screenWidth / 4f;
        pauseScreen.pos.y = control.screenHeight / 5f;
        pauseScreen.width = (control.screenWidth / 4f) * 2;
        pauseScreen.height = (control.screenHeight / 5f) * 3;
        pauseBtn = new Button(game, control, game.assets.manager.get("ui/buttons/pauseBtn.png", Texture.class), true, 100, 100);
        pauseBtn.pos = new Vector3(control.screenWidth - pauseBtn.width - 10, control.screenHeight - pauseBtn.height - 10, 0);
        chest = new Chest(game, control);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw pause button
        pauseBtn.draw(batch);
        //draw lives
        for (int i = 0; i < game.safe.life; i++) {
            lives.pos = new Vector3(i * (lives.width + 4)+4, control.screenHeight - lives.height, 0);
            lives.draw(batch);
        }

        coins.pos = new Vector3(14, control.screenHeight - lives.height - coins.height -10, 0);
        coins.draw(batch);
        game.font.getData().setScale(2);
        game.font.draw(batch, String.valueOf(game.safe.coins) , coins.width + 20, control.screenHeight - lives.height - (coins.height/2) +3);
        game.font.getData().setScale(1);

        //draw extra GameClass screens
        if (game.gameScreen.paused && !game.gameScreen.chest) pauseScreen.draw(batch);
        if (game.gameScreen.chest) chest.act(batch, player);

        // draw coordinates for dev mode
        if (Control.debug) game.font.draw(batch, "x:" + Math.round(game.camera.position.x) + " y:" + Math.round(game.camera.position.y), control.screenWidth/2f, control.screenHeight- 20);
        if (game.safe.showTask) game.font.draw(batch, "Lv " + game.safe.level + ": " + Enums.tasks[game.safe.level], 4, 20);
    }

    public void showMessage(SpriteBatch batch, String text) {
        batch.draw(game.assets.manager.get("ui/textbox.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/8f);
        game.font.draw(batch, text, Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/8f/2);
    }

    public void update() {
        pauseBtn.update();
        //handle updates for textFields
    }
}
