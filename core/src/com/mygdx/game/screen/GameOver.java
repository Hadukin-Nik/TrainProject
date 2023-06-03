package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TrainGame;

public class GameOver implements Screen {

    private TrainGame game;
    private Sprite spriteText;
    private Sprite spriteMonitor;

    private PlayScreen playScreen;

    private float scale;

    public GameOver(TrainGame game, String pathToImage, String pathToMonitor, PlayScreen playScreen){
        this.game = game;
        this.playScreen = playScreen;

        spriteText = new Sprite(new Texture(pathToImage));
        spriteMonitor = new Sprite(new Texture(pathToMonitor));

        Vector2 size = game.getConfig();
        scale = Math.min((size.x - spriteText.getWidth()) / spriteText.getWidth(), (size.x - spriteMonitor.getWidth()) / spriteMonitor.getWidth());
        scale = Math.min(scale, Math.min((size.y - spriteText.getHeight()) / spriteText.getHeight(), (size.y - spriteMonitor.getHeight()/spriteMonitor.getHeight())));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            playScreen.createPreviousLevel();
            game.setScreen(playScreen);
            dispose();

            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(spriteMonitor.getTexture(), (game.getConfig().x - spriteMonitor.getTexture().getWidth() * scale) / 2, (game.getConfig().y - spriteMonitor.getTexture().getHeight() * scale) / 2, spriteMonitor.getTexture().getWidth() * scale, spriteMonitor.getTexture().getHeight() * scale);
        game.batch.draw(spriteText.getTexture(), (game.getConfig().x - spriteText.getTexture().getWidth() * scale) / 2, (game.getConfig().y - spriteText.getTexture().getHeight() * scale) / 2, spriteText.getTexture().getWidth() * scale, spriteText.getTexture().getHeight() * scale);
        game.batch.end();
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
    }
}

