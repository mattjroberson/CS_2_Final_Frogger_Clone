package logic.objects.frog;

import logic.FroggerLogic;
import logic.RuntimeObject;
import logic.objects.Body;
import logic.objects.PhysicsBody.Type;
import logic.objects.platform.Platform;

public class BonusFrog extends RuntimeObject implements Frog{
	private double speed;
	
	private String jumpDirection;
	private String dir;
	
	public BonusFrog(Platform log, double speed, FroggerLogic logic) {
		super(logic.scale(1.0, 1.0), 0.0, log, Type.FROG, logic);
		this.speed = speed;
		
		dir = "right";
	}
	
	public void move(String dir) {
		double move = dir.equals("left") ? -getSize().x : getSize().x;
		setLocalX(getLocalX() + move);
		jumpDirection = dir;
	}
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		this.deltaTime += deltaTime;
		
		if(this.deltaTime >= speed) {
			move(dir);
			this.deltaTime = 0;
		}
		
		checkBounds();
	}
	
	private void checkBounds() {		
		if(getLocalX() < 0) {
			setLocalX(0);
			dir = dir.equals("left") ? "right" : "left";
		}
		else if(getLocalX() > getParent().getSize().x * .95) {
			setLocalX(getParent().getSize().x - getSize().x);
			dir = dir.equals("left") ? "right" : "left";
		}
	}

	public String getJumpDirection() { return jumpDirection; }

	public void clearJumpDirection() { jumpDirection = null; }

	public boolean showBonus() { return false; }

	public void clearShowBonus() {}
	
	public String getStringType() { return "lady_frog"; }

	public Body getBody() { return super.getBody(); }
}
