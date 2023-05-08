package com.mygdx.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.screen.PlayScreen;

public abstract class EnemyProvider extends EntityProvider {
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;
    protected Vector2 velocity;

    protected EntityData enemyData;

    public EnemyProvider(PlayScreen screen, EntityData enemyData, Vector2 position){
        super(enemyData);

        this.world = screen.getWorld();
        this.enemyData = enemyData;
        this.screen = screen;

        setPosition(position.x, position.y);

        defineEnemy();

        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public abstract void damage(EntityData entityData);

    public abstract void collisionWithoutDamage();
}
