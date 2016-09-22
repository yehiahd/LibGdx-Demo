package actor;

import callback.ActorEventListener;
import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class GapAnimalActor extends AbstractAnimalActor {

	public GapAnimalActor(ActorEventListener listener){
		super(listener, -1);
		setTextureRegion(Assets.getInstance().getTextureAt(0), false);
	}

	@Override
	public void destroy() {
		//will never be destroyed.
	}
}
