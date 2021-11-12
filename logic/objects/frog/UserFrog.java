package logic.objects.frog;

import logic.FroggerLogic;
import logic.Vector;
import logic.objects.PhysicsBody;
import logic.objects.Snake;
import logic.objects.PhysicsBody.Type;
import logic.objects.landingspot.FrogHead;
import logic.objects.landingspot.GatorHead;
import logic.objects.landingspot.LandingSpot;
import logic.objects.landingspot.LandingSpotObject.LandingSpotType;
import logic.objects.platform.Platform;

public class UserFrog extends PhysicsBody implements Frog{
	
	private boolean inWater;
	private boolean capturedBonus;
	private boolean showBonus;
	private String jumpDirection;
	
	private Vector<Double> startingPosition;

	private double currentProgress;
	private double timeLimit;
	
	private long stopwatch;
	private long startTime;
	
	public UserFrog(Vector<Double> position, Vector<Double> size, FroggerLogic logic) {
		super(position, size, 0, Type.FROG, logic);

		startingPosition = position;
		currentProgress = position.y;
		
		timeLimit = 30.0 * 1000;
		stopwatch = 0;
		startTime = System.currentTimeMillis();
		
		setScreenWrap(false);
		capturedBonus = false;
		showBonus = false;
		inWater = false;
	}
	
	public void move(String dir) {
		Vector<Double> move;
		
		if(dir.equals("up")) {
			move = new Vector<Double>(0.0, -getSize().y);
		}
		else if(dir.equals("down")) {
			move = new Vector<Double>(0.0, getSize().y); 
		}
		else if(dir.equals("left")) {
			move = new Vector<Double>(-getSize().x, 0.0); 
		}
		else { // right
			move = new Vector<Double>(getSize().x, 0.0); 
		}
		
		jumpDirection = dir;
			
		Vector<Double> newPos = Vector.addDouble(getPosition(), move);
		setPosition(newPos);
	}
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		updateTimer();
		
		checkCollisions();
		checkBounds();
		checkWater();
		checkProgress();
	}
	
	public void reset() {
		unParent();
		setPosition(startingPosition);
		currentProgress = startingPosition.y;
		capturedBonus = false;
		
		startTime = System.currentTimeMillis();
		stopwatch = 0;
	}
	
	public boolean capturedBonus() { return capturedBonus; }
	
	public String getStringType() { return "frog"; }
	
	public String getJumpDirection() { return jumpDirection; }
	
	public void clearJumpDirection() { jumpDirection = null; }
	
	public boolean showBonus() { return showBonus; }
	
	public void clearShowBonus() { showBonus = false; }
	
	public int getStopwatch() { return 30 - (int)(stopwatch / 1000); }
		
	private void updateTimer() {
		stopwatch = System.currentTimeMillis() - startTime;
		if(stopwatch >= timeLimit) { logic.loseLife("You ran out of time!"); }
	}
	
	private void checkCollisions() {
		for(int i = logic.getCollidables().size() - 1; i >= 0; i--) {
			PhysicsBody body = logic.getCollidables().get(i);
			if(body == null) return;
			
			if(isColliding(body)) {
				if(body.getType() == Type.ENEMY) {
					logic.loseLife("Enemy got you!");
				}
				else if(body.getType() == Type.SNAKE) {
					if(((Snake)(body)).steppedOnHead(this)) logic.loseLife("Snake bite!");
				}
				else if(body.getType() == Type.FROG){
					capturedBonus = true;
					showBonus = true;
					((BonusFrog) body).kill();
				}
				else { //PLATFORM
					((Platform) body).handleCollision(this);
				}
			}
		}
	}
	
	private void checkBounds() {
		boolean safe = (precisionCompare(getPosition().y, "=", logic.getStartMedian()) || 
						precisionCompare(getPosition().y, "=", logic.getCenterMedian().getPosition().y));
		
		if(getPosition().x < 0) {
			if(safe) setPosition(0.0, "x");
			else logic.loseLife("Out of Bounds!");
		}	
		else if(getPosition().x + getSize().x > 1) {
			if(safe) setPosition(1 - getSize().x, "x");
			else logic.loseLife("Out of Bounds!");
		}
		
		if(getPosition().y < logic.getLastLane()) {
			checkWinCase();
		}
		else if(getPosition().y > logic.getStartMedian()) {
			setPosition(logic.getStartMedian(), "y");
		}
	}
	
	private void checkWater() {
		if(getPosition().y >= logic.getWaterStart() && getPosition().y <= logic.getWaterEnd()) {
			if(isChild() == false) {
				if(inWater == true) {
					logic.loseLife("Drowned!");
					return;
				} else inWater = true;
			} else inWater = false;
		}
	}
	
	private void checkProgress() {
		if(getPosition().y < currentProgress) {
			currentProgress = getPosition().y;
			logic.increaseScore(10);
		}
	}
	
	private void checkWinCase() {
		for(LandingSpot spot : logic.getLandingSpots()) {
			if(spot.contains(this)) {
				if(spot.isEmpty()) {
					spot.setCurrentObject(new FrogHead(spot, LandingSpotType.FROG));
					logic.foundHome(false);
					return;
				}
				else {
					if(spot.currentType() == LandingSpotType.FLY) {
						spot.setCurrentObject(new FrogHead(spot, LandingSpotType.FROG));
						logic.foundHome(true);
						return;
					}
					else if(spot.currentType() == LandingSpotType.GATOR) {
						if(((GatorHead)(spot.getObject())).isYoung()){
							spot.setCurrentObject(new FrogHead(spot, LandingSpotType.FROG));
							logic.foundHome(false);
							return;
						}
					}
				}
			}
		}
		logic.loseLife("Ran into the bank or gator!");
	}
}
