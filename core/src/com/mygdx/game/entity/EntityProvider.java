package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Constants;

public abstract class EntityProvider extends Sprite {
    private Vector2 moveDirection;
    private EntityData entityData;
    protected Vector2 velocity;

    private double stateTimer;


    public EntityProvider(EntityData data) {
        this.entityData = data;
    }

    public EntityProvider(Vector2 location, EntityData data) {
        this.entityData = data;
    }

    public void applyImpulse(Vector2 impulse) {

    }

    public Texture getTexture() {
        return entityData.getTexture();
    }

    public Vector2 getLocation() {
    }

    public void update(double time) {
    }

    public double getStateTimer() {
        return stateTimer;
    }
}
