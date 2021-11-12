package logic.objects.frog;

import java.util.Timer;
import java.util.TimerTask;

import logic.FroggerLogic;
import logic.RuntimeObject;
import logic.Vector;
import logic.objects.PhysicsBody.Type;

public class DeadFrog extends RuntimeObject implements Frog{

	public DeadFrog(Vector<Double> position, Vector<Double> size, long lifeSpan, FroggerLogic logic) {
		super(position, size, Type.FROG, logic);
		
		Timer timer = new Timer();
		timer.schedule(new LifeSpan(), lifeSpan);
		
	}
	
	private class LifeSpan extends TimerTask {
		@Override
		public void run() {
			kill();
		}
	}

	@Override
	public void move(String dir) {}

	@Override
	public String getJumpDirection() { return null;	}

	@Override
	public void clearJumpDirection() {}

	@Override
	public boolean showBonus() { return false; }

	@Override
	public void clearShowBonus() {}

	@Override
	public String getStringType() { return "dead_frog"; }

}
