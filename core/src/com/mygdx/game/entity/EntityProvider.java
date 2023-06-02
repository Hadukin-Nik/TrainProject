package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class EntityProvider extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, WALKING, POSTRUNNING }

    protected double stateTimer;

    protected State currentState;
    protected State previousState;

    protected World world;
    protected Body b2body;

    protected EntityData entityData;

    protected boolean setToDestroy;

    public EntityProvider() {
        currentState = State.STANDING;
    }

    public State getState() {
        if(!entityData.isAlive()) {
            return State.DEAD;
        }
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING)){
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }
    public abstract void update(double time);

    public Vector2 getPosition() {
        return b2body.getPosition();
    }

    public EntityData getEntityData() {
        return entityData;
    }
}
