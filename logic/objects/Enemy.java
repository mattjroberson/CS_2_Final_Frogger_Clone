package logic.objects;

import logic.FroggerLogic;
import logic.Vector;

public class Enemy extends PhysicsBody{
	
	public static enum EnemyType { YELLOW_CAR, DOZER, PURPLE_CAR, WHITE_CAR, TRUCK };
	private EnemyType type;
	
	public Enemy(Vector<Double> position, Vector<Double> size, double speed, EnemyType type, FroggerLogic logic) {
		super(position, size, speed, Type.ENEMY, logic);
		this.type = type;
	}
	
	public EnemyType getEnemyType() { return type; }
}
