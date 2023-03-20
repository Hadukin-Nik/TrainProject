package com.mygdx.game.entity;

import com.badlogic.gdx.math.Vector2;

public class DropProvider extends EntityProvider{

    public DropProvider(DropData data) {
        super(data);
    }

    public DropProvider(Vector2 location, DropData data) {
        super(location, data);
    }
}
