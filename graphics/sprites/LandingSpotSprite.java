package graphics.sprites;

import java.awt.Graphics;

import graphics.FroggerFrame;
import logic.objects.landingspot.GatorHead;
import logic.objects.landingspot.LandingSpot;
import logic.objects.landingspot.LandingSpotObject.LandingSpotType;

public class LandingSpotSprite extends Sprite {

	LandingSpot landingSpot;
	
	public LandingSpotSprite(LandingSpot landingSpot, FroggerFrame frame) {
		super(landingSpot, "", frame);
		this.landingSpot = landingSpot;
	}
	
	@Override
	public void draw(Graphics g) {
		if(landingSpot.isEmpty() == false) {
			if(landingSpot.currentType() == LandingSpotType.FLY) {
				setImageIndex(0);
				setImageSet("landing_fly");
				super.draw(g);
			}
			else if(landingSpot.currentType() == LandingSpotType.FROG) {
				setImageIndex(0);
				setImageSet("landing_frog");
				super.draw(g);
			}
			else if(landingSpot.currentType() == LandingSpotType.GATOR) {
				setImageSet("landing_gator");
				
				if(((GatorHead) landingSpot.getObject()).isYoung()) {
					setImageIndex(0);
				}
				else {
					setImageIndex(1);
				}
				
				super.draw(g);
			}
		}
	}

}
