package logic.objects.frog;

import logic.objects.Body;

public interface Frog {
	
	public void move(String dir);
	
	public String getJumpDirection();
	
	public void clearJumpDirection();
	
	public boolean showBonus();
	
	public void clearShowBonus();
	
	public String getStringType();
	
	public Body getBody();
}
