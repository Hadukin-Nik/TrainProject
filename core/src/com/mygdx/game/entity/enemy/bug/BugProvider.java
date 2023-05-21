package com.mygdx.game.entity.enemy.bug;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletData;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.List;
import java.util.Random;

public class BugProvider extends EnemyProvider {
    private double damage;
    private double timeToAttack;
    private double time;

    private double attackRange;

    private Random random;

    public BugProvider(PlayScreen screen, EntityData enemyData, Vector2 position, double damage, double reloadTime, double attackRange) {
        super(screen, enemyData, position);
        this.damage = damage;

        stateTimer = 0;

        setBounds(position.x, position.y, 8 / Constants.PPM, 8 / Constants.PPM);

        setToDestroy = false;

        currentState = previousState = State.STANDING;

        timeToAttack = reloadTime;

        random = new Random();

        this.attackRange = attackRange;

        setRegion(new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 16, 16));
    }

    @Override
    protected void defineEnemy(Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / Constants.PPM, position.y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / Constants.PPM);
        fdef.filter.categoryBits = Masks.ENEMY_BIT;
        fdef.filter.maskBits = Masks.GROUND_BIT |
                Masks.BRICK_BIT |
                Masks.ENEMY_BIT |
                Masks.OBJECT_BIT |
                Masks.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void damage(EntityData entityData) {
       // entityData.decreaseHP(damage);
    }

    @Override
    public void collisionWithoutDamage() {
        reverseVelocity(true, false);
    }

    @Override
    public void update(double time) {
        if(this.time >= 0) {
            this.time -= time;
        }

        if(this.time <= 0) {
            float dist = playerProvider.getPosition().dst(b2body.getPosition());
            Vector2 pp = playerProvider.getPosition();
            if(playerProvider != null && playerProvider.getEntityData().isAlive() && dist <= attackRange*attackRange) {
                this.time = timeToAttack;
                float x = pp.x - b2body.getPosition().x;
                float y = pp.y - b2body.getPosition().y;
                Vector2 dir = new Vector2(x, y);

                Vector2 loc = new Vector2(b2body.getPosition().x * Constants.PPM, b2body.getPosition().y * Constants.PPM);

                BulletProvider bullet = new BulletProvider(world, loc, new BulletData(new Vector2(8,8), 1.4, 0.4, 1.0, 1.0), dir.nor(), Masks.PLAYER_BIT);
                screen.addToUpdate(bullet);
            }
        }

        stateTimer += time;
        if(setToDestroy && currentState != State.DEAD){
            world.destroyBody(b2body);
            currentState = State.DEAD;
            stateTimer = 0;
        }
        else if(currentState != State.DEAD) {
            //b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
    }

    public void draw(Batch batch){
        if(currentState != State.DEAD || stateTimer < 1)
            super.draw(batch);
    }

}
