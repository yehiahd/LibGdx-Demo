package com.ya.piratespigger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import actor.AnimalActor;
import actor.BoardActor;
import handler.Assets;
import model.AnimalTypes;

public class PiratesPigger extends ApplicationAdapter {

	SpriteBatch batch;
	BoardActor boardActor;
    Stage stage;
	@Override
	public void create () {
		Assets.load();
//		batch = new SpriteBatch();
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        AnimalActor actor = new AnimalActor(0,0, AnimalTypes.TYPES[0],Assets.panda);
        actor.setTouchable(Touchable.enabled);
        stage.addActor(actor);

//		boardActor = new BoardActor(10,10);
//		boardActor.initialize(batch);
	}

	@Override
	public void render () {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
        stage.dispose();
	}

}
