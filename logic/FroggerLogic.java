package logic;
import java.util.ArrayList;
import java.util.List;

import logic.objects.Enemy.EnemyType;
import logic.objects.Lane;
import logic.objects.PhysicsBody;
import logic.objects.frog.DeadFrog;
import logic.objects.frog.UserFrog;
import logic.objects.landingspot.LandingSpot;
import logic.objects.landingspot.LandingSpotObject.LandingSpotType;
import logic.objects.platform.Platform;
import logic.objects.platform.Platform.PlatformType;

public class FroggerLogic {
		
	private double frameRate;
	private Vector<Integer> grid;
	
	private List<PhysicsBody> collidables;
	private List<Lane> lanes;
	private List<Platform> bigLogs;
	private List<LandingSpot> landingSpots;
	private List<RuntimeObject> runtimeObjects;
	
	private UserFrog frog;
	
	private UpdateHandler<Lane> laneUpdater;
	private UpdateHandler<RuntimeObject> runtimeUpdater;
	private HighScoreManager highScoreManager;
	private Spawner spawner;
	private LevelUpdater levelUpdater;
	
	private double startMedian;
	private Lane centerMedian;
	private double lastLane;
	
	private double waterStart;
	private double waterEnd;
		
	private boolean gameOver;
	private boolean showHighScores;
	private int livesLeft;
	private int homesLeft;
	private int score;
	
	public FroggerLogic(double frameRate) {
		this.frameRate = frameRate;
		grid = new Vector<Integer>(14, 13);
		
		frog = new UserFrog(scale(7.0, 12.0), scale(1.0, 1.0), this);				
		
		runtimeUpdater = new UpdateHandler<RuntimeObject>();
		runtimeObjects = new ArrayList<RuntimeObject>();
		spawner = new Spawner(1.0, this);
			
		levelUpdater = new LevelUpdater(this);
		highScoreManager = new HighScoreManager();
		
		collidables = new ArrayList<PhysicsBody>();
		bigLogs = new ArrayList<Platform>();
		
		laneUpdater = new UpdateHandler<Lane>();
		lanes = new ArrayList<Lane>();

		lanes.add(new Lane(4,  .5, scale(0.0, 1.0), scale(3.0, 1.0),   .1, PlatformType.LOG, this));
		lanes.add(new Lane(5,   .5, scale(0.0, 2.0), scale(2.0, 1.0),  -.2, PlatformType.TURTLE, new int[] {0}, this));
		lanes.add(new Lane(1,  1.0, scale(0.0, 3.0), scale(6.0, 1.0),   .3, PlatformType.LOG, this));
		lanes.add(new Lane(3,  1.0, scale(0.0, 4.0), scale(3.0, 1.0),  .05, PlatformType.LOG, this));
		lanes.add(new Lane(4,  0.33, scale(0.0, 5.0), scale(3.0, 1.0),  -.1, PlatformType.TURTLE, new int[] {0}, this));
		
		centerMedian = new Lane(scale(0.0, 6.0), scale(grid.x, 1.0), this);
		lanes.add(centerMedian);
		
		lanes.add(new Lane(2,  2.0, scale(0.0, 7.0), scale(2.0, 1.0), -.1, EnemyType.TRUCK, this));
		lanes.add(new Lane(1,  0.0, scale(0.0, 8.0), scale(1.2, 1.0),  .2,  EnemyType.WHITE_CAR, this));
		lanes.add(new Lane(3, 2.75, scale(0.0, 9.0), scale(1.2, 1.0), -.1,  EnemyType.PURPLE_CAR, this));
		lanes.add(new Lane(3, 2.75, scale(0.0, 10.0), scale(1.2, 1.0), .1,  EnemyType.DOZER, this));
		lanes.add(new Lane(3,  3.0, scale(0.0, 11.0), scale(1.2, 1.0), -.05,  EnemyType.YELLOW_CAR, this));
		
		
		laneUpdater.addAll(lanes);
					
		landingSpots = new ArrayList<LandingSpot>();
		double spotWidth = 1.5 / grid.x;
		double spotHeight = getLaneHeight();
		
		for(int i = 0; i < 5; i++) {
			double x = (.25 + i * 3.0) / grid.x;			
			landingSpots.add(new LandingSpot(new Vector<Double>(x, 0.0), new Vector<Double>(spotWidth, spotHeight)));
		}
			
		waterStart = 1.0 / grid.y;
		waterEnd = 6.0 / grid.y;
		startMedian = 12.0 / grid.y;
		lastLane = 1.0 / grid.y;
				
		gameOver = false;	
		showHighScores = false;
		livesLeft = 3;
		homesLeft = 5;
		score = 0;
	}
	
	public void restart() {
		levelUpdater.reset();
		rebootGameValues();
	}
	
	public void rebootGameValues() {
		livesLeft = 3;
		score = 0;

		rebootRoundValues();
		gameOver = false;
	}
	
	public void rebootRoundValues() {
		homesLeft = 5;
		frog.reset();
		clearLandingSpots();
	}
	
	public void loseLife(String msg) { 
		livesLeft--;
		
		new DeadFrog(frog.getPosition(), frog.getSize(), 2000, this);
		
		if(livesLeft == 0) {
			gameOver = true;
			showHighScores = true;
		}
		else frog.reset();
	}

	public void foundHome(boolean ateFly) {
		if(frog.capturedBonus()) increaseScore(200);
		if(ateFly) increaseScore(200);
		increaseScore(10 * (int)(30-frog.getStopwatch()));
		
		increaseScore(50);
		
		homesLeft--;
		if(homesLeft == 0) {
			increaseScore(1000);
			startNewLevel();
		}
		
		frog.reset();
	}
	
	private void startNewLevel() {
		levelUpdater.nextLevel();
		rebootRoundValues();
	}
	
	private void clearLandingSpots() {
		for(LandingSpot spot : landingSpots) {
			if(spot.isEmpty() == false && spot.currentType() == LandingSpotType.FROG) {
				spot.setCurrentObject(null);
			}
		}
	}
		
	public boolean gameOver() { return gameOver; }
	
	public void increaseScore(int reward) { score += reward; }
	
	public double getFrameRate() { return frameRate; }
	
	public List<PhysicsBody> getCollidables() { return collidables; }
	
	public List<Lane> getLanes() { return lanes; }
	
	public List<RuntimeObject> getRuntimeObjects() {  return runtimeObjects; }
	
	public List<Platform> getBigLogs() { return bigLogs; }
	
	public List<LandingSpot> getLandingSpots() { return landingSpots; }
	
	public UpdateHandler<RuntimeObject> getRuntimeUpdater() { return runtimeUpdater; }
	
	public UpdateHandler<Lane> getLaneUpdater() { return laneUpdater; }
	
	public HighScoreManager getHighScoreManager() { return highScoreManager; }
	
	public Spawner getSpawner() { return spawner; } 
	
	public UserFrog getFrog() { return frog; }
	
	public int getLivesLeft() { return livesLeft; }
	
	public int getScore() { return score; }
	
	public boolean showHighScores() { return showHighScores; }
	
	public void clearShowHighScores() { showHighScores = false; }
	
	public Vector<Integer> getGrid() { return grid; }
	
	public double getWaterStart() { return waterStart; }
	
	public double getWaterEnd() { return waterEnd; }
	
	public double getStartMedian() { return startMedian; }
	
	public Lane getCenterMedian() { return centerMedian; }
	
	public void setCenterMedian(Lane centerMedian) { this.centerMedian = centerMedian; }
	
	public double getLastLane() { return lastLane; } 
		
	public double getLaneHeight() { return 1.0 / grid.y; }
	
	public double getGridWidth() { return 1.0 / grid.x; }
	
	/** scales the given grid coordinates to position coords */
	public Vector<Double> scale(double x, double y){
		return new Vector<Double>(x/grid.x, y/grid.y);
	}
}
