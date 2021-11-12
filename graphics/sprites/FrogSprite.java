package graphics.sprites;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import graphics.FroggerFrame;
import logic.objects.frog.Frog;

public class FrogSprite extends Sprite {

	private Frog frog;
	private Timer timer;
	
	private String prefix;
	private String type;
		
	public FrogSprite(Frog frog, FroggerFrame frame) {
		super(frog.getBody(), frog.getStringType() + "_up", frame);
		this.frog = frog;
		this.type = frog.getStringType();
		this.prefix = "";
		
		timer = new Timer(60, new AnimationHandler());
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		String suffix = "up";
		
		if(frog.showBonus()) {
			prefix = "bonus_";
			frog.clearShowBonus();
			setImageSet(prefix + "frog_" + suffix);
		}
		
		if(frog.getJumpDirection() != null) {
			suffix = frog.getJumpDirection();
			frog.clearJumpDirection();
			
			setImageSet(prefix + type + "_" + suffix);
			timer.start();
		}
	}
	
	private class AnimationHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean looped = stepImageIndex();
			if(looped == true) timer.stop();
		}		
	}

}
