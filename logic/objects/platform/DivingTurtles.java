package logic.objects.platform;

import logic.FroggerLogic;
import logic.Vector;

public class DivingTurtles extends Platform{

	public static enum DiveState { SUBMERGED, SURFACING, FLOATING, SINKING };
	private DiveState diveState;
	
	private double diveTime;
	private double deltaTime;
	
	public DivingTurtles(Vector<Double> position, Vector<Double> size, double speed, double diveTime, FroggerLogic logic) {
		super(position, size, speed, PlatformType.DIVER, logic);
		this.diveTime = diveTime;
		diveState = DiveState.FLOATING;
		
		deltaTime = 0;
	}
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		
		this.deltaTime += deltaTime;
		
		if(this.deltaTime >= diveTime) {
			switch(diveState) {
	        	case SUBMERGED:
	        		diveState = DiveState.SURFACING;
	        		break;
	        	case SURFACING:
	        		diveState = DiveState.FLOATING;
	        		setCanRide(true);
	        		break;
	        	case FLOATING:
	        		diveState = DiveState.SINKING;
	        		break;
	        	case SINKING:
	        		diveState = DiveState.SUBMERGED;
	        		setCanRide(false);
	        		if(hasFrog()) {
	        			kickFrog();
	        		}
	        		break;
        	}
						
			this.deltaTime = 0;
		}
	}

	public DiveState getDiveState() { return diveState; }
	
}
