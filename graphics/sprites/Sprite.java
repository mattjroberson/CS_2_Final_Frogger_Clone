package graphics.sprites;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import graphics.FroggerFrame;
import logic.objects.Body;

public class Sprite {
	
	protected FroggerFrame frame;
	private Body body;
	private Color color;
	
	private List<Image> imageSet;
	private int imageIndex = 0;
	
	public Sprite(Body body, Color color, FroggerFrame frame) {
		this.body = body;
		this.frame = frame;
		this.color = color;
	}
	
	public Sprite(Body body, String imageType, FroggerFrame frame) {
		this.body = body;
		this.frame = frame;
		imageSet = frame.getAssetManager().getImageSet(imageType);
	}
	
	public void drawRect(Graphics g) {
		g.setColor(color);
		g.fillRect(getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
	}
	
	public void drawRect(Graphics g, double scale) {
		g.setColor(color);
		g.fillRect(getScreenX(), getScreenY(), (int)(getScreenWidth()*scale), (int)(getScreenHeight()*scale));
	}
	
	public void draw(Graphics g) {
		drawImage(imageSet.get(imageIndex), getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight(), g);
	}
	
	public void draw(Graphics g, int x, int y, int width, int height) {
		drawImage(imageSet.get(imageIndex), x, y, width, height, g);
	}
	
	public boolean stepImageIndex() { 
		imageIndex++; 
		
		if(imageSet.size() == imageIndex) {
			imageIndex = 0;
			return true;
		}
		
		return false;
	}
	
	public void setImageSet(String imageType) { 
		imageSet = frame.getAssetManager().getImageSet(imageType); 
	}
	
	public void drawImage(Image image, int x, int y, int width, int height, Graphics g) {
		if(body.getSpeed() < 0) {
			width *= -1;
			x -= width;
		}
		g.drawImage(image, x, y, width, height, null);
	}

	public Body getObject() { return body; }
	
	public void setImageIndex(int index) { imageIndex = index; }
	
	public void setColor(Color color) { this.color = color; }
	
	public int getScreenX() { return (int)(body.getPosition().x * frame.getScreenSize().x); }
	
	public int getScreenY() { return (int)(body.getPosition().y * frame.getScreenSize().y); }
	
	public int getScreenWidth() { return (int)(body.getSize().x * frame.getScreenSize().x * frame.getScreenScale().x); } 
	
	public int getScreenHeight() { return (int)(body.getSize().y * frame.getScreenSize().y * frame.getScreenScale().y); }

	
}
