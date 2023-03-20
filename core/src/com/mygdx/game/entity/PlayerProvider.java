package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.mygdx.game.constants.Constants;

/**
 *
 */
public class PlayerProvider extends EntityProvider{

    private Vector2 screenDimentions;
    private boolean isInJump;

    /**
     * @screenDimentions Nikolay
     */

    private Box2D box;

    public PlayerProvider(EntityData data) {
        super(data);

        screenDimentions = new Vector2(Constants.WIDTH_SCREEN_STANDART, Constants.HEIGHT_SCREEN_STANDART);
    }

    public PlayerProvider(Vector2 location, EntityData data, Vector2 screenDimentions) {
        super(location, data);

        this.screenDimentions = screenDimentions;
    }
    /**
     * @param time delta time of screen update
     */
    public void update(double time) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) setMoveDirection(new Vector2(-1, 0));
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setMoveDirection(new Vector2(1, 0));

        if(getLocation().x < 0) setLocationTo(new Vector2(0, getLocation().y));
        if(getLocation().x > screenDimentions.x) setLocationTo(new Vector2(screenDimentions.x - getTexture().getWidth(), getLocation().y));
    }


}
