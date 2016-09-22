package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import callback.ActorEventListener;
import handler.Assets;
import model.AnimalTypes;

/**
 * Created by ahmadz on 9/21/16.
 */
public abstract class AbstractAnimalActor extends Actor {
	private TextureRegion textureRegion;
	private int typeID;
	private Table table;

	private ActorEventListener listener;
	public AbstractAnimalActor(ActorEventListener listener, int typeID){
		this.listener = listener;
		this.typeID = typeID;
		refreshTexture();
		setTouchable(Touchable.enabled);
		prepareListener();
	}

	private void prepareListener() {
		InputListener inputListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("touchDown: ", this.toString());
				listener.onActorClicked(AbstractAnimalActor.this);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("touchUp: ", String.format("(%s, %s)", x, y));
				/*int change = -1; // signal no change

				if (Math.abs(x - lastTouch.x) <= AbstractAnimalActor.this.getWidth()) { // no outside change in x
					if (y < 0) { //change in y
						change = 1;
					}
				} else if (Math.abs(y - lastTouch.y) <= AbstractAnimalActor.this.getHeight()){
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

					if (absX > absY && absX >= AbstractAnimalActor.this.getWidth()) //change in x
						change = 0;
					else if (absY > absX && absY >= AbstractAnimalActor.this.getHeight())
						change = 1;
				}

				if (change == 1) { // change in y
					if (y > 0) { // up
						board.touchUp(AbstractAnimalActor.this, row-1, col);
					} else if (y < 0) { // down
						board.touchUp(AbstractAnimalActor.this, row+1, col);
					}
				} else if (change == 0) { // change in x
					if (x > 0) { // right
						board.touchUp(AbstractAnimalActor.this, row, col+1);
					} else { // left
						board.touchUp(AbstractAnimalActor.this, row, col-1);
					}
				}*/
			}
		};
		addListener(inputListener);
	}

	public int getTypeID() {
		return typeID;
	}

	public Table getTable() {
		return table;
	}

	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(getTexture(), getX(), getY(), getWidth() * getScaleX(),
				getHeight() * getScaleY());
		batch.setColor(color.r, color.g, color.b, 1f);
	}
//
//	public void setTypeID(int typeID) {
//		this.typeID = typeID;
//	}

	public abstract void destroy();

//	public void replaceWithRandom() {
//		Gdx.app.log("Before replace: ", this.toString());
//		replaceWith(AnimalFactory.getRandomIndex());
////		this.swapWith(this.getPredecessor(this.getTableIndex()), false);
//		Gdx.app.log("After replace: ", this.toString());
//	}
//	private AbstractAnimalActor getPredecessor() {
//		int index = this.getTableIndex();
//		int cols = table.getColumns();
//		int cells = table.getCells().size;
//		AbstractAnimalActor predecessor = null;
//
//		if (index + cols < cells) {
//			predecessor = (AbstractAnimalActor) table.getCells().get(index + cols).getActor();
//		}
//
//		return predecessor;
//	}
//
//	public void replaceWith(int typeID) {
//		setTypeID(typeID);
//		refreshTexture();
//	}

	public void refreshTexture() {
		Texture texture = Assets.getInstance().getTextureAt(this.typeID);
		if (texture != null) {
			this.textureRegion = new TextureRegion(texture);
			this.setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
		}
	}

	public TextureRegion getTexture(){
		return this.textureRegion;
	}

	public boolean canSwap(AbstractAnimalActor that) {
		if (this.getX() != that.getX() && this.getY() != that.getY())
			return false;

		if (Math.abs(this.getX() - that.getX()) != this.getWidth() && Math.abs(this.getY() - that.getY()) != this.getHeight())
			return false;

		return true;
	}

	public void swapWith(final AbstractAnimalActor that, final boolean fromUser) {
		listener.setAnimating();

		this.addAction(
				Actions.sequence(
						Actions.alpha(0.4f, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
//								AbstractAnimalActor.this.swapTypeWith(that);
							}
						}),
						Actions.alpha(1, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (fromUser && !listener.checkMatches()) //if an actual user swap and there are no matches, swap them back.
									AbstractAnimalActor.this.swapWith(that, !fromUser);
								else {// we're done here.
									listener.clearAnimation();
								}
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

//	private void swapTypeWith(AbstractAnimalActor that) {
//		int tmpType = this.getTypeID();
//		this.replaceWith(that.getTypeID());
//		that.replaceWith(tmpType);
//	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int getTableIndex() {
		int row = table.getCell(this).getRow();
		int col = table.getCell(this).getColumn();
		return table.getRows() * row + col;
	}

	@Override
	public String toString() {
		return String.format(
				"AnimalType: %s\n" +
						"row, col => (%s, %s)\n" +
						"x, y => (%s, %s)\n",
				AnimalTypes.getType(typeID),
				table.getCell(this).getRow(),
				table.getCell(this).getColumn(),
				getX(),
				getY()
		);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractAnimalActor))
			return false;
		AbstractAnimalActor that = (AbstractAnimalActor)o;
		return this.getTypeID() == that.getTypeID() && that.getTypeID() != -1;
	}
}
