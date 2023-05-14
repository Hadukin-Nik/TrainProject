package com.mygdx.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class EnemyProvider extends EntityProvider {
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;
    protected Vector2 velocity;

    protected EntityData enemyData;

    protected List<EntityProvider> enemies;
    protected PlayerProvider playerProvider;


    public EnemyProvider(PlayScreen screen, EntityData enemyData, Vector2 position){
        super(enemyData);

        this.world = screen.getWorld();
        this.enemyData = enemyData;
        this.screen = screen;

        setPosition(position.x, position.y);

        defineEnemy(position);

        velocity = new Vector2(-0.1f, 0);
        b2body.setActive(true);
    }

    public void setEnemies(List<EntityProvider> enemies) {
        this.enemies = enemies;
        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i) instanceof PlayerProvider) {
                playerProvider = (PlayerProvider) enemies.get(i);
            }
        }
    }

    public void addEnemy(EntityProvider enemyProvider) {
        if(enemies == null) enemies = new ArrayList<>();
        enemies.add(enemyProvider);
        if(playerProvider != null) return;
        if(enemyProvider instanceof PlayerProvider) {
            playerProvider = (PlayerProvider) enemyProvider;
        }
    }

    protected abstract void defineEnemy(Vector2 location);
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public abstract void damage(EntityData entityData);

    public abstract void collisionWithoutDamage();
}
