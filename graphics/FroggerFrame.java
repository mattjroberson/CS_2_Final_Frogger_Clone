package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logic.FroggerLogic;
import logic.Vector;

public class FroggerFrame extends JFrame {
								
	private FroggerLogic logic;
	private Vector<Integer> screenSize;
	private Vector<Double> screenScale;
	private AssetManager assetManager;
	private HighScoreFrame highScoreWindow;
	
	public FroggerFrame(FroggerLogic logic, Vector<Integer> screenSize) {
		this.logic = logic;
		this.screenSize = screenSize;
		
		screenScale = new Vector<Double>(1.0, 1.0);

		assetManager = new AssetManager();
		assetManager.load();
		
		highScoreWindow = new HighScoreFrame(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setTitle("Frogger");
		
		drawMenu();
		
		GamePanel gamePanel = new GamePanel(screenSize, this);
		add(gamePanel);
		
		gamePanel.setFocusable(true);
		gamePanel.grabFocus();
		
		InfoPanel infoPanel = new InfoPanel(this);
		add(infoPanel);
		
		setVisible(true);
		pack();
	}
	
	@Override
	public void repaint() {
		super.repaint();
		if(logic.showHighScores()){
			highScoreWindow.setVisible(true);
			logic.clearShowHighScores();
		}
	}
		
	public FroggerLogic getLogic() { return logic; }
	
	public Vector<Integer> getScreenSize() { return screenSize; }
	
	public Vector<Double> getScreenScale() { return screenScale; } 
	
	public AssetManager getAssetManager() { return assetManager; }
		
	private void drawMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		
		JMenuItem menuRestart = new JMenuItem("Restart");
		
		menuRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getLogic().restart();
			}
		});
			
		menu.add(menuRestart);
		
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
}