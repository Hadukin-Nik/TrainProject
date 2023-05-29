package com.mygdx.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletData;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.Scanner;

/**
 *
 */
public class PlayerProvider extends EntityProvider {
    protected boolean runningRight;

    private TextureRegion playerStand;
    private TextureRegion playerJump;

    private Animation playerRun;

    private EffectManager effectManager;

    private PlayerData playerData;
    private float timerToShoot;

    private PlayScreen screen;

    public PlayerProvider(Vector2 location, PlayerData data, PlayScreen screen) {
        super(data);

        effectManager = new EffectManager(data);
        playerData = data;

        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        /*Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to playerRun Animation
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        playerRun = new Animation(0.1f, frames);

        frames.clear();

        //get jump animation frames and add them to playerJump Animation
        playerJump = new TextureRegion(screen.getAtlas().findRegion("player"), 80, 0, 16, 16);

        //create texture region for player standing
         */

        playerStand = new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 16, 16);



        //define mario in Box2d
        definePlayer(location);

        //set initial values for players location, width and height. And initial frame as marioStand.
        setBounds(location.x, location.y, 16 / Constants.PPM, 16 / Constants.PPM);
        setRegion(playerStand);
    }
    /**
     * @param time delta time of screen update
     */
    public void update(double time) {
        timerToShoot = timerToShoot > 0 ? (float) (timerToShoot - time) : 0;
        handleInput(time);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        currentState = getState();
        setRegion(getFrame(time));

        //update sprite with the correct frame
        //setRegion(getFrame(time));
    }



    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void definePlayer(Vector2 location){
        BodyDef bdef = new BodyDef();
        bdef.position.set(location.x / Constants.PPM, location.y / Constants.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Constants.PPM);
        fdef.filter.categoryBits = Masks.PLAYER_BIT;
        fdef.filter.maskBits = Masks.GROUND_BIT | Masks.OBJECT_BIT | Masks.ENEMY_BIT | Masks.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void handleInput(double dt) {
        float multi = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && playerData.getStamina() > 0 ? 3f : 1f);
        //control our player using immediate impulses
        if(getState() != State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                jump();
            if (Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= 2)
                b2body.applyLinearImpulse(new Vector2(0.1f * multi, 0), b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -2)
                b2body.applyLinearImpulse(new Vector2(-0.1f * multi, 0), b2body.getWorldCenter(), true);
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && timerToShoot == 0) {
                timerToShoot = 1;

                float x = runningRight ? 1 : -1;
                float y = 0;
                Vector2 dir = new Vector2(x, y);

                Vector2 loc = new Vector2(b2body.getPosition().x * Constants.PPM, b2body.getPosition().y * Constants.PPM);

                BulletProvider bullet = new BulletProvider(world, loc, new BulletData(new Vector2(8,8), 1.4, 0.4, 1.0, 1.0), dir.nor(), Masks.ENEMY_BIT);
                screen.addToUpdate(bullet);
            }
            if (multi == 3f){
                previousState = currentState;
                currentState = State.RUNNING;
            }
            if (currentState == State.RUNNING && (b2body.getLinearVelocity().x > 1 || b2body.getLinearVelocity().x < - 1)){
                playerData.decreaseStamina(0.2f);
            } else {
                playerData.addStamina(0.1f);
            }
        }
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    //getCurrentFrameDependingOnItsState(jump, standing, etc)
    public TextureRegion getFrame(double dt){
        currentState = getState();

        TextureRegion region = playerStand;

        /*switch(currentState){
            case DEAD:
                break;
            case JUMPING:
                //region = playerJump;
                break;
            case RUNNING:
                //region = (TextureRegion) playerRun.getKeyFrame((float) stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                //region = playerStand;
                break;
        }
        */
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }
}
