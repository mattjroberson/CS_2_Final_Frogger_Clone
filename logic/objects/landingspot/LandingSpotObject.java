package logic.objects.landingspot;

import java.util.Timer;
import java.util.TimerTask;

import logic.Vector;

public abstract class LandingSpotObject {
	public static enum LandingSpotType { FLY, GATOR, FROG};
	private LandingSpotType type;
	
	private LandingSpot spot;
	
	private Vector<Double> position;
	private Vector<Double> size;
	
	private Timer timer;
	
	public LandingSpotObject(LandingSpot spot, LandingSpotType type) {
		this.spot = spot;
		this.type = type;
		spot.setCurrentObject(this);
	}
	
	public LandingSpotObject(LandingSpot spot, LandingSpotType type, long updateTime) {
		this(spot, type);
		timer = new Timer();
		timer.schedule(new LifeCycle(), updateTime, updateTime);
	}
	
	public void dettach() {
		spot.setCurrentObject(null);
	}
	
	private class LifeCycle extends TimerTask {
		@Override
		public void run() { refresh(); }
	}
	
	public abstract void refresh();
	
	public LandingSpotType getType() { return type; }
	
	public Vector<Double> getPosition() { return position; }
	
	public Vector<Double> getSize() { return size; }
	
	public void cancelTimer() {
		timer.cancel();
	}
}
