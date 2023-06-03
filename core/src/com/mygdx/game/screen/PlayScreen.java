package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TrainGame;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.EntityProvider;

import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.interfaces.IUpdatable;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.tools.B2WorldCreator;
import com.mygdx.game.tools.FPScutter;
import com.mygdx.game.tools.WorldContactListener;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
    //Main game handlerer
    private TrainGame game;

    //Reference to Game, used to set Screens
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private FPScutter fpsCutter;


    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;


    //sprites
    private PlayerProvider player;
    private List<IUpdatable> entitiesToUpdate;

    private boolean worldCreated;

    public PlayScreen(TrainGame trainGame){

        fpsCutter = new FPScutter(30);
        //atlas = new TextureAtlas(".pack");

        //create cam used to follow player through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport( (Constants.WIDTH_SCREEN_STANDART / Constants.PPM), (Constants.HEIGHT_SCREEN_STANDART / Constants.PPM), gamecam);
        this.game = trainGame;



        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("TutorialTemple.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, (1 / Constants.PPM));

        //initially set our gamecam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        entitiesToUpdate = new ArrayList<>();

        world.setContactListener(new WorldContactListener());
    }


    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {


    }


    public void update(float dt){
        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        for(int i = 0; i < entitiesToUpdate.size(); i++) {
            entitiesToUpdate.get(i).update(dt);
        }

        //System.out.println("entities:" + entitiesToUpdate.size());

        hud.update();

        //attach our gamecam to our players.x coordinate
        if(player.getCurrentState() != PlayerProvider.State.DEAD) {
            gamecam.position.x = player.getPosition().x;
        }

        if(player.getCurrentState() != PlayerProvider.State.DEAD) {
            gamecam.position.y = (float) (player.getPosition().y + 0.007 * Constants.PPM);
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }

    public void addToUpdate(IUpdatable newEntity) {
        entitiesToUpdate.add(newEntity);
    }
    @Override
    public void render(float delta) {
        //Creating a world if it is not created
        if(!worldCreated) {
            worldCreated = true;
            creator = new B2WorldCreator(this);
            player = creator.getPlayer();
//            BugProvider bug = new BugProvider(this, new EnemyData(10, 1, 1,1,1), new Vector2(player.getPosition().x + 1, player.getPosition().y), 10);
//            entitiesToUpdate.add(bug);
//            bug.addEnemy(player);

            //create our game HUD for scores/timers/level info
            hud = new Hud(game.batch, (PlayerData) player.getEntityData());
        }
        if(!fpsCutter.isReadyToUpdate(delta)) return;

        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        //Enemies, Items, Piggies... Trains?
        for(IUpdatable i : entitiesToUpdate) {
            i.draw(game.batch);
        }
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }
}
