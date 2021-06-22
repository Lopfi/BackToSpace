package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;

public class UI {
    public Button pauseBtn;
    public Lives lives;

    public UI(GameClass game, Control control) {
        pauseBtn = new Button(game, control, game.assets.manager.get("ui/PauseBtn.png", Texture.class), true, 100, 100);
        pauseBtn.pos = new Vector3(control.screenWidth - pauseBtn.width - 10, control.screenHeight - pauseBtn.height -10, 0);

        lives = new Lives(game);
    }

    public void draw(SpriteBatch batch) {
        pauseBtn.draw(batch);
        lives.draw(batch);
    }

    public void update() {
        pauseBtn.update();
    }
}
