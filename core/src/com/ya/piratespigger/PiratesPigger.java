package com.ya.piratespigger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import actor.BoardActor;
import handler.Assets;
import handler.Drawer;

public class PiratesPigger extends ApplicationAdapter {

	private SpriteBatch batch;
	private BoardActor board;
	private Stage stage;
	private Table table;
	private Assets assets;


	@Override
	public void create () {
		assets = Assets.getInstance();
		assets.load();

		batch = new SpriteBatch();
		stage = new Stage();
		table = new Table();

		board = new BoardActor(8 ,8);
		board.initialize(table);
		initTable();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	private void initTable() {
		table.debug();
		table.setFillParent(false);
		table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2, Gdx.graphics.getHeight()/2 - table.getHeight()/2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0.2f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		batch.begin();
		Drawer.draw(batch, width, height);
		batch.end();

		if (!board.isAnimating())
			board.removeMatches();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
		assets.dispose();
	}

}
