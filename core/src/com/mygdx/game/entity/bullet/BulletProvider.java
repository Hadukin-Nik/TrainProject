package com.mygdx.game.entity.bullet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;

public class BulletProvider extends EntityProvider {
    private Rectangle rectangle;

    private BulletData bulletData;
    protected Vector2 move;
    private EntityData eD = new EntityData();
    public BulletProvider(Vector2 location, BulletData data, Vector2 move){
        super(location,data);
        this.move = move;
    }
    public void moveByDirection(double time) {
        float x = (float) (rectangle.x + bulletData.getSpeed() * move.x * time);
        float y =  (float) (rectangle.y + bulletData.getSpeed() * move.y * time);
        rectangle = rectangle.setPosition(x, y);
    }
    public void update(double time){
        if(bulletData.isAlive()) moveByDirection(time);
    }

    public void damage(EntityData entityData) {
        entityData.decreaseHP(bulletData.damage());
        bulletData.kill();
    }
}
