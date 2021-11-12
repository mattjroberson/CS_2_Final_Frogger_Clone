package logic.objects.platform;

import logic.FroggerLogic;
import logic.Vector;

public class Alligator extends Platform{
	
	public Alligator(Vector<Double> position, Vector<Double> size, double speed, FroggerLogic logic) {
		super(position, size, speed, PlatformType.GATOR, logic);
	}
	
	@Override
	public void update(double deltaTime) {
		if(hasFrog()) {
			if(checkMouth() == true) return;
		}
		
		super.update(deltaTime);
	}
	
	private boolean checkMouth() {
		if(getSpeed() > 0) {
			if(getFrog().getLocalX() > getSize().x * .80) {
				logic.loseLife("Gator bit you!");
				return true;
			}
		}
		else {
			if(getFrog().getLocalX() < getSize().x * .20 ) {
				logic.loseLife("Gator bit you!");
				return true;
			}
		}
		
		return false;
	}
}
