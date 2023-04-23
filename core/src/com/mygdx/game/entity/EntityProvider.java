package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Constants;

public class EntityProvider extends Sprite {
    private Vector2 moveDirection;
    private Rectangle rectangle;

    private EntityData entityData;
    protected Vector2 velocity;


    public EntityProvider(EntityData data) {
        rectangle = new Rectangle(Constants.PLACE_OF_SPAWN_STANDART.x, Constants.PLACE_OF_SPAWN_STANDART.y, (float)data.getTexture().getWidth(), (float)data.getTexture().getHeight());
        moveDirection = new Vector2(0,0);

        this.entityData = data;
        velocity = new Vector2();
    }

    public EntityProvider(Vector2 location, EntityData data) {
        rectangle = new Rectangle(location.x, location.y, (float)data.getTexture().getWidth(), (float)data.getTexture().getHeight());

        this.entityData = data;
        velocity = new Vector2();
    }
    public void setMoveDirection(Vector2 direction) {
        moveDirection.set(direction);
    }

    public void setLocationTo(Vector2 place) {
        rectangle.setPosition(place);
    }

    public void moveByDirection(double time) {
        float x = (float) (rectangle.x + entityData.getSpeed() * time);
        float y =  (float) (rectangle.y + entityData.getSpeed() * time);

        rectangle = rectangle.setPosition(x, y);
    }

    public void applyImpulse(Vector2 impulse) {
        velocity.add(impulse);
    }

    public Texture getTexture() {
        return entityData.getTexture();
    }

    public Vector2 getLocation() {
        return new Vector2(rectangle.getX(), rectangle.getY());
    }

    public void update(double time) {
        moveByDirection(time);
    }
}
