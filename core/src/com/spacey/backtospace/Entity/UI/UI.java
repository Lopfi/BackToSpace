package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;

public class UI extends UIElement{
    public Button pauseBtn;
    private GameClass game;
    private Control control;
    private UIElement lives;
    private Texture textField;
    private Texture coins;
    public String textFieldText;

    public UI(GameClass game, Control control) {
        super(game);
        this.game = game;
        this.control = control;
        this.lives = new UIElement(game);
        lives.texture = lives.initTexture(game.assets.manager.get("menu/herz.png", Texture.class));
        pauseBtn = new Button(game, control, game.assets.manager.get("ui/PauseBtn.png", Texture.class), true, 100, 100);
        pauseBtn.pos = new Vector3(control.screenWidth - pauseBtn.width - 10, control.screenHeight - pauseBtn.height -10, 0);

    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw pause button
        pauseBtn.draw(batch);
        //draw lives
        for (int i = 0; i < game.life; i++) {
            lives.pos = new Vector3(i * (lives.width + 4)+4, control.screenHeight - lives.height, 0);
            lives.draw(batch);
        }
        //draw coins
        //draw coin texture
        // draw amount next to it (wäre cool mit 0001 das man das mit nullern auffüllt)

        //draw text field
        //draw textField texture
        //draw font
    }

    public void update() {
        pauseBtn.update();
    }
}
