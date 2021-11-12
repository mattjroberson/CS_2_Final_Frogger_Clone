package logic;
import graphics.FroggerFrame;

public class GameLoop implements Runnable {

	private FroggerLogic logic;
	private FroggerFrame frame;
		
	public GameLoop(FroggerLogic logic, FroggerFrame frame) {
		this.logic = logic;
		this.frame = frame;
	}
	
	@Override
	public void run() {		
		
		long currentFrameTime = System.nanoTime();
		long lastFrameTime = currentFrameTime;
		
		boolean running = true;
		
		while(running ==true) {			
			lastFrameTime = currentFrameTime;
	        currentFrameTime = System.nanoTime();
						
			if(logic.gameOver() == false) {
		        double timeTaken = (currentFrameTime - lastFrameTime)  * 0.000000001;				
				updateLogic(timeTaken);
				updateView();
			}
			else {
				try{
					Thread.sleep(10);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}	
	}
	
	private void updateLogic(double timeTaken) {
		logic.getFrog().update(timeTaken);
		
		logic.getSpawner().update(timeTaken);
		
		for(int i = logic.getLanes().size() - 1; i >= 0; i--) {
			logic.getLanes().get(i).update(timeTaken);
			if(logic.gameOver()) return;
		}
	}
	
	private void updateView() {
		frame.repaint();
	}
}
