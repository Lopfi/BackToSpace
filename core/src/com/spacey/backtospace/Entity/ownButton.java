package com.spacey.backtospace.Entity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.spacey.backtospace.Helper.Control;

public class ownButton extends ApplicationAdapter {
    Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

    public void create (Stage stage, String text, InputListener listen,float width, float height, float x, float y) {
    Gdx.input.setInputProcessor(stage);
    TextButton button2 = new TextButton(text,mySkin,"small");
    button2.setSize(width, height);
    button2.setPosition(x, y);
    button2.addListener(listen);
    stage.addActor(button2);
    //stage.act();
    //stage.draw();
    }
  }