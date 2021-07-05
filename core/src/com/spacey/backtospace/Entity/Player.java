package com.spacey.backtospace.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.spacey.backtospace.Entity.UI.Inventory;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.Helper.Animations;
import com.spacey.backtospace.Helper.Control;
import com.spacey.backtospace.Helper.Enums;
import com.spacey.backtospace.box2d.Box2DHelper;

// the player with a controller to handle movement inputs
public class Player extends Entity {

    private int speed;
    public Inventory inventory;
    private boolean flipped;
    GameClass game;
    public Player(Vector3 pos, GameClass game) {
        super(game.assets.manager.get("player/spaceman_walk" + game.safe.currentSkin + ".png", Texture.class));
        this.game = game;
        this.pos = pos;
        type = Enums.ENTITYTYPE.PLAYER;
        animation = Animations.createAnimation(texture, 2, 1, 0.5f);
        inventory = new Inventory(3, game);
        width = width/2f;
        speed = 60;
        body = Box2DHelper.createBody(game.box2d.world, width, height + 4, pos, BodyDef.BodyType.DynamicBody, false);
    }

    public Player(Vector3 pos, GameClass game, Texture texture) {
        super(texture);
        this.game = game;
        this.pos = pos;
        type = Enums.ENTITYTYPE.PLAYER;
        animation = Animations.createAnimation(texture, 2, 1, 0.5f);
        width = width/2f;
    }

    public void updateSkin() {
        texture = initTexture(game.assets.manager.get("player/spaceman_walk" + game.safe.currentSkin + ".png", Texture.class));
        width = width/2f;
        animation = Animations.createAnimation(texture, 2, 1, 0.5f);
    }

    public void drawAnimation(SpriteBatch batch, float stateTime) {
        super.drawAnimation(batch, stateTime, flipped);
    }

    public void createBox(Vector3 pos){
        game.box2d.world.destroyBody(body);
        body = Box2DHelper.createBody(game.box2d.world, width, height + 4, pos, BodyDef.BodyType.DynamicBody, false);
    }
    public void update(Control control) {
        int dirX = 0;
        int dirY = 0;

        if(control.LMB) {
            int x = Math.round(control.mouse_click_pos.x);
            int y = Math.round(control.mouse_click_pos.y);
            int midX = Gdx.graphics.getWidth() / 2;
            int midY = Gdx.graphics.getHeight() / 2;

            if (x < midX - 100) dirX = -1;
            if (x > midX + 100) dirX = 1;
            if (y > midY + 100)  dirY = 1;
            if (y < midY - 100) dirY = -1;

        } else{
            if (control.down) dirY = -1;
            if (control.up) dirY = 1;
            if (control.left) dirX = -1;
            if (control.right) dirX = 1;
        }

        if (dirX < 0) flipped = true;
        if (dirX > 0) flipped = false;

        body.setLinearVelocity(dirX * speed, dirY * speed);
        //body.applyForceToCenter(new Vector2(dirX * speed, dirY * speed), false);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - (height-4)/2;
    }
}