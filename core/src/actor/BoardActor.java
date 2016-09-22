package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashSet;
import java.util.Iterator;

import callback.ActorEventListener;
import handler.AnimalFactory;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Actor implements ActorEventListener {
	private AbstractAnimalActor selectedActor;
	private Table table;
	private boolean animating;

	public BoardActor(int rows, int cols, Table table) {
		this.table = table;
		initialize(rows, cols);
	}

    public void initialize(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
	            AbstractAnimalActor actor = AnimalFactory.getRandomizedAnimal(this);
	            actor.setTable(table);
	            table.add(actor);
            }
	        table.row();
        }
    }

	/**
	 * the list which stores the actors to be removed by removeMatches().
	 */
	HashSet<AbstractAnimalActor> toBeRemoved;

	/**
	 * replaces with animation the matched actors.
	 */
	public void removeMatches(){
		toBeRemoved = new HashSet<AbstractAnimalActor>();

		checkMatches(false);

		if (!toBeRemoved.isEmpty()) {
			Gdx.app.log("Removing: ", toBeRemoved.size()+" actors.");
			animating = true;
		}

		for (final Iterator<AbstractAnimalActor> iterator = toBeRemoved.iterator(); iterator.hasNext(); ) {
			final AbstractAnimalActor actor = iterator.next();
			// TODO: 9/20/16 put explosive animation logic in a runnable action here to maintain the sequential behaviour.
			actor.addAction(Actions.sequence(
					Actions.scaleBy(-0.5f, -0.5f, 1),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							actor.destroy();
						}
					}),
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

	@Override
	public void actorDestroyed(AbstractAnimalActor actor) {
		replaceWithGap(actor);
	}

	private void replaceWithGap(AbstractAnimalActor actor) {
		int tableIndex = actor.getTableIndex();
		table.removeActor(actor);
		table.addActorAt(tableIndex, AnimalFactory.getHiddenAnimal(this));
	}

	/**
	 *
	 * @return whether the whole grid contains a match or not.
	 */

	@Override
	public boolean checkMatches() {
		toBeRemoved = new HashSet<AbstractAnimalActor>();
		return checkMatches(true);
	}

	public boolean checkMatches(boolean clearAfter) {
		boolean result;
		for (int i = 0; i < table.getRows(); i++)
			for (int j = 0; j < table.getColumns(); j++)
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

		AbstractAnimalActor current = getActorAt(row, col);
		if (row > 1)
			if (current.equals(getActorAt(row-1, col)) && current.equals(getActorAt(row-2, col))) {
				toBeRemoved.add(getActorAt(row - 1, col));
				toBeRemoved.add(getActorAt(row - 2, col));
			}

		if (row+2 < table.getRows())
			if (current.equals(getActorAt(row+1, col)) && current.equals(getActorAt(row+2, col))) {
				toBeRemoved.add(getActorAt(row + 1, col));
				toBeRemoved.add(getActorAt(row + 2, col));
			}

		if (col > 1)
			if (current.equals(getActorAt(row, col-1)) && current.equals(getActorAt(row, col-2))) {
				toBeRemoved.add(getActorAt(row, col - 1));
				toBeRemoved.add(getActorAt(row, col - 2));
			}

		if (col+2 < table.getColumns())
			if (current.equals(getActorAt(row, col+1)) && current.equals(getActorAt(row, col+2))) {
				toBeRemoved.add(getActorAt(row, col + 1));
				toBeRemoved.add(getActorAt(row, col + 2));
			}

		if (row > 1)
			if (current.equals(getActorAt(row-1, col)) && current.equals(getActorAt(row-2, col))) {
				toBeRemoved.add(getActorAt(row - 1, col));
				toBeRemoved.add(getActorAt(row - 2, col));
			}

		if (row+2 < table.getRows())
			if (current.equals(getActorAt(row+1, col)) && current.equals(getActorAt(row+2, col))) {
				toBeRemoved.add(getActorAt(row + 1, col));
				toBeRemoved.add(getActorAt(row + 2, col));
			}

		if (col > 1)
			if (current.equals(getActorAt(row, col-1)) && current.equals(getActorAt(row, col-2))) {
				toBeRemoved.add(getActorAt(row, col - 1));
				toBeRemoved.add(getActorAt(row, col - 2));
			}

		if (col+2 < table.getColumns())
			if (current.equals(getActorAt(row, col+1)) && current.equals(getActorAt(row, col+2))) {
				toBeRemoved.add(getActorAt(row, col + 1));
				toBeRemoved.add(getActorAt(row, col + 2));
			}

		return !toBeRemoved.isEmpty();
	}

	private AbstractAnimalActor getActorAt(int row, int col) {
		AbstractAnimalActor actor = (AbstractAnimalActor) table.getCells().get(table.getRows() * row + col).getActor();
		if (row != table.getCell(actor).getRow() || col != table.getCell(actor).getColumn())
			Gdx.app.log("CONFLICT!", actor.toString());
		return actor;
	}

	@Override
	public void clearAnimation() {
		animating = false;
	}

	@Override
	public void setAnimating() {
		animating = true;
	}

	@Override
	public void onActorClicked(AbstractAnimalActor actor) {
		if (this.selectedActor != null){
			if (actor.canSwap(this.selectedActor)) {
//				actor.swapWith(this.selectedActor, true);
				swap(this.selectedActor, actor);
			}
			BoardActor.this.selectedActor = null;
		} else {
			this.selectedActor = actor;
		}
	}

	private void swap(AbstractAnimalActor first, AbstractAnimalActor second) {
		Cell<AbstractAnimalActor> cell = table.getCell(first);
		Cell<AbstractAnimalActor> cell1 = table.getCell(second);

		try {
			NormalAnimalActor clone1 = (NormalAnimalActor) ((NormalAnimalActor)first).clone();
			NormalAnimalActor clone2 = (NormalAnimalActor) ((NormalAnimalActor)second).clone();

			cell.setActor(clone2);
			cell1.setActor(clone1);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private boolean outOfBoundary(int row, int col) {
		return row >= table.getRows() || col >= table.getColumns() || row < 0 || col < 0;
	}

	@Override
	public boolean isAnimating() {
		return animating;
	}
}
