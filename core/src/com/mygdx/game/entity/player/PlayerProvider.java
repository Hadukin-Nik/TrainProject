package com.mygdx.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.screen.PlayScreen;

/**
 *
 */
public class PlayerProvider extends EntityProvider {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    private double stateTimer;

    private State currentState;
    public State previousState;

    private boolean runningRight;
    private boolean isDead;


    private TextureRegion playerStand;
    private TextureRegion playerJump;


    private Animation playerRun;


    public World world;
    public Body b2body;

    private Box2D box;

    public PlayerProvider(EntityData data) {
        super(data);
    }

    public PlayerProvider(Vector2 location, EntityData data, PlayScreen screen) {
        super(location, data);

        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to playerRun Animation
        /*for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        playerRun = new Animation(0.1f, frames);

        frames.clear();

        //get jump animation frames and add them to playerJump Animation
        playerJump = new TextureRegion(screen.getAtlas().findRegion("player"), 80, 0, 16, 16);

        //create texture region for player standing
         */

        playerStand = new TextureRegion(new Texture(Constants.PATH_TO_STANDART_IMAGE), 16, 16);



        //define mario in Box2d
        definePlayer();

        //set initial values for players location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 16 / Constants.PPM, 16 / Constants.PPM);
        setRegion(playerStand);
    }
    /**
     * @param time delta time of screen update
     */
    public void update(double time) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        currentState = getState();

        //update sprite with the correct frame depending on marios current action
        //setRegion(getFrame(time));
    }

    public State getState() {
        if(isDead) {
            return State.DEAD;
        }
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING)){
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Constants.PPM, 32 / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Constants.PPM);
        fdef.filter.categoryBits = 1;
        fdef.filter.maskBits = Masks.GROUND_BIT | Masks.OBJECT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    //getCurrentFrameDependingOnItsState(jump, standing, etc)
    /*public TextureRegion getFrame(double dt){
        currentState = getState();

        TextureRegion region = null;

        switch(currentState){
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

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
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

    }*/
}
