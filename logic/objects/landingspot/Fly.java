package logic.objects.landingspot;

public class Fly extends LandingSpotObject{

	private boolean alive;
	
	public Fly(LandingSpot spot, long lifeSpan) {
		super(spot, LandingSpotType.FLY, lifeSpan);
		alive = true;
	}

	@Override
	public void refresh() {
		if(alive) dettach();
		cancelTimer();
	}
}
