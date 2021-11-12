package logic.objects;

import logic.FroggerLogic;
import logic.RuntimeObject;
import logic.objects.frog.UserFrog;

public class Snake extends RuntimeObject{
	private boolean onMedian;
	
	private double lifeSpan;
	
	public Snake(double speed, PhysicsBody parent, double lifeSpan, FroggerLogic logic) {
		super(logic.scale(2.0, 1.0), speed, parent, Type.SNAKE, logic);

		this.lifeSpan = lifeSpan;
		
		onMedian = logic.getCenterMedian().equals(parent);
		setScreenWrap(!onMedian);
	}
	
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);		
		
		setLocalX(getSpeed()*deltaTime + getLocalX());
		updateBounds();
		if(!onMedian) updateLifeCycle(deltaTime);
	}
	
	@Override
	public void kill() {
		super.kill();
		logic.getSpawner().removeSnake();
	}
	
	public boolean steppedOnHead(UserFrog frog) {
		if(getSpeed() > 0) {
			if(frog.getPosition().x + frog.getSize().x > getPosition().x + (getSize().x * .75)) {
				return true;
			}
		}
		else {
			if(frog.getPosition().x < getPosition().x + (getSize().x * .2)) {
				return true;
			}
		}
		return false;
	}
	
	private void updateBounds() {
		if(getLocalX() < 0) {
			if(!onMedian){
				setLocalX(0);
				flipSpeed();
			}
			else kill();
		}
		else if(getLocalX() + getSize().x > getParent().getSize().x) {
			if(!onMedian) {
				setLocalX(getParent().getSize().x - getSize().x);
				flipSpeed();
			}
			else kill();
		}
	}
	
	public void updateLifeCycle(double deltaTime) {		
		this.deltaTime += deltaTime;
		
		if(this.deltaTime >= lifeSpan) {
			kill();
			this.deltaTime = 0;
		}
	}
}
