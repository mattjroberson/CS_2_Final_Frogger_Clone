package graphics.sprites;

import java.awt.Graphics;

import graphics.FroggerFrame;
import logic.objects.platform.Platform;

public class PlatformSprite extends Sprite {

	private int size;
	private boolean isLog;
	
	public PlatformSprite(Platform platform, FroggerFrame frame) {
		super(platform, getType(platform), frame);
		size = (int)(getObject().getSize().x * frame.getLogic().getGrid().x);
		isLog = false;
	}
	
	public PlatformSprite(Platform platform, FroggerFrame frame, boolean isLog) {
		this(platform, frame);
		this.isLog = true;
	}
	
	@Override
	public void draw(Graphics g) {
		
		int logEndIndex = 1;
		
		if(isLog) {
			if(getObject().getSpeed() > 0) {
				setImageIndex(1);
				logEndIndex = size-1;
			} else setImageIndex(0);
		}
		for(int i = 0; i < size; i++) {
			if(isLog && i == logEndIndex) {
				int index = getObject().getSpeed() > 0 ? 0 : 1;
				setImageIndex(index);
			}
			
			double x = getObject().getPosition().x + (i * frame.getLogic().getGridWidth());
			super.draw(g, getScreenX(x), getScreenY(), getScreenWidth(frame.getLogic().getGridWidth()), getScreenHeight());
		}
	}

	private static String getType(Platform platform) {
		switch(platform.getPlatformType()) {
			case LOG: return "log";
			case TURTLE: return "turtle";
			case DIVER: return "turtle";
			default: return null;
		}
	}
	
	private int getScreenX(double x) { return (int)(x * frame.getScreenSize().x); } 
	
	private int getScreenWidth(double w) { return (int)(w * frame.getScreenSize().x); }
}
