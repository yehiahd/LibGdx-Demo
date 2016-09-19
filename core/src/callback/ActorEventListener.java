package callback;

import actor.AnimalActor;

/**
 * Created by ahmadz on 9/19/16.
 */
public interface ActorEventListener {
	void onActorClicked(AnimalActor actor);

	void clearAnimation();

	boolean checkMatches(boolean clearAfter);
}
