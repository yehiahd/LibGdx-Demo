package actor;

import callback.ActorEventListener;
import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class NormalAnimalActor extends AbstractAnimalActor {

	public NormalAnimalActor(ActorEventListener listener, int typeID){
		super(listener, typeID);
		setTextureRegion(Assets.getInstance().getTextureAt(typeID), true);
    }

	@Override
	public void destroy() {
		listener.actorDestroyed(this);
	}
}
