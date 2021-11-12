package logic;

import graphics.FroggerFrame;

public class Frogger {
	
	private static final double FRAME_RATE = 60;
	
	public static void main(String[] args) {
		FroggerLogic logic = new FroggerLogic(1.0/FRAME_RATE);
		FroggerFrame frame = new FroggerFrame(logic, new Vector<Integer>(560,520));
		
		Thread gameLoop = new Thread(new GameLoop(logic, frame));
		gameLoop.start();
	}
}
