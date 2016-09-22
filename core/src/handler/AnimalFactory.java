package handler;

import actor.NormalAnimalActor;
import actor.BoardActor;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalFactory {
    public static NormalAnimalActor getRandomizedAnimal(BoardActor board){
        int index = getRandomIndex();
        return new NormalAnimalActor(board, index);
    }

	public static int getRandomIndex() {
		return (int)(Math.random() * (AnimalTypes.COUNT-1));
	}

	public static NormalAnimalActor getHiddenAnimal(BoardActor board) {
		return new NormalAnimalActor(board, -1);
	}
}
