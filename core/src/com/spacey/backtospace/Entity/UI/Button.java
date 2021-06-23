package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;

public class Button extends UIElement {

    Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    Control control;
    Boolean visible;

    private boolean clicked;
    public boolean pressed;

    public Button(GameClass game, Stage stage, String text, InputListener listen, float width, float height, float x, float y){
        super(game);
        clicked = false;
        pressed = false;
        Gdx.input.setInputProcessor(stage);
        TextButton button2 = new TextButton(text,mySkin,"small");
        button2.setSize(width, height);
        button2.setPosition(x, y);
        button2.addListener(listen);
        stage.addActor(button2);
    }

    public Button(GameClass game, Control control, Texture texture, boolean visible, float x, float y){
        super(game);
        clicked = false;
        pressed = false;
        this.control = control;
        this.visible = visible;
        this.texture = this.initTexture(game.assets.manager.get("ui/PauseBtn.png", Texture.class));
        pos.x = x;
        pos.y = y;
    }

    public void update() {
        Gdx.app.log("lmb", String.valueOf(control.LMB));
        if (control.LMB && mouseOnButton() && !clicked && !pressed) {
            pressed = true;
            clicked = true;
        }
        else {
            pressed = false;
            if(!control.LMB )clicked = false;
        }
    }

    private boolean mouseOnButton() {
        return control.mouse_click_pos.x <= pos.x+width && control.mouse_click_pos.x >= pos.x && control.mouse_click_pos.y <= pos.y+height && control.mouse_click_pos.y >= pos.y;
    }
  }