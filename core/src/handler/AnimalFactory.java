package handler;

import actor.AnimalActor;
import actor.BoardActor;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalFactory {
    public static AnimalActor getRandomizedAnimal(int row, int col, BoardActor board){
        int index = (int)(Math.random() * (AnimalTypes.COUNT-1));
//        String animalType = AnimalTypes.TYPES[index];
        return new AnimalActor(board, row, col, index);
    }
}
