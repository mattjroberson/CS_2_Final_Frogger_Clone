package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InfoPanel extends JPanel{

	private FroggerFrame frame;
	private JLabel livesLeft;
	private JLabel curScore;
	private JLabel middle; 
	
	
	public InfoPanel(FroggerFrame frame) {
		this.frame = frame;
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
			
		Font font = new Font("ARIAL", Font.BOLD, 22);
		
		livesLeft = new JLabel(" ");
		livesLeft.setFont(font);
		livesLeft.setForeground(Color.red);
		
		middle = new JLabel(" ");
		middle.setHorizontalAlignment(JLabel.CENTER);
		middle.setFont(font);
		middle.setForeground(Color.white);
		
		middle.setFont(font);
		
		curScore = new JLabel(" ");
		curScore.setFont(font);
		curScore.setForeground(Color.yellow);
		
		add(livesLeft , BorderLayout.LINE_START);
		add(middle, BorderLayout.CENTER);
		add(curScore, BorderLayout.LINE_END);
		
		setOpaque(true);
		setBackground(Color.black);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		livesLeft.setText("Lives: " + String.valueOf(frame.getLogic().getLivesLeft()));
		curScore.setText("Score: " + String.valueOf(frame.getLogic().getScore()));
		
		if(frame.getLogic().gameOver()) middle.setText("GAME OVER");
		else middle.setText(String.valueOf(frame.getLogic().getFrog().getStopwatch()));
	}
}
