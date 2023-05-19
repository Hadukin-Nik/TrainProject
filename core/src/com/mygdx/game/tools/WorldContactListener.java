package com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        //int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Masks.BULLET_BIT | Masks.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT) {
                    ((BulletProvider)fixA.getUserData()).damage(((PlayerProvider) fixB.getUserData()).getEntityData());
                    System.out.println(((PlayerProvider) fixB.getUserData()).getEntityData().getHp());
                }
                else {
                    ((BulletProvider)fixB.getUserData()).damage(((PlayerProvider) fixA.getUserData()).getEntityData());
                    System.out.println(((PlayerProvider) fixA.getUserData()).getEntityData().getHp());
                }
                break;
            case Masks.ENEMY_BIT | Masks.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.ENEMY_BIT) {
                    ((EnemyProvider)fixA.getUserData()).damage(((PlayerProvider) fixB.getUserData()).getEntityData());
                }
                else {
                    ((EnemyProvider)fixB.getUserData()).damage(((PlayerProvider) fixA.getUserData()).getEntityData());
                }
                break;
            case Masks.ENEMY_BIT | Masks.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT) {
                    ((BulletProvider)fixA.getUserData()).damage(((EnemyProvider) fixB.getUserData()).getEntityData());
                }
                else {
                    ((BulletProvider)fixB.getUserData()).damage(((EnemyProvider) fixA.getUserData()).getEntityData());
                }
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
