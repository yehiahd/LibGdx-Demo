package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashSet;
import java.util.Iterator;

import callback.ActorEventListener;
import handler.AnimalFactory;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Actor implements ActorEventListener {
    private final int rows;
    private final int cols;
	private AnimalActor selectedActor;
	private Table table;
	private boolean animating;

	public BoardActor(int rows, int cols, Table table) {
        this.rows = rows;
        this.cols = cols;
		this.table = table;
		initialize();
	}

    public void initialize() {
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
	 * @return whether the whole grid contains a match or not.
	 */

	@Override
	public boolean checkMatches() {
		return checkMatches(true);
	}

	public boolean checkMatches(boolean clearAfter) {
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

	@Override
	public void clearAnimation() {
		animating = false;
	}

	@Override
	public void onActorClicked(AnimalActor actor) {
		if (this.selectedActor != null){
			if (actor.canSwap(this.selectedActor)) {
				animating = true;
				actor.swapWith(this.selectedActor, true);
			}
			BoardActor.this.selectedActor = null;
		}
		else
			this.selectedActor = actor;
	}

	private boolean outOfBoundary(int row, int col) {
		return row >= rows || col >= cols || row < 0 || col < 0;
	}

	public boolean isAnimating() {
		return animating;
	}
}
