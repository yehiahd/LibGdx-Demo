package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
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
	private Vector2 lastTouch;

	public AnimalActor(BoardActor board, int row, int col, int typeID){
	    this.board = board;
	    this.row=row;
        this.col=col;
        this.typeID = typeID;
		lastTouch = new Vector2();

	    setTouchable(Touchable.enabled);
	    prepareListener();
    }

	private void prepareListener() {
		InputListener inputListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("touchDown: ", String.format("(%s, %s)", x, y));
				board.setSelectedAnimal(AnimalActor.this);
				lastTouch.set(x,y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("touchUp: ", String.format("(%s, %s) (%s, %s), %s", x, y, row, col, pointer, button));
				int change = -1; // signal no change

//				if (Math.abs(x - lastTouch.x) <= AnimalActor.this.getWidth()) { // no outside change in x
//					if (y < 0) { //change in y
//						change = 1;
//					}
//				} else if (Math.abs(y - lastTouch.y) <= AnimalActor.this.getHeight()){
//					if (y < 0) { // change in both
//						if (Math.abs(x) > Math.abs(y)) // change in x
//							change = 0;
//						else
//							change = 1; // change in y
//					} else {
//						change = 0; // change in x
//					}
//				}
				if (y < 0 && x < 0)
					if (Math.abs(y) < Math.abs(x))
						change = 0; //change in x
					else
						change = 1; //change in y
				else if (y < 0)
					change = 1;
				else if (x < 0)
					change = 0;
				else { //positive values
					float absX = Math.abs(x - lastTouch.x);
					float absY = Math.abs(y - lastTouch.y);

					if (absX > absY && absX >= AnimalActor.this.getWidth()) //change in x
						change = 0;
					else if (absY > absX && absY >= AnimalActor.this.getHeight())
						change = 1;
				}

				if (change == 1) { // change in y
					if (y > 0) { // up
						board.touchUp(row, col+1);
					} else if (y < 0) { // down
						board.touchUp(row, col-1);
					}
				} else if (change == 0) { // change in x
					if (x > 0) { // right
						board.touchUp(row+1, col);
					} else { // left
						board.touchUp(row-1, col);
					}
				}
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
//				Gdx.app.log("touchDragged: ", String.format("(%s, %s), pointer: %s", x, y, pointer));
			}
		};
		addListener(inputListener);
	}

	public int getTypeID() {
        return typeID;
    }

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		draw(batch);
	}

	public void draw(Batch batch){
		Texture texture = Assets.animalTextures.get(typeID);
        float x = row*texture.getWidth();
		float y = col*texture.getHeight();
		setX(x);
		setY(y);
		batch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
		setBounds(x, y, texture.getWidth(), texture.getHeight());
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AnimalActor))
			return false;
		AnimalActor that = (AnimalActor)o;
		return this.getTypeID() == that.getTypeID();
	}
}
