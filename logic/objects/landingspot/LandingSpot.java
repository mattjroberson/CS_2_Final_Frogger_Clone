package logic.objects.landingspot;

import logic.Vector;
import logic.objects.Body;
import logic.objects.PhysicsBody;
import logic.objects.landingspot.LandingSpotObject.LandingSpotType;

public class LandingSpot extends Body {

	private double leftSide;
	private double rightSide;
	
	private LandingSpotObject currentObject;
	
	public LandingSpot(Vector<Double> position, Vector<Double> size) {
		super(position, size, 0);
		
		this.leftSide = position.x;
		this.rightSide = position.x + size.x;
	}
	
	public boolean contains(PhysicsBody body) {
		return (leftSide < body.getPosition().x && rightSide > body.getPosition().x + body.getSize().x);
	}
	
	public void setCurrentObject(LandingSpotObject currentObject) { this.currentObject = currentObject; }
	
	public boolean isEmpty() { return currentObject == null; }
	
	public LandingSpotType currentType() { return currentObject.getType(); }
		
	public LandingSpotObject getObject() { return currentObject; }
	
	public double getLeft() { return leftSide; }
	
	public double getRight() { return rightSide; }
	
}
