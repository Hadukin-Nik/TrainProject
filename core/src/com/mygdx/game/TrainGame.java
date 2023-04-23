package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.PlayScreen;

public class TrainGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new PlayScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
