package graphics.sprites;

import java.awt.Graphics;

import graphics.FroggerFrame;
import logic.objects.platform.DivingTurtles;

public class DivingTurtleSprite extends PlatformSprite {
		private DivingTurtles turtles;
		
		public DivingTurtleSprite(DivingTurtles turtles, FroggerFrame frame) {
			super(turtles, frame);
			this.turtles = turtles;
		}
		
		@Override
		public void draw(Graphics g) {
			switch(turtles.getDiveState()) {
			case SUBMERGED:
				setImageIndex(2);
        		break;
        	case SURFACING:
				setImageIndex(1);
        		break;
        	case FLOATING:
				setImageIndex(0);
        		break;
        	case SINKING:
				setImageIndex(1);
        		break;
        	}
			
			super.draw(g);
		}
	}
