package com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletProvider;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        //int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Masks.BULLET_BIT | Masks.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT)
                    ((BulletProvider)fixA.getUserData()).damage(((EntityProvider) fixB.getUserData()).getData());
                else
                    ((BulletProvider)fixB.getUserData()).damage(((EntityProvider) fixA.getUserData()).getData());
                break;
        }
    }


    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
