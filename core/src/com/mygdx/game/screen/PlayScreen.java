package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
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
    private List<String> levels;

    private Level level;

    private int state;

    private TrainGame game;
    private int nowLevel;

    public PlayScreen(TrainGame trainGame){
        game = trainGame;

        levels = new ArrayList<>();
        levels.add("TutorialTemple.tmx");

        level = new Level(this, levels.get(0), trainGame);
        nowLevel = 0;
    }


    @Override
    public void show() {

    }


    public void addToUpdate(IUpdatable newEntity) {
        level.addToUpdate(newEntity);
    }
    @Override
    public void render(float delta) {

        if(level == null) return;

        state = level.render(delta);

        if(state == 1) {
            level.dispose();
            level = null;
        } else if (state == -1) {
            game.setScreen(new GameOver(game, "pngs/monitors/texts/GAME OVER TEXT.png" ,"pngs/monitors/monitor.png", this));

            level.dispose();
            level = null;
        }
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        level.resize(width,height);
    }

    public void createPreviousLevel() {
        level = new Level(this, levels.get(nowLevel), game);
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

    }

    public TiledMap getMap(){
        return level.getMap();
    }
    public World getWorld(){
        return level.getWorld();
    }
}
