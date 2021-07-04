package com.spacey.backtospace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.spacey.backtospace.GameClass;

public class LoadingScreen extends ScreenAdapter {

    GameClass game;
    ShapeRenderer shapeRenderer;

    public LoadingScreen(GameClass game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
    }



    @Override
    public void show() {
        Gdx.app.log("INFO", "Started Loading");
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        game.assets.loadAssets(game.safe.playMusic);
    }

    @Override
    public void hide() {
        //if we idk need to clear something after the screen ends
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void render(float delta) {
        if (game.assets.manager.update()) {
            //music play logic
            game.startMusic();
            Gdx.app.log("INFO", "Finished Loading");
            game.gameScreen = new GameScreen(game);
            game.setScreen(new CutSzeneScreen(game));
        }
        // display loading information
        Gdx.gl.glClearColor(.05f, .15f, .35f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(380, 500, 200f, 20);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(380, 500, 200f * game.assets.manager.getProgress(), 20);
        shapeRenderer.end();
        game.batch.begin();
        game.batch.draw(new Texture("screens/loading.png"), 650, 350, 200, 200); //nicht über asset loader laden!!
        game.font.draw(game.batch, "LOADING:     (" + Math.round(game.assets.manager.getProgress() * 100) + "%)...", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Remaining: [" + game.assets.manager.getQueuedAssets() + "x]", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .6f);
        game.batch.end();

    }
}