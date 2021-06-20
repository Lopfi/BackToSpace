package com.spacey.backtospace.Entity;

import java.util.concurrent.Callable;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.spacey.backtospace.Helper.Control;

public class ownButton extends ApplicationAdapter {
    Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    SpriteBatch batch;
    Control control;
    String text;
    Boolean visible;
    Callable<Void> todo;
    float width;
    float height;
    float x;
    float y;

    private boolean normalclick;
    public boolean ispressed;

    public ownButton(){
      normalclick = false;
      ispressed = false;
    }

    public void create(Stage stage, String text, InputListener listen,float width, float height, float x, float y) {
      Gdx.input.setInputProcessor(stage);
      TextButton button2 = new TextButton(text,mySkin,"small");
      button2.setSize(width, height);
      button2.setPosition(x, y);
      button2.addListener(listen);
      stage.addActor(button2);
      //stage.act();
      //stage.draw();
    }
    
    //Added Button Support, for Stages
        //Stage stage = new Stage(new ScreenViewport());
        //    InputListener action = new InputListener(){
        //        @Override
        //        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        //            Gdx.app.log("BUTTON", "Pressed Text Button");
        //            return true;
        //        }
        //    };
        //    btn.create(stage, "Click me!", action, 400, 150, 200, 200);
    //}

    public void createNoStage(SpriteBatch ibatch, Control icontrol, String itext, Boolean ivisible, Callable<Void> itodo, float iwidth, float iheight, float ix, float iy){
      batch = ibatch;
      control = icontrol;
      text = itext;
      visible = ivisible;
      todo = itodo;
      width = iwidth;
      height = iheight;
      x = ix;
      y = iy;
    }
    public void renderNoStage(){
      ShapeRenderer shapeRenderer = new ShapeRenderer();
      BitmapFont font = new BitmapFont();

      if (control.LMB && control.mouse_click_pos.x <= x+width && control.mouse_click_pos.x >= x && control.mouse_click_pos.y <= y+height && control.mouse_click_pos.y >= y){
        if (visible) Gdx.gl.glLineWidth(5);
        ispressed = true;
      } else {
        if (visible) Gdx.gl.glLineWidth(3);
        ispressed = false;
      }
      if (visible){
      batch.end();
      shapeRenderer.begin(ShapeType.Filled);
      shapeRenderer.setColor(Color.WHITE);
      shapeRenderer.rect(x, y, width, height);
      shapeRenderer.end();
      shapeRenderer.begin(ShapeType.Line);
      shapeRenderer.setColor(Color.BLUE);
      shapeRenderer.rect(x, y, width, height);
      shapeRenderer.end();
      batch.begin();
      font.setColor(Color.RED);
      font.draw(batch, text, x+(width/6), y+(height/2));
    }
      if(control.LMB){
        normalclick = true;
      }

      if(normalclick && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
        //Gdx.app.log("click", control.mouse_click_pos.x + "-" + control.mouse_click_pos.y);
        normalclick = false;
        if (control.mouse_click_pos.x <= x+width && control.mouse_click_pos.x >= x && control.mouse_click_pos.y <= y+height && control.mouse_click_pos.y >= y){
          try {
            todo.call();
          } catch (Exception e) {
            e.printStackTrace();
            //for the cheesy case that the function passed has errors
          }
        }
      }
    }


            //Own Button Implementation:
            //
            //ownButton button = new ownButton();
            //class Test implements Callable {
            //  public Object call() {
            //      <The code you want to execute if the button was pressed>
            //      return null;
            //  }
            //}
          //
          //Callable<Void> todo = new Test();
          //button.createNoStage(batch, control, "Pause Game", true, todo, 111f, 35f, control.screenWidth-113f, control.screenHeight-37f);
          //
          //then:
          //button.renderNoStage()
  }