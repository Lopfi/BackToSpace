package com.spacey.backtospace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    public static final int WIDTH = 270;
    public static final int HEIGHT = 480;
    final MyGame game;
    OrthographicCamera camera;
    ScreenViewport viewport;

    // Temp x and y co-ords
    int x, y;

    // For Movement
    int direction_x, direction_y;
    int speed = 3;

    Control control;

    float stateTime;

    public GameScreen(final MyGame game) {
        this.game = game;

        int h = (int) (game.displayH/Math.floor(game.displayH/160));
        int w = (int) (game.displayW/(game.displayH/ (game.displayH/Math.floor(game.displayH/160))));

        camera = new OrthographicCamera(w,h);
        camera.zoom = 1f;

        // Used to capture Keyboard Input
        control = new Control(game.displayW, game.displayH, camera);
        Gdx.input.setInputProcessor(control);

        stateTime = 0f;
    }

    public float getScale() {
        return ((float)Gdx.graphics.getWidth())/((float)WIDTH);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1f, 1f, 1f, 1f);

        // GAME LOGIC
        // Reset the direction values
        direction_x=0;
        direction_y=0;

        if(control.down) direction_y = -1 ;
        if(control.up) direction_y = 1 ;
        if(control.left) direction_x = -1;
        if(control.right) direction_x = 1;

        camera.position.x += direction_x * speed;
        camera.position.y += direction_y * speed;
        camera.update();

        stateTime += Gdx.graphics.getDeltaTime();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        TextureRegion blueSlimeFrame = Assets.kingSlime.getKeyFrame(stateTime, true);

        game.batch.begin();
            game.batch.draw(blueSlimeFrame,100, 100);
            game.batch.draw(Assets.textureBack, 0, 0);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        //music.play
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}