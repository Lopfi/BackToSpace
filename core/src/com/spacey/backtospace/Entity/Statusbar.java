package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.spacey.backtospace.Entity.Entity;

public class Statusbar extends Entity {
    
    public static void create(Batch batch, int width, String text) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        BitmapFont font = new BitmapFont();
        batch.end();
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0, 100, width, 200);
        shapeRenderer.end();
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, text, width/4, 200);
    }
}