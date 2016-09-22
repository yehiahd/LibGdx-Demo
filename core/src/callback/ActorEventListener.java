package callback;

import actor.AbstractAnimalActor;

/**
 * Created by ahmadz on 9/19/16.
 */
public interface ActorEventListener {
	void onActorClicked(AbstractAnimalActor actor);

	void clearAnimation();

	boolean checkMatches();

	void setAnimating();

	boolean isAnimating();

	void actorDestroyed(AbstractAnimalActor actor);
}
