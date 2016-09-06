package com.ya.piratespigger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import actor.BoardActor;
import handler.Assets;
import handler.Drawer;

public class PiratesPigger extends ApplicationAdapter {

	SpriteBatch batch;
	BoardActor board;
//	Stage stage;

	@Override
	public void create () {
		Assets.load();
		batch = new SpriteBatch();

//		stage = new Stage();
		board = new BoardActor(10,10);
		board.initialize();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		batch.begin();
		Drawer.draw(batch, width, height);
		batch.end();
		board.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
