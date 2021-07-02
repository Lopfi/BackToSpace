package com.spacey.backtospace.box2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spacey.backtospace.screens.GameScreen;

// event listener for contacts between two box2d bodies
public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    GameScreen screen;

    public ContactListener(GameScreen screen) {
        super();
        this.screen = screen;
    }


    @Override
    public void beginContact(Contact contact) {
        if (screen.player.getFixture() == contact.getFixtureA()) screen.touchedFixture = contact.getFixtureB();
        if (screen.player.getFixture() == contact.getFixtureB()) screen.touchedFixture = contact.getFixtureA();
    }

    @Override
    public void endContact(Contact contact) {
        screen.touchedFixture = null; // reset the touched fixture
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
