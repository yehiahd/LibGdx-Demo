package model;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalTypes {
    public static final int COUNT = 6;
    public static String TYPES[] = {
        "pig",
        "lemon",
        "panda",
        "carrot",
        "bunny",
        "bear"
    };

	public static String getType(int typeID) {
		if (typeID == -1)
			return "hidden";
		else
			return TYPES[typeID];
	}
}
