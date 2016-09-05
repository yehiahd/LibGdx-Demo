package handler;

import actor.AnimalActor;
import model.AnimalTypes;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalFactory {
    public static AnimalActor getRandomizedAnimal(int row, int col){
        int index = (int)(Math.random() * (AnimalTypes.COUNT-1));
        String animalType = AnimalTypes.TYPES[index];
        return new AnimalActor(row, col, animalType, Assets.animalTextures.get(index));
    }
}
