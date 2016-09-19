package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashSet;
import java.util.Iterator;

import handler.AnimalFactory;
import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Actor {
    private final int rows;
    private final int cols;
	private final int H;
	private final int W;
	private AnimalActor selectedActor;
	private Table table;
	private boolean animating;

	public BoardActor(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
	    this.W = rows * Assets.panda.getWidth();
	    this.H = cols * Assets.panda.getHeight();
    }

    public void initialize(Table table) {
	    this.table = table;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
	            AnimalActor actor = AnimalFactory.getRandomizedAnimal(this);
	            actor.setTable(table);
	            table.add(actor);
            }
	        table.row();
        }
//	    checkForMatches();
    }

//	public void drawBoard(float x, float y) {
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        ShapeRenderer renderer = new ShapeRenderer();
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        renderer.setColor(new Color(1,1,1,0.3f));
//
////        setOrigin((Gdx.graphics.getWidth() - W)/2.0f, (Gdx.graphics.getHeight() - H) /2.0f);
//        renderer.rect(x, y, W,H);
//
//        renderer.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//
////		for (int i = 0; i < rows; i++) {
////			for (int j = 0; j < cols; j++) {
//////				animalActors[i][j].setOrigin(getOriginX(), getOriginY());
////				stage.addActor(animalActors[i][j]);
////			}
////		}
//    }

	HashSet<Actor> toBeRemoved;

	public void checkForMatches(){
		toBeRemoved = new HashSet<Actor>();
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				checkForMatches(i, j);

//		Gdx.app.log("checking: ", toBeRemoved.size()+"");
//		for (Vector2 vector: toBeRemoved)
//			Gdx.app.log("print: ", vector.x + " " + vector.y);
		if (!toBeRemoved.isEmpty()) {
			Gdx.app.log("Removing: ", toBeRemoved.size()+" actors.");
			animating = true;
		}

		for (final Iterator<Actor> iterator = toBeRemoved.iterator(); iterator.hasNext(); ) {
			Actor actor = iterator.next();
//			actor.addAction(new SequenceAction(Actions.fadeOut(1f)));
			final AnimalActor animalActor = (AnimalActor) actor;
			animalActor.replaceWithRandom();
			actor.addAction(Actions.sequence(
					Actions.scaleBy(-0.5f, -0.5f, 1),
					new Action() {
						@Override
						public boolean act(float delta) {
							animalActor.refreshTexture();
							return true;
						}
					},
					Actions.scaleBy(0.5f, 0.5f, 1),
					new Action() {
						@Override
						public boolean act(float delta) {
							if (!iterator.hasNext())
								animating = false;
							return true;
						}
					}
			));
		}
		if (!toBeRemoved.isEmpty())
			Gdx.app.log("Done!", "");
		toBeRemoved.clear();
	}

	private void checkForMatches(int row, int col) {
		if (outOfBoundary(row, col))
			return;

		AnimalActor current = getActorAt(row, col);
		if (row > 1)
			if (current.equals(getActorAt(row-1, col)) && current.equals(getActorAt(row-2, col))) {
				toBeRemoved.add(getActorAt(row - 1, col));
				toBeRemoved.add(getActorAt(row - 2, col));
			}

		if (row+2 < rows)
			if (current.equals(getActorAt(row+1, col)) && current.equals(getActorAt(row+2, col))) {
				toBeRemoved.add(getActorAt(row + 1, col));
				toBeRemoved.add(getActorAt(row + 2, col));
			}

		if (col > 1)
			if (current.equals(getActorAt(row, col-1)) && current.equals(getActorAt(row, col-2))) {
				toBeRemoved.add(getActorAt(row, col - 1));
				toBeRemoved.add(getActorAt(row, col - 2));
			}

		if (col+2 < cols)
			if (current.equals(getActorAt(row, col+1)) && current.equals(getActorAt(row, col+2))) {
				toBeRemoved.add(getActorAt(row, col + 1));
				toBeRemoved.add(getActorAt(row, col + 2));
			}

		if (row > 1)
			if (current.equals(getActorAt(row-1, col)) && current.equals(getActorAt(row-2, col))) {
				toBeRemoved.add(getActorAt(row - 1, col));
				toBeRemoved.add(getActorAt(row - 2, col));
			}

		if (row+2 < rows)
			if (current.equals(getActorAt(row+1, col)) && current.equals(getActorAt(row+2, col))) {
				toBeRemoved.add(getActorAt(row + 1, col));
				toBeRemoved.add(getActorAt(row + 2, col));
			}

		if (col > 1)
			if (current.equals(getActorAt(row, col-1)) && current.equals(getActorAt(row, col-2))) {
				toBeRemoved.add(getActorAt(row, col - 1));
				toBeRemoved.add(getActorAt(row, col - 2));
			}

		if (col+2 < cols)
			if (current.equals(getActorAt(row, col+1)) && current.equals(getActorAt(row, col+2))) {
				toBeRemoved.add(getActorAt(row, col + 1));
				toBeRemoved.add(getActorAt(row, col + 2));
			}

	}

	private AnimalActor getActorAt(int row, int col) {
		AnimalActor actor = (AnimalActor) table.getCells().get(rows * row + col).getActor();
		if (row != table.getCell(actor).getRow() || col != table.getCell(actor).getColumn())
		Gdx.app.log("CONFLICT!", actor.toString());
		return actor;
	}

//	public void touchUp(AnimalActor selectedAnimal, int row, int col) {
//		if (outOfBoundary(row, col)) return;
//
//		AnimalActor otherAnimal = animalActors[row][col];
//		if (selectedAnimal != null && selectedAnimal != otherAnimal){
////			swapActors(selectedAnimal, otherAnimal);
//		}
//	}

	private void swapActors(final AnimalActor selectedAnimal, final AnimalActor otherAnimal) {
		table.swapActor(otherAnimal, selectedAnimal);
		int tmpType = selectedAnimal.getTypeID();
		selectedAnimal.replaceWith(otherAnimal.getTypeID());
		otherAnimal.replaceWith(tmpType);
//		checkForMatches();
//		float tmpX = selectedAnimal.getX();
//		float tmpY = selectedAnimal.getY();
//		selectedAnimal.addAction(
//				Actions.sequence(
//						Actions.moveTo(otherAnimal.getX(), otherAnimal.getY(), 0.5f),
//						Actions.run(new Runnable() {
//							@Override
//							public void run() {
//								int tmpType = selectedAnimal.getTypeID();
//								selectedAnimal.replaceWith(otherAnimal.getTypeID());
//								otherAnimal.replaceWith(tmpType);
//								table.swapActor(otherAnimal, selectedAnimal);
//							}
//						})
//				));
//		otherAnimal.addAction(Actions.sequence(Actions.moveTo(tmpX, tmpY, 0.5f)));
	}

	private boolean outOfBoundary(int row, int col) {
		return row >= rows || col >= cols || row < 0 || col < 0;
	}

	public void setSelectedActor(AnimalActor otherActor) {
		if (this.selectedActor != null){
			if (canSwap(otherActor, this.selectedActor))
				swapActors(otherActor, this.selectedActor);
			BoardActor.this.selectedActor = null;
		}
		else
			this.selectedActor = otherActor;
	}

	private boolean canSwap(AnimalActor first, AnimalActor second) {
		if (first.getX() != second.getX() && first.getY() != second.getY())
			return false;

		if (Math.abs(first.getX() - second.getX()) != first.getWidth() && Math.abs(first.getY() - second.getY()) != first.getHeight())
			return false;

		return true;
	}

	public boolean isAnimating() {
		return animating;
	}
}
