package com.ya.piratespigger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import actor.BoardActor;
import handler.Assets;
import handler.Drawer;

public class PiratesPigger extends ApplicationAdapter {

	private SpriteBatch batch;
	private BoardActor board;
	private Stage stage;


	@Override
	public void create () {
		Assets.load();

		batch = new SpriteBatch();
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);

		board = new BoardActor(10,10);
		board.initialize(stage);
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
//		stage.clear();
		board.drawBoard();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}

}
