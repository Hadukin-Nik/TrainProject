package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TrainGame;
import com.mygdx.game.constants.Constants;
import org.graalvm.compiler.phases.common.NodeCounterPhase;

public class GameOverScreen implements Screen {

    Viewport viewport;
    Stage stage;
    TrainGame trainGame;
    public  GameOverScreen(TrainGame trainGame)
    {
        this.trainGame = trainGame;
        viewport = new FitViewport(Constants.WIDTH_SCREEN_STANDART,Constants.HEIGHT_SCREEN_STANDART, new OrthographicCamera());
        stage = new Stage(viewport,trainGame.batch);

        Label.LabelStyle gameOverfont = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle restartfont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", gameOverfont);
        Label restartLabel = new Label("Click To Restart", restartfont);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(restartLabel).expandX().padTop(10f);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isTouched())
        {
            trainGame.setScreen(new PlayScreen(trainGame));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
