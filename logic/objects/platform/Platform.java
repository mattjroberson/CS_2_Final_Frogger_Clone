package logic.objects.platform;

import logic.FroggerLogic;
import logic.Vector;
import logic.objects.PhysicsBody;
import logic.objects.PhysicsBody.Type;
import logic.objects.frog.UserFrog;

public class Platform extends PhysicsBody{

	public static enum PlatformType { LOG , TURTLE, GATOR, DIVER };
	private PlatformType type;
	
	private UserFrog frog;
	
	private double overhang;
	private boolean canRide;
	private boolean kickFrog;
	
	public Platform(Vector<Double> position, Vector<Double> size, double speed, PlatformType type, FroggerLogic logic) {
		super(position, size, speed, PhysicsBody.Type.PLATFORM, logic);
		this.type = type;
		
		canRide = true;
		overhang = .5;
	}
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		
		if(hasFrog() == true) {
			if(fitsOnLog(frog) == false || precisionCompare(getPosition().y, "=", frog.getPosition().y) == false || kickFrog == true) {
				dettachFrog();
				kickFrog = false;
			}
		}
	}
	
	public synchronized void handleCollision(UserFrog frog) {
		if(frog.isChild() == false && canRide == true) {
			if(fitsOnLog(frog)) {
				attachFrog(frog);
			}
		}
	}
	
	private boolean fitsOnLog(PhysicsBody body) {
		return (body.getPosition().x > getPosition().x - (body.getSize().x * overhang) &&
				   body.getPosition().x < getPosition().x + getSize().x + (body.getSize().x * overhang));
	}
	
	private void attachFrog(UserFrog frog) {
		frog.setParent(this, frog.getPosition().x - getPosition().x);
		childList.add(frog);
		this.frog = frog;
	}
	
	private void dettachFrog() {
		frog.unParent();
		frog = null;

		childList.remove(frog);
	}
	
	public void kickFrog() { kickFrog = true; }
	
	public boolean canRide() { return canRide; }
	
	public void setCanRide(boolean canRide) { this.canRide = canRide; }
	
	public boolean hasFrog() { return frog != null; }
	
	public UserFrog getFrog() { return frog; } 
		
	public PlatformType getPlatformType() { return type; }
}
