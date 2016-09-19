package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashSet;
import java.util.Iterator;

import handler.AnimalFactory;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Actor {
    private final int rows;
    private final int cols;
	private AnimalActor selectedActor;
	private Table table;
	private boolean animating;

	public BoardActor(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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
    }

	/**
	 * the list which stores the actors to be removed by removeMatches().
	 */
	HashSet<Actor> toBeRemoved;

	/**
	 * replaces with animation the matched actors.
	 */
	public void removeMatches(){
		toBeRemoved = new HashSet<Actor>();

		checkMatches(false);

		if (!toBeRemoved.isEmpty()) {
			Gdx.app.log("Removing: ", toBeRemoved.size()+" actors.");
			animating = true;
		}

		for (final Iterator<Actor> iterator = toBeRemoved.iterator(); iterator.hasNext(); ) {
			Actor actor = iterator.next();
			final AnimalActor animalActor = (AnimalActor) actor;
			actor.addAction(Actions.sequence(
					Actions.scaleBy(-0.5f, -0.5f, 1),
					new Action() {
						@Override
						public boolean act(float delta) {
							animalActor.replaceWithRandom();
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

	/**
	 *
	 * @param clearAfter if you want the list to be cleared after you're done checking.
	 * @return whether the whole grid contains a match or not.
	 */
	private boolean checkMatches(boolean clearAfter) {
		boolean result;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				checkMatches(i, j);

		result = toBeRemoved.size() > 0;

		if (clearAfter) {
			toBeRemoved.clear();
		}
		return result;
	}

	/**
	 * @param row
	 * @param col
	 * @return return whether a match exists for this cell or not.
	 */
	private boolean checkMatches(int row, int col) {
		if (outOfBoundary(row, col))
			return false;

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

		return !toBeRemoved.isEmpty();
	}

	private AnimalActor getActorAt(int row, int col) {
		AnimalActor actor = (AnimalActor) table.getCells().get(table.getRows() * row + col).getActor();
		if (row != table.getCell(actor).getRow() || col != table.getCell(actor).getColumn())
			Gdx.app.log("CONFLICT!", actor.toString());
		return actor;
	}

	private void swapActors(final AnimalActor selectedAnimal, final AnimalActor otherAnimal, final boolean fromUser) {
		final int tmpType = selectedAnimal.getTypeID();

		animating = true;
		selectedAnimal.addAction(
				Actions.sequence(
						Actions.alpha(0, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								selectedAnimal.replaceWith(otherAnimal.getTypeID());
								otherAnimal.replaceWith(tmpType);
							}
						}),
						Actions.alpha(1, 0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (fromUser && !checkMatches(true)) //if an actual user swap and there are no matches, swap them back.
									swapActors(otherAnimal, selectedAnimal, !fromUser);
								else // we're done here.
									animating = false;
							}
						})
				)
		);
		otherAnimal.addAction(
				Actions.sequence(
						Actions.alpha(0, 0.5f),
						Actions.alpha(1, 0.5f)
				)
		);
	}

	private boolean outOfBoundary(int row, int col) {
		return row >= rows || col >= cols || row < 0 || col < 0;
	}

	public void setSelectedActor(AnimalActor otherActor) {
		if (this.selectedActor != null){
			if (canSwap(otherActor, this.selectedActor))
				swapActors(otherActor, this.selectedActor, true);
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
