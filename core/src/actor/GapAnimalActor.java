package actor;

import callback.ActorEventListener;

/**
 * Created by yehia on 05/09/16.
 */
public class GapAnimalActor extends AbstractAnimalActor {

	public GapAnimalActor(ActorEventListener listener, int typeID){
		super(listener, typeID);
	}

	@Override
	public void destroy() {
		//will never be destroyed.
	}
}
