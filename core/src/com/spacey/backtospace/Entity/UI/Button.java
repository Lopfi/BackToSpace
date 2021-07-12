package com.spacey.backtospace.Entity.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Control;

// clickable button with a texture
// TODO: deprecate all scene2d buttons
public class Button extends UIElement {

    Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    Control control;
    Boolean visible;

    private boolean clicked;
    public boolean pressed;
    TextButton scene2dButton;
    //scene2d button (needs to be deprecated)
    public Button(GameClass game, Stage stage, String text, InputListener listen, float width, float height, float x, float y){
        super(game);
        clicked = false;
        pressed = false;
        Gdx.input.setInputProcessor(stage);
        scene2dButton = new TextButton(text,mySkin,"small");
        scene2dButton.setSize(width, height);
        scene2dButton.setPosition(x, y);
        Label style = scene2dButton.getLabel();
        style.setColor(new Color(.49f, 0f, .49f, 1f));
        //style.setFontScale(style.getFontScaleX()*1.5f, style.getFontScaleY()*1.5f);
        style.sizeBy(3);
        scene2dButton.addListener(listen);
        stage.addActor(scene2dButton);
    }

    public void remove() {
        scene2dButton.remove();
    }

    public void setLabel(Label label) {
        scene2dButton.setLabel(label);
    }

    public Label getLabel() {
        return scene2dButton.getLabel();
    }

    public Button(GameClass game, Control control, Texture texture, boolean visible, float x, float y){
        super(game, texture);
        clicked = false;
        pressed = false;
        this.control = control;
        this.visible = visible;
        pos.x = x;
        pos.y = y;
    }

    public void update() {
        if (control.LMB && mouseOnButton() && !clicked && !pressed) {
            pressed = true;
            clicked = true;
        }
        else {
            pressed = false;
            if(!control.LMB)clicked = false;
        }
    }

    private boolean mouseOnButton() {
        return control.mouse_click_pos.x <= pos.x+width && control.mouse_click_pos.x >= pos.x && control.mouse_click_pos.y <= pos.y+height && control.mouse_click_pos.y >= pos.y;
    }
  }