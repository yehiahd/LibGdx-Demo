package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import callback.ActorEventListener;
import handler.AnimalFactory;
import handler.Assets;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalActor extends Actor {

	private TextureRegion textureRegion;
    private int typeID;
	private Table table;
	private ActorEventListener listener;

	public AnimalActor(ActorEventListener listener, int typeID){
		AnimalActor.this.listener = listener;
        AnimalActor.this.typeID = typeID;
		textureRegion = new TextureRegion(Assets.getInstance().getTextureAt(typeID));
		setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
		setTouchable(Touchable.enabled);
	    prepareListener();
    }

	private void prepareListener() {
		InputListener inputListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("touchDown: ", AnimalActor.this.toString());
				listener.onActorClicked(AnimalActor.this);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("touchUp: ", String.format("(%s, %s)", x, y));
				/*int change = -1; // signal no change

				if (Math.abs(x - lastTouch.x) <= AnimalActor.AnimalActor.this.getWidth()) { // no outside change in x
					if (y < 0) { //change in y
						change = 1;
					}
				} else if (Math.abs(y - lastTouch.y) <= AnimalActor.AnimalActor.this.getHeight()){
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

					if (absX > absY && absX >= AnimalActor.AnimalActor.this.getWidth()) //change in x
						change = 0;
					else if (absY > absX && absY >= AnimalActor.AnimalActor.this.getHeight())
						change = 1;
				}

				if (change == 1) { // change in y
					if (y > 0) { // up
						board.touchUp(AnimalActor.AnimalActor.this, row-1, col);
					} else if (y < 0) { // down
						board.touchUp(AnimalActor.AnimalActor.this, row+1, col);
					}
				} else if (change == 0) { // change in x
					if (x > 0) { // right
						board.touchUp(AnimalActor.AnimalActor.this, row, col+1);
					} else { // left
						board.touchUp(AnimalActor.AnimalActor.this, row, col-1);
					}
				}*/
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
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(textureRegion, getX(), getY(), getWidth() * getScaleX(),
				getHeight() * getScaleY());
		batch.setColor(color.r, color.g, color.b, 1f);
	}

	public void setTypeID(int typeID) {
		AnimalActor.this.typeID = typeID;
	}

	public void replaceWithRandom() {
		Gdx.app.log("Before replace: ", AnimalActor.this.toString());
		replaceWith(AnimalFactory.getRandomIndex());
		Gdx.app.log("After replace: ", AnimalActor.this.toString());
	}

	public void replaceWith(int typeID) {
		setTypeID(typeID);
		refreshTexture();
	}

	public void refreshTexture() {
		AnimalActor.this.textureRegion = new TextureRegion(Assets.getInstance().getTextureAt(AnimalActor.this.typeID));
	}

	public boolean canSwap(AnimalActor that) {
		if (AnimalActor.this.getX() != that.getX() && AnimalActor.this.getY() != that.getY())
			return false;

		if (Math.abs(AnimalActor.this.getX() - that.getX()) != AnimalActor.this.getWidth() && Math.abs(AnimalActor.this.getY() - that.getY()) != AnimalActor.this.getHeight())
			return false;

		return true;
	}

	public void swapWith(final AnimalActor that, final boolean fromUser) {
		final int tmpType = AnimalActor.this.getTypeID();

		AnimalActor.this.addAction(
				Actions.sequence(
						Actions.alpha(0, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								AnimalActor.this.replaceWith(that.getTypeID());
								that.replaceWith(tmpType);
							}
						}),
						Actions.alpha(1, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (fromUser && !listener.checkMatches()) //if an actual user swap and there are no matches, swap them back.
									that.swapWith(AnimalActor.this, !fromUser);
								else // we're done here.
									listener.clearAnimation();
							}
						})
				)
		);
		that.addAction(
				Actions.sequence(
						Actions.alpha(0, 0.5f),
						Actions.alpha(1, 0.5f)
				)
		);
	}

	public void setTable(Table table) {
		AnimalActor.this.table = table;
	}

	public int getTableIndex() {
		int row = table.getCell(AnimalActor.this).getRow();
		int col = table.getCell(AnimalActor.this).getColumn();
		return table.getRows() * row + col;
	}

	@Override
	public String toString() {
		return String.format(
				"AnimalType: %s\n" +
						"row, col => (%s, %s)\n" +
						"x, y => (%s, %s)\n",
				AnimalTypes.TYPES[typeID],
				table.getCell(AnimalActor.this).getRow(),
				table.getCell(AnimalActor.this).getColumn(),
				getX(),
				getY()
		);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AnimalActor))
			return false;
		AnimalActor that = (AnimalActor)o;
		return AnimalActor.this.getTypeID() == that.getTypeID();
	}
}
