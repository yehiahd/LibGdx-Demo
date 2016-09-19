package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import handler.AnimalFactory;
import handler.Assets;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalActor extends Actor {

	private TextureRegion textureRegion;
	private BoardActor board;

    private int typeID;
	private Table table;

	public AnimalActor(BoardActor board, int typeID){
	    this.board = board;
        this.typeID = typeID;
		textureRegion = new TextureRegion(Assets.animalTextures.get(typeID));
		setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());

		setTouchable(Touchable.enabled);
	    prepareListener();
    }

	private void prepareListener() {
		InputListener inputListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("touchDown: ", AnimalActor.this.toString());
//				lastTouch.set(x,y);
				board.setSelectedActor(AnimalActor.this);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("touchUp: ", String.format("(%s, %s)", x, y));
				/*int change = -1; // signal no change

				if (Math.abs(x - lastTouch.x) <= AnimalActor.this.getWidth()) { // no outside change in x
					if (y < 0) { //change in y
						change = 1;
					}
				} else if (Math.abs(y - lastTouch.y) <= AnimalActor.this.getHeight()){
					if (y < 0) { // change in both
						if (Math.abs(x) > Math.abs(y)) // change in x
							change = 0;
						else
							change = 1; // change in y
					} else {
						change = 0; // change in x
					}
				}
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
						board.touchUp(AnimalActor.this, row-1, col);
					} else if (y < 0) { // down
						board.touchUp(AnimalActor.this, row+1, col);
					}
				} else if (change == 0) { // change in x
					if (x > 0) { // right
						board.touchUp(AnimalActor.this, row, col+1);
					} else { // left
						board.touchUp(AnimalActor.this, row, col-1);
					}
				}*/
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
//		super.draw(batch, parentAlpha);
		draw(batch);
	}

	public void draw(Batch batch){
		batch.draw(textureRegion,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),
				getRotation());
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

	public void replaceWithRandom() {
		Gdx.app.log("Before replace: ", this.toString());
		setTypeID(AnimalFactory.getRandomIndex());
//		refreshTexture();
//		Gdx.app.log("After replace: ", this.toString());
	}

	public void replaceWith(int typeID) {
		setTypeID(typeID);
		refreshTexture();
	}

	public void refreshTexture() {
		this.textureRegion = new TextureRegion(Assets.getTexture(this.typeID));
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return String.format(
				"AnimalType: %s\n" +
				"row, col => (%s, %s)\n" +
				"x, y => (%s, %s)\n",
				AnimalTypes.TYPES[typeID],
				table.getCell(this).getRow(),
				table.getCell(this).getColumn(),
				getX(),
				getY()
				);
	}
}
