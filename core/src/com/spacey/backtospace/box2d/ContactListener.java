package com.spacey.backtospace.box2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spacey.backtospace.GameClass;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    GameClass game;

    public ContactListener(GameClass game) {
        super();
        this.game = game;
    }


    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Contact", contact.getFixtureA() + " touched with " + contact.getFixtureB());

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
