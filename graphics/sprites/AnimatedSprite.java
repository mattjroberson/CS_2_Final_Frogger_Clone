package graphics.sprites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import graphics.FroggerFrame;
import logic.objects.Body;

public class AnimatedSprite extends Sprite {

	public AnimatedSprite(Body body, String imageType, int timeStep, FroggerFrame frame) {
		super(body, imageType, frame);
		
		Timer timer = new Timer(timeStep, new AnimationHandler());
		timer.start();
	}
	
	private class AnimationHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stepImageIndex();
		}
		
	}

}
