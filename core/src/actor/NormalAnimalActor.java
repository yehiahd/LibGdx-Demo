package actor;

import callback.ActorEventListener;

/**
 * Created by yehia on 05/09/16.
 */
public class NormalAnimalActor extends AbstractAnimalActor {

	public NormalAnimalActor(ActorEventListener listener, int typeID){
		super(listener, typeID);
    }

	@Override
	public void destroy() {
		// replace with gapActor
	}
}
