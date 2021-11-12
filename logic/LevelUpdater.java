package logic;

import java.util.List;

import logic.objects.Enemy.EnemyType;
import logic.objects.platform.Platform.PlatformType;
import logic.objects.Lane;

/* Was unable to complete the process of generating new levels in time
 * The code is started, but not usable.
 */

public class LevelUpdater {

	private FroggerLogic logic;
	private int curLevel;
	
	public LevelUpdater(FroggerLogic logic) {
		this.logic = logic;
		curLevel = 1;
	}
	
	public void nextLevel() {
		curLevel++;
		
		logic.getSpawner().increaseBadChance();
		
		if(curLevel == 2) {
			clearLevel();
			
			List<Lane> lanes = logic.getLanes();
			
			lanes.add(new Lane(4,  .5, logic.scale(0.0, 1.0), logic.scale(3.0, 1.0),   .1, PlatformType.LOG, new int[] {1}, logic));
			lanes.add(new Lane(5,   .5, logic.scale(0.0, 2.0), logic.scale(2.0, 1.0),  -.2, PlatformType.TURTLE, new int[] {0}, logic));
			lanes.add(new Lane(1,  1.0, logic.scale(0.0, 3.0), logic.scale(6.0, 1.0),   .3, PlatformType.LOG, logic));
			lanes.add(new Lane(3,  1.0, logic.scale(0.0, 4.0), logic.scale(3.0, 1.0),  .05, PlatformType.LOG, logic));
			lanes.add(new Lane(4,  0.33, logic.scale(0.0, 5.0), logic.scale(3.0, 1.0),  -.1, PlatformType.TURTLE, new int[] {1}, logic));
			
			Lane centerMedian = new Lane(logic.scale(0.0, 6.0), logic.scale(logic.getGrid().x, 1.0), logic);
			lanes.add(centerMedian);
			logic.setCenterMedian(centerMedian);
			
			lanes.add(new Lane(3, 2.0, logic.scale(0.0, 7.0), logic.scale(2.0, 1.0), -.25, EnemyType.TRUCK, logic));
			lanes.add(new Lane(2, 1.0, logic.scale(0.0, 8.0), logic.scale(1.2, 1.0),   .2,  EnemyType.WHITE_CAR, logic));
			lanes.add(new Lane(4, 2.5, logic.scale(0.0, 9.0), logic.scale(1.2, 1.0), -.15,  EnemyType.PURPLE_CAR, logic));
			lanes.add(new Lane(4, 2.5, logic.scale(0.0, 10.0), logic.scale(1.2, 1.0), .15,  EnemyType.DOZER, logic));
			lanes.add(new Lane(4, 2.5, logic.scale(0.0, 11.0), logic.scale(1.2, 1.0), -.1,  EnemyType.YELLOW_CAR, logic));
			
			logic.getLaneUpdater().addAll(lanes);
		}
		
		logic.getSpawner().updateBonusFrog();
	}
	
	public void reset() {
		curLevel = 1;
		clearLevel();
		
		List<Lane> lanes = logic.getLanes();
		
		lanes.add(new Lane(4,  .5, logic.scale(0.0, 1.0), logic.scale(3.0, 1.0),   .1, PlatformType.LOG, logic));
		lanes.add(new Lane(5,   .5, logic.scale(0.0, 2.0), logic.scale(2.0, 1.0),  -.2, PlatformType.TURTLE, new int[] {0}, logic));
		lanes.add(new Lane(1,  1.0, logic.scale(0.0, 3.0), logic.scale(6.0, 1.0),   .3, PlatformType.LOG, logic));
		lanes.add(new Lane(3,  1.0, logic.scale(0.0, 4.0), logic.scale(3.0, 1.0),  .05, PlatformType.LOG, logic));
		lanes.add(new Lane(4,  0.33, logic.scale(0.0, 5.0), logic.scale(3.0, 1.0),  -.1, PlatformType.TURTLE, new int[] {0}, logic));
		
		Lane centerMedian = new Lane(logic.scale(0.0, 6.0), logic.scale(logic.getGrid().x, 1.0), logic);
		lanes.add(centerMedian);
		logic.setCenterMedian(centerMedian);
		
		lanes.add(new Lane(2,  2.0, logic.scale(0.0, 7.0), logic.scale(2.0, 1.0), -.1, EnemyType.TRUCK, logic));
		lanes.add(new Lane(1,  0.0, logic.scale(0.0, 8.0), logic.scale(1.2, 1.0),  .2,  EnemyType.WHITE_CAR, logic));
		lanes.add(new Lane(3, 2.75, logic.scale(0.0, 9.0), logic.scale(1.2, 1.0), -.1,  EnemyType.PURPLE_CAR, logic));
		lanes.add(new Lane(3, 2.75, logic.scale(0.0, 10.0), logic.scale(1.2, 1.0), .1,  EnemyType.DOZER, logic));
		lanes.add(new Lane(3,  3.0, logic.scale(0.0, 11.0), logic.scale(1.2, 1.0), -.05,  EnemyType.YELLOW_CAR, logic));
		
		logic.getLaneUpdater().addAll(lanes);
	}
	
	private void clearLevel() {
		for(int i = logic.getLanes().size()-1; i >= 0; i--) {
			logic.getLaneUpdater().remove(logic.getLanes().get(i));
			logic.getLanes().remove(i);
			logic.getCollidables().clear();
			
			for(RuntimeObject object : logic.getRuntimeObjects()) {
				object.kill();
			}
			
			logic.getRuntimeObjects().clear();
			logic.getBigLogs().clear();
		}
	}
}
