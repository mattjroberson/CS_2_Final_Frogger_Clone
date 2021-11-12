package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import graphics.sprites.AnimatedSprite;
import graphics.sprites.DivingTurtleSprite;
import graphics.sprites.FrogSprite;
import graphics.sprites.LandingSpotSprite;
import graphics.sprites.PlatformSprite;
import graphics.sprites.Sprite;
import logic.UpdateHandler;
import logic.Vector;
import logic.objects.Body;
import logic.objects.Enemy;
import logic.objects.PhysicsBody;
import logic.objects.frog.Frog;
import logic.objects.landingspot.LandingSpot;
import logic.objects.platform.DivingTurtles;
import logic.objects.platform.Platform;

public class GamePanel extends JPanel{
		
	private FroggerFrame frame;
	private List<Sprite> spriteList;
	private List<Rectangle> landingSpotRects;	
	private Rectangle waterRect;
	private Rectangle startMedianRect;
	
	private Color bankColor;
	private Color waterColor;
	
	private Sprite centerMedianSprite;
	private Sprite frogSprite;
	
	public GamePanel(Vector<Integer> size, FroggerFrame frame) {
		super();
		
		this.frame = frame;
		
		calculateBankRects();
		calculateWaterRect();
		calculateStartMedianRect();
		
		waterColor = new Color(0,0,128);
		bankColor = new Color(0,200,0);
		
		spriteList = new ArrayList<Sprite>();
		
		for(LandingSpot spot : frame.getLogic().getLandingSpots()) {
			spriteList.add(new LandingSpotSprite(spot, frame));
		}

		frogSprite = new FrogSprite(frame.getLogic().getFrog(), frame);
		centerMedianSprite = new Sprite(frame.getLogic().getCenterMedian(), "median", frame);
		
		addKeyListener(new KeyHandler());
		
		setOpaque(true);
		setBackground(Color.black);
		
		setPreferredSize(new Dimension(size.x, size.y));
		revalidate();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawMedians(g);
		drawBank(g);
		drawWater(g);
						
		if(frame.getLogic().getRuntimeUpdater().needsUpdate()) {
			updateSpriteList(frame.getLogic().getRuntimeUpdater());
		}
		
		if(frame.getLogic().getLaneUpdater().needsUpdate()) {
			updateSpriteList(frame.getLogic().getLaneUpdater());
		}
		
		for(Sprite sprite : spriteList) {
			sprite.draw(g);
		}
		
		frogSprite.draw(g);
	}
	
	private void calculateStartMedianRect() {
		int x = 0;
		int y = (int)(frame.getLogic().getStartMedian() * frame.getScreenSize().y);
		int width = frame.getScreenSize().x;
		int height = (int)(frame.getLogic().getLaneHeight() * frame.getScreenSize().y);
		
		startMedianRect = new Rectangle(x,y,width,height);
	}

	private void drawMedians(Graphics g) {		
		g.drawImage(frame.getAssetManager().getImageSet("median").get(0), startMedianRect.x, startMedianRect.y, startMedianRect.width, startMedianRect.height, null);
		centerMedianSprite.draw(g);
	}
	
	private void calculateWaterRect() {
		int x = 0;
		int y = (int)(frame.getLogic().getWaterStart() * frame.getScreenSize().y);
		int width = frame.getScreenSize().x;
		int height = (int)((frame.getLogic().getWaterEnd() - frame.getLogic().getWaterStart()) * frame.getScreenSize().y);
		
		waterRect = new Rectangle(x,y,width, height);
	}
	
	private void drawWater(Graphics g) {		
		g.setColor(waterColor);
		g.fillRect(waterRect.x, waterRect.y, waterRect.width, waterRect.height);
	}
	
	private void calculateBankRects() {
		landingSpotRects = new ArrayList<Rectangle>();
		List<LandingSpot> spots = frame.getLogic().getLandingSpots();
		
		int height = (int)(frame.getLogic().getLaneHeight() * frame.getScreenSize().y);
		int y = 0;
		
		landingSpotRects.add(new Rectangle(0,y, 
				(int)(spots.get(0).getLeft()*frame.getScreenSize().x), height));
		
		for(int i = 0; i < spots.size() - 1; i++) {
			int x = (int)(spots.get(i).getRight() * frame.getScreenSize().x);
			int width = (int)((spots.get(i+1).getLeft() - spots.get(i).getRight()) * frame.getScreenSize().x);
			
			landingSpotRects.add(new Rectangle(x,y,width,height));
		}
		
		landingSpotRects.add(new Rectangle((int)(spots.get(spots.size()-1).getRight()*frame.getScreenSize().x), y,
				(int)((1-spots.get(spots.size()-1).getRight())*frame.getScreenSize().x), height));
		
		landingSpotRects.add(new Rectangle(0,0,frame.getScreenSize().x, (int)(height*.1)));
	}
	
	private void drawBank(Graphics g) {
		g.setColor(bankColor);
		for(Rectangle r : landingSpotRects) g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	private void updateSpriteList(UpdateHandler objects) {		
		emptyQueue(objects.getNewObjects(), "add");
		emptyQueue(objects.getOldObjects(), "purge");
		
		objects.setNeedsUpdate(false);
	}
	
	private void emptyQueue(Queue objects, String action) {
		while(objects.isEmpty() == false) {
			PhysicsBody object = (PhysicsBody) objects.poll();
			if(object == null) break;
			
			if(action.equals("purge")){
				purgeSprite(object);
				for(Body child : object.getChildren()) {
					purgeSprite(child);
				}
			}
			else { //add
				addSprite(object);
				for(PhysicsBody child : object.getChildren()) {
					addSprite(child);
				}
			}	
		}
	}
	
	private void addSprite(PhysicsBody body) {
		if(body.getType() == PhysicsBody.Type.SNAKE) {
			spriteList.add(new AnimatedSprite(body, "snake", 200, frame));
		}
		else if(body.getType() == PhysicsBody.Type.FROG) {
			spriteList.add(new FrogSprite((Frog)body, frame));
		}
		else if(body.getType() == PhysicsBody.Type.ENEMY) {
			switch(((Enemy)(body)).getEnemyType()) {
				case YELLOW_CAR:
					spriteList.add(new Sprite(body, "car_yellow", frame)); break;
				case DOZER:
					spriteList.add(new Sprite(body, "dozer", frame)); break;
				case PURPLE_CAR:
					spriteList.add(new Sprite(body, "car_purple", frame)); break;
				case WHITE_CAR:
					spriteList.add(new Sprite(body, "car_white", frame)); break;
				case TRUCK:
					spriteList.add(new Sprite(body, "truck", frame)); break;
			}
		}
		else if(body.getType() == PhysicsBody.Type.PLATFORM) {
			switch(((Platform)(body)).getPlatformType()){
				case DIVER:
					spriteList.add(new DivingTurtleSprite((DivingTurtles)body, frame)); break;
				case GATOR:
					spriteList.add(new AnimatedSprite(body, "gator", 1000, frame)); break;
				case LOG:
					spriteList.add(new PlatformSprite((Platform)body, frame, true)); break;
				case TURTLE:
					spriteList.add(new PlatformSprite((Platform) body, frame)); break;
			}
		}
	}
	
	private void purgeSprite(Body object) {
		for(int i = spriteList.size() -1; i >= 0; i--) {
			if(spriteList.get(i).getObject().equals(object)) {
				spriteList.remove(i);
				break;
			}
		}
	}
	
	private class KeyHandler extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(frame.getLogic().gameOver() == true) return;
			
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				frame.getLogic().getFrog().move("up");
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				frame.getLogic().getFrog().move("down");
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				frame.getLogic().getFrog().move("left");
			}
			else {//right
				frame.getLogic().getFrog().move("right");
			}
			frame.repaint();
		}
	}
}
