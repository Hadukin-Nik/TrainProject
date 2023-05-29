package com.mygdx.game.entity.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;

public class Potion extends EntityProvider {
    private Effect effectOfPotion;

    private boolean setToDestroy;

    public Potion(Effect effectOfPotion, World world, Vector2 position) {
        super();
        this.effectOfPotion = effectOfPotion;

        setBounds(position.x, position.y, 8 / Constants.PPM, 8 / Constants.PPM);

        setToDestroy = false;

        setRegion(new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 16, 16));

        currentState = State.STANDING;
        //define a potion body in a world

        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / Constants.PPM, position.y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body b2body = world.createBody(bdef);
        b2body.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;

        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Constants.PPM);
        fdef.filter.categoryBits = Masks.OBJECT_BIT;
        fdef.filter.maskBits = (short) (Masks.GROUND_BIT | Masks.BRICK_BIT | Masks.PLAYER_BIT);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void collision(PlayerProvider playerProvider) {
        if(setToDestroy || currentState == State.DEAD) return;

        EffectManager effectManager = playerProvider.getEffectManager();

        effectManager.AddEffect(effectOfPotion);

        setToDestroy = true;
    }

    @Override
    public void draw(Batch batch) {
        if(!setToDestroy && currentState != State.DEAD)
        super.draw(batch);
    }

    @Override
    public void update(double time) {
        if(setToDestroy && currentState != State.DEAD) {
            world.destroyBody(b2body);
            currentState = State.DEAD;
        }
    }
}
