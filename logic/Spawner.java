package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.objects.Snake;
import logic.objects.frog.BonusFrog;
import logic.objects.landingspot.Fly;
import logic.objects.landingspot.GatorHead;
import logic.objects.landingspot.LandingSpotObject.LandingSpotType;
import logic.objects.PhysicsBody;

public class Spawner {

	private Random rand;
	private FroggerLogic logic;
	
	private double deltaTime;
	private double updateTime;
	
	private int snakeCount;
	private int snakeCap;
	
	private double flySpawnChance;
	private double gatorSpawnChance;
	private double frogSpawnChance;

	private double snakeSpawnChance;
	private double medianSpawnChance;
			
	public Spawner(double updateTime, FroggerLogic logic) {
		this.updateTime = updateTime;
		this.logic = logic;
		
		flySpawnChance = .10;
		gatorSpawnChance = .10;
		frogSpawnChance = .30;
		
		snakeSpawnChance = .05;
		medianSpawnChance = .25;
		
		snakeCount = 0;
		snakeCap = 3;
				
		rand = new Random();
	}
	
	public void update(double deltaTime) {
		this.deltaTime += deltaTime;
		
		if(this.deltaTime >= updateTime) {
			updateFly();
			updateGator();
			updateSnake();
			
			this.deltaTime = 0;
		}
	}
	
	public void increaseBadChance() {
		gatorSpawnChance += .05;
		snakeSpawnChance += .05;
	}
	
	private void updateFly() {
		double chance = rand.nextDouble();
				
		List<Integer> openIndexes = findOpenSpots(LandingSpotType.FLY);
		if(openIndexes == null) return;
		
		if(chance <= flySpawnChance) {
			int randIndex = rand.nextInt(openIndexes.size());
			new Fly(logic.getLandingSpots().get(openIndexes.get(randIndex)), 4000);
		}
	}
	
	private void updateGator() {
		double chance = rand.nextDouble();
		
		List<Integer> openIndexes = findOpenSpots(LandingSpotType.GATOR);
		if(openIndexes == null) return;
		
		if(chance <= gatorSpawnChance) {
			int randIndex = rand.nextInt(openIndexes.size());
			new GatorHead(logic.getLandingSpots().get(openIndexes.get(randIndex)), 2000);
		}
	}
	
	private void updateSnake() {
		if(snakeCount >= snakeCap) return;
		
		double chance = rand.nextDouble();
				
		if(chance <= snakeSpawnChance) {
			chance = rand.nextDouble();
			PhysicsBody parent;
			
			if(chance <= medianSpawnChance) {
				parent = logic.getCenterMedian();
			}
			else {
				int randIndex = rand.nextInt(logic.getBigLogs().size());
				parent = logic.getBigLogs().get(randIndex);
			}
			
			double speed = rand.nextDouble() < .5 ? -.1 : .1;
			
			new Snake(speed, parent, 5, logic);
			snakeCount++;
		}
	}
	
	public void updateBonusFrog() {
		double chance = rand.nextDouble();
		
		if(chance <= frogSpawnChance) {
			int randIndex = rand.nextInt(logic.getBigLogs().size());
			new BonusFrog(logic.getBigLogs().get(randIndex), 1, logic);
		}
	}
	
	public void removeSnake() { snakeCount--; }
	
	private List<Integer> findOpenSpots(LandingSpotType type) {
		List<Integer> openIndexes = new ArrayList<Integer>();
		
		for(int i = 0; i < logic.getLandingSpots().size(); i++) {
			if(logic.getLandingSpots().get(i).isEmpty()) {
				openIndexes.add(i);
			}
			else if(logic.getLandingSpots().get(i).currentType() == type) return null;
		}
		
		if(openIndexes.size() == 0) return null;
		else return openIndexes;
	}
}
