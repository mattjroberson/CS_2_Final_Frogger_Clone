package logic.objects;

import logic.Vector;

public class Body {

	private Vector<Double> position;
	private Vector<Double> size;
	private double speed;
	
	public Body(Vector<Double> position, Vector<Double> size, double speed) {
		this.position = position;
		this.size = size;
		this.speed = speed;
	}
	
	public Vector<Double> getPosition() { return position; }
	
	public void setPosition(Vector<Double> position) { this.position = position; }
	
	public Vector<Double> getSize() { return size; }
	
	public void setSize(Vector<Double> size) { this.size = size; }

	public double getSpeed() { return speed; }
	
	public void flipSpeed() { this.speed *= -1; }
}
