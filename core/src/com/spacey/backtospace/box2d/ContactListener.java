package com.spacey.backtospace.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spacey.backtospace.Entity.Entity;
import com.spacey.backtospace.Entity.Player;
import com.spacey.backtospace.GameClass;
import com.spacey.backtospace.screens.GameScreen;

import java.util.ArrayList;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    GameScreen screen;

    public ContactListener(GameScreen screen) {
        super();
        this.screen = screen;
    }


    @Override
    public void beginContact(Contact contact) {
        screen.touchedFixture = contact.getFixtureB();
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
