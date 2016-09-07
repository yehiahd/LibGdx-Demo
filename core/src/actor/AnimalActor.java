package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalActor extends Image {

	private BoardActor board;
	private int row;
	private int col;
    private int typeID;

    public AnimalActor(BoardActor board, int row, int col, int typeID){
	    this.board = board;
	    this.row=row;
        this.col=col;
        this.typeID = typeID;
	    prepareListener();
    }

	private void prepareListener() {
		InputListener inputListener = new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Gdx.app.log("enter: ", String.format("(%s, %s), %s, actor: %s", row, col, pointer, fromActor));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("touchDown: ", String.format("(%s, %s), %s, button: %s", x, y, pointer, button));
//				if (event.getTarget() instanceof AnimalActor)
//					Gdx.app.log("ashraf", "ashroofa");
//				else
//					Gdx.app.log("yehia", "howaaaa7");
				board.setSelectedAnimal(AnimalActor.this);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("touchUp: ", String.format("(%s, %s) (%s, %s), %s", x, y, row, col, pointer, button));
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				Gdx.app.log("touchDragged: ", String.format("(%s, %s), %s", x, y, typeID));
				board.touchUp(0, 0);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Gdx.app.log("exit: ", String.format("(%s, %s), %s, actor: %s", x, y, pointer, toActor));
			}
		};
		addListener(inputListener);
	}

	public int getTypeID() {
        return typeID;
    }

	@Override
	public void draw(Batch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
		draw(batch);
	}

	public void draw(Batch batch){
		Texture texture = Assets.animalTextures.get(typeID);
        float x = row*texture.getWidth();
		float y = col*texture.getHeight();
		setX(x);
		setY(y);
		batch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
		setTouchable(Touchable.enabled);
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
		setBounds(x, y, texture.getWidth(), texture.getHeight());
	}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AnimalActor))
            return false;
        AnimalActor that = (AnimalActor)o;
        return this.getTypeID() == that.getTypeID();
    }

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
}
