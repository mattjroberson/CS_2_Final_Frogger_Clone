package logic.objects;
import java.util.ArrayList;
import java.util.List;

import logic.FroggerLogic;
import logic.Vector;

public abstract class PhysicsBody extends Body{
	
	public static enum Type { FROG, ENEMY, PLATFORM, LANE, SNAKE };
	private Type type;
	
	private PhysicsBody parent;
	protected List<PhysicsBody> childList;
	
	protected FroggerLogic logic;
	
	private boolean screenWrap;
	private double localX;
	
	private double precision;
	
	public PhysicsBody(Vector<Double> position, Vector<Double> size, double speed, Type type, FroggerLogic logic) {
		super(position, size, speed);
		
		this.logic = logic;
		this.type = type;
		
		childList = new ArrayList<PhysicsBody>();
		
		screenWrap = true;
		precision = .00005;
	}
	
	public void update(double deltaTime) {
		if(isChild()) {
			setPosition(localX + parent.getPosition().x, "x");
		}
		else {
			if(screenWrap) handleScreenWrap();
			setPosition(getSpeed()*deltaTime + getPosition().x, "x");
		}
		
		for(int i = childList.size() - 1; i >= 0; i--) {
			childList.get(i).update(deltaTime);
		}
	}
		
	public boolean isColliding(PhysicsBody other) {
		return (precisionCompare(getPosition().x, "<", other.getPosition().x + other.getSize().x) &&
				precisionCompare(getPosition().x + getSize().x, ">", other.getPosition().x) &&
				precisionCompare(getPosition().y, "<", other.getPosition().y + other.getSize().y) &&
				precisionCompare(getPosition().y + getSize().y, ">", other.getPosition().y));
	}
	
	protected boolean precisionCompare(double a, String operator, double b) {
		if(operator.equals("<")) return b - (a + precision) > 0;
		else if(operator.equals(">"))return a - (b + precision) > 0;
		else return Math.abs(a - b) < precision;
	}
	
	public void handleScreenWrap() {		
		if(getPosition().x >= 1) {
			setPosition(-getSize().x, "x");
		}
		else if(getPosition().x + getSize().x <= 0) {
			setPosition(1.0, "x");
		}
	}
	
	public void setPosition(Vector<Double> position) {
		if(isChild()) localX = position.x - parent.getPosition().x;
		super.setPosition(position);
	}
	
	public void setPosition(double val, String type) {
		if(type.equals("x")) setPosition(new Vector<Double>(val, getPosition().y));
		else setPosition(new Vector<Double>(getPosition().x, val));
	}
	
	public void setParent(PhysicsBody parent, double localX) {
		this.parent = parent;
		parent.childList.add(this);
		this.localX = localX;
	}
	
	public void unParent() {
		if(!isChild()) return;
		parent.childList.remove(this);
		parent = null;
	}
	
	public Body getBody() { return this; }
	
	public PhysicsBody getParent() { return parent; }
		
	public List<PhysicsBody> getChildren() { return childList; }
	
	public void setScreenWrap(boolean screenWrap) { this.screenWrap = screenWrap; }
		
	public Type getType() { return type; }
		
	public double getLocalX() { return localX; }
				
	public void setLocalX(double localX) { this.localX = localX; }
	
	public boolean isChild() { return parent != null; }
}
