package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Animations;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.box2d.Box2DHelper;
import com.spacey.backtospace.box2d.Box2DWorld;

public class Player extends Entity {

    private final int speed;
    public Inventory inventory;
    private boolean flipped;

    public Player(Vector3 pos, GameClass game) {
        super();
        texture = game.assets.manager.get("player/Spaceman_walk.png", Texture.class);
        animation = Animations.createAnimation(texture, 2, 1, 0.5f);
        inventory = new Inventory(3, game.assets.manager);
        height = texture.getHeight();
        width = texture.getWidth()/2f;
        speed = 60;
        body = Box2DHelper.createBody(game.box2d.world, width, height, pos, BodyDef.BodyType.DynamicBody);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        super.drawAnimation(batch, stateTime, flipped);
        inventory.draw(batch);
    }

    public void update(Control control) {
        int dirX=0;
        int dirY=0;

        if(control.LMB) {
            int x = Math.round(control.mouse_click_pos.x);
            int y = Math.round(control.mouse_click_pos.y);
            int midX = Gdx.graphics.getWidth() / 2;
            int midY = Gdx.graphics.getHeight() / 2;

            if (x < midX - 100) dirX = -1;
            if (x > midX +100) dirX = 1;
            if (y > midY + 100)  dirY = 1;
            if (y < midY -50) dirY = -1;

        }
        else{
            if (control.down) dirY = -1;
            if (control.up) dirY = 1;
            if (control.left) dirX = -1;
            if (control.right) dirX = 1;
        }

        flipped = dirX < 0;

        body.setLinearVelocity(dirX * speed, dirY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;
        inventory.pos.x = pos.x - 10;
        inventory.pos.y = pos.y - 88;
    }

    public void dispose() {
        texture.dispose();
    }
}