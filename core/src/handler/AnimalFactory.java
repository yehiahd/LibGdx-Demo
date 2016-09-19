package handler;

import actor.AnimalActor;
import actor.BoardActor;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalFactory {
    public static AnimalActor getRandomizedAnimal(BoardActor board){
        int index = getRandomIndex();
        return new AnimalActor(board, index);
    }

	public static int getRandomIndex() {
		return (int)(Math.random() * (AnimalTypes.COUNT-1));
	}
}
