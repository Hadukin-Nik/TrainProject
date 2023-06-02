package com.mygdx.game.entity.enemy.SlimeProvider;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.entity.enemy.EnemyProvider;


public class SlimeProvider extends EnemyProvider{
    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;


    public SlimeProvider(PlayScreen screen, EntityData enemyData, Vector2 position) {
        super(screen, enemyData, position);
        stateTime = 0;
        setBounds(position.x, position.y, 16 / Constants.PPM, 16 / Constants.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
        setRegion(new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 16, 16));
    }
    @Override
    public void update(double time){
        stateTime += time;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
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

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
    public void hitSlime() {
        setToDestroy = true;
    }
    @Override
    public void damage(EntityData entityData) {
        hitSlime();
        entityData.decreaseHP(1);
    }

    @Override
    public void collisionWithoutDamage(EnemyProvider userData) {
    }
}