package com.ya.piratespigger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import actor.BoardActor;
import handler.Assets;
import handler.Drawer;

public class PiratesPigger extends ApplicationAdapter {

	SpriteBatch batch;
	BoardActor boardActor;
	@Override
	public void create () {
		Assets.load();
		batch = new SpriteBatch();
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		batch.begin();
		Drawer.draw(batch, width, height);
		batch.end();
		boardActor = new BoardActor(10,10);
		boardActor.initialize(batch);
	}

	@Override
	public void render () {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
