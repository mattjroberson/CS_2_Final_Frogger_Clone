package logic.objects;

import logic.FroggerLogic;
import logic.Vector;
import logic.objects.Enemy.EnemyType;
import logic.objects.platform.Alligator;
import logic.objects.platform.DivingTurtles;
import logic.objects.platform.Platform;
import logic.objects.platform.Platform.PlatformType;

public class Lane extends PhysicsBody{
	
	private EnemyType enemyType;
	private PlatformType curPlatformType, mainPlatformType;
	
	private boolean isMedian;
	
	public Lane(Vector<Double> position, Vector<Double> size, FroggerLogic logic) {
		super(position, size, 0.0, Type.LANE, logic);
		isMedian = true;
	}
	
	public Lane(int count, double gap, Vector<Double> position, Vector<Double> size, double speed, EnemyType type, FroggerLogic logic){
		super(position, Vector.zero, 0, Type.LANE, logic);
		this.enemyType = type;
		isMedian = false;
		
		createLane(count, gap, position, size, speed, new int[]{}, Type.ENEMY);
	}
	
	public Lane(int count, double gap, Vector<Double> position, Vector<Double> size, double speed, PlatformType type, FroggerLogic logic){
		this(count, gap, position, size, speed, type, new int[]{}, logic);
	}
	
	public Lane(int count, double gap, Vector<Double> position, Vector<Double> size, double speed, PlatformType type, int[] specialIndexes, FroggerLogic logic){
		super(position, Vector.zero, 0, Type.LANE, logic);
		this.curPlatformType = this.mainPlatformType = type;
		
		createLane(count, gap, position, size, speed, specialIndexes, Type.PLATFORM);
	}
	
	public boolean isMedian() { return isMedian; }
	
	private void createLane(int count, double gap, Vector<Double> position, Vector<Double> size, double speed, int[] specialIndexes, Type type) {
		double localX = 0;
		
		for(int i = 0; i < count; i++) {
			
			handleSpecialCase(i, specialIndexes);
			
			PhysicsBody child = createChild(position, size, speed, type);
			child.setParent(this, localX);
			
			localX += (size.x + (gap*size.x));
			curPlatformType = mainPlatformType;
		}
		
		localX -= (gap*size.x);
		setSize(new Vector<Double>(localX, 0.0));
		
		if(getSpeed() < 0) {
			setPosition(1-size.x, "x");
		}
				
		logic.getCollidables().addAll(childList);
	}
	
	@Override
	public void update(double deltaTime) {
		for(int i = 0; i < getChildren().size(); i++) {
			PhysicsBody child = getChildren().get(i);
			child.handleScreenWrap();
			child.setLocalX((child.getSpeed()*deltaTime) + child.getLocalX());
			child.update(deltaTime);
		}
	}
	
	private PhysicsBody createChild(Vector<Double> position, Vector<Double> size, double speed, Type type) {
		if(type == Type.ENEMY) {
			return new Enemy(new Vector<Double>(0.0, position.y), size, speed, enemyType, logic);
		}
		else { // Platform
			switch(curPlatformType) {
				case LOG:
					Platform log = new Platform(new Vector<Double>(0.0, position.y), size, speed, curPlatformType, logic);
					if(size.x * logic.getGrid().x > 3)logic.getBigLogs().add(log);
					return log;
				case TURTLE:
					return new Platform(new Vector<Double>(0.0, position.y), size, speed, curPlatformType, logic);
				case DIVER:
					return new DivingTurtles(new Vector<Double>(0.0, position.y), size, speed, 1.0, logic);
				case GATOR:
					return new Alligator(new Vector<Double>(0.0, position.y), size, speed, logic);
				default: return null;
			}
		}
	}
	
	private void handleSpecialCase(int i, int[] indexes) {
		for(int j = 0; j < indexes.length; j++) {
			if(i == indexes[j]) {
				if(mainPlatformType == PlatformType.TURTLE) curPlatformType = PlatformType.DIVER;
				else curPlatformType = PlatformType.GATOR;
			}
		}
	}
}
