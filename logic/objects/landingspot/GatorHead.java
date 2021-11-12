package logic.objects.landingspot;

public class GatorHead extends LandingSpotObject{

	private boolean young;
	
	public GatorHead(LandingSpot spot, long lifeSpan) {
		super(spot, LandingSpotType.GATOR, lifeSpan);
		young = true;	
	}

	@Override
	public void refresh() {
    	if(young) {
    		young = false;
    	}
    	else {
    		dettach();
    		cancelTimer();
    	}
    }
	
	public boolean isYoung() { return young; }

}
