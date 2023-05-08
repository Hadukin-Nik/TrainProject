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
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.screen.PlayScreen;

public class BugProvider extends EnemyProvider {
    private double damage;
    public BugProvider(PlayScreen screen, EntityData enemyData, Vector2 position, double damage) {
        super(screen, enemyData, position);
        this.damage = damage;

        stateTimer = 0;

        setBounds(getX(), getY(), 8 / Constants.PPM, 8 / Constants.PPM);

        setToDestroy = false;

        currentState = previousState = State.STANDING;

        setRegion(new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 8, 8));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
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
        entityData.decreaseHP(damage);
    }

    @Override
    public void collisionWithoutDamage() {
        reverseVelocity(true, false);
    }

    @Override
    public void update(double time) {
        stateTimer += time;
        if(setToDestroy && currentState != State.DEAD){
            world.destroyBody(b2body);
            currentState = State.DEAD;
            stateTimer = 0;
        }
        else if(currentState != State.DEAD) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
    }

    public void draw(Batch batch){
        if(currentState != State.DEAD || stateTimer < 1)
            super.draw(batch);
    }

}
