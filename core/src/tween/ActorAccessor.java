package tween;

import actor.AbstractAnimalActor;
import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by yehia on 20/09/16.
 */

public class ActorAccessor implements TweenAccessor<AbstractAnimalActor> {

    public static final int MOVE=5;

    @Override
    public int getValues(AbstractAnimalActor target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case MOVE:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(AbstractAnimalActor target, int tweenType, float[] newValues) {
        switch (tweenType){
            case MOVE:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            default:
                assert false;
        }
    }
}
