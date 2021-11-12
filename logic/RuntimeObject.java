package logic;

import logic.objects.PhysicsBody;

public abstract class RuntimeObject extends PhysicsBody{

	protected double deltaTime;
	
	public RuntimeObject(Vector<Double> size, double speed, PhysicsBody parent, Type type, FroggerLogic logic) {
		super(parent.getPosition(), size, speed, type, logic);
		
		double localX = speed > 0 ? 0 : parent.getSize().x - size.x;		
		setParent(parent, localX);
		
		logic.getCollidables().add(this);
		deltaTime = 0;
		
		logic.getRuntimeUpdater().add(this);
		logic.getRuntimeObjects().add(this);
	}
	
	public RuntimeObject(Vector<Double> position, Vector<Double> size, Type type, FroggerLogic logic) {
		super(position, size, 0.0, type, logic);

		logic.getRuntimeUpdater().add(this);		
	}
	
	public void kill() {		
		logic.getCollidables().remove(this);
		unParent();
		logic.getRuntimeUpdater().remove(this);
	}

}
