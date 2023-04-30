package com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.PlayerProvider;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        //int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case BulletData.BULLET_BIT | BulletData.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == BulletData.BULLET_BIT)
                    ((EntityData)fixB.getUserData()).decreaseHP(((BulletData) fixA.getUserData()).damage());
                else
                    ((EntityData)fixA.getUserData()).decreaseHP(((BulletData) fixB.getUserData()).damage());
                break;
        }
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
