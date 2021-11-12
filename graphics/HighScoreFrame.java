package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class HighScoreFrame extends JFrame {
	
		private JLabel[][] scoreLabels;
		private Font titleFont;
		private Font font;
		
		private JPanel scorePanel;
		private JLabel title;
		private JTextField textField;
		
		private int scoreIndex;
		
		public HighScoreFrame(FroggerFrame frame) {
			super();
						
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			getContentPane().setBackground(Color.black);
			
			titleFont = new Font("ARIAL", Font.BOLD, 40);
			font = new Font("ARIAL", Font.BOLD, 22);
			
			title = new JLabel();
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			title.setOpaque(true);
			title.setBackground(Color.BLACK);
			title.setForeground(Color.WHITE);
			title.setBorder(new EmptyBorder(15, 15, 15, 15));
			title.setFont(titleFont);
			add(title);
			
			textField = new JTextField();
			textField.setFont(font);
			textField.setBackground(Color.BLACK);
			textField.setForeground(Color.YELLOW);
			textField.setCaretColor(Color.YELLOW);
			textField.addActionListener(new ActionListener(){  
				@Override
				public void actionPerformed(ActionEvent e){  
					if(textField.isEditable()) {
						frame.getLogic().getHighScoreManager().write(
								new String[] {textField.getText(), 
								String.valueOf(frame.getLogic().getScore())}, 
								scoreIndex); 
					}
					frame.getLogic().restart(); 
					closeWindow();
				}
			});  
			
			add(textField);
			
			scorePanel = new JPanel(new GridLayout(0, 2));
			scorePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
			scorePanel.setBackground(Color.black);
			add(scorePanel);
			
			scoreLabels = new JLabel[10][2];			
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 2; j++) {
					scoreLabels[i][j] = new JLabel();
					scoreLabels[i][j].setFont(font);
					scoreLabels[i][j].setForeground(Color.yellow);
					scorePanel.add(scoreLabels[i][j]);
				}
				
				scoreLabels[i][0].setHorizontalAlignment(JLabel.LEFT);
				scoreLabels[i][1].setHorizontalAlignment(JLabel.RIGHT);
			}
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					frame.getLogic().restart();
				}
				
				@Override
				public void windowActivated(WindowEvent e) { 
					frame.getLogic().getHighScoreManager().load();
					scoreIndex = frame.getLogic().getHighScoreManager().getHighScoreIndex(frame.getLogic().getScore());
					
					if(scoreIndex != -1) {
						title.setText("High Score!");
						textField.setText("ENTER NAME");
						textField.setEditable(true);
					}else {
						title.setText("Game Over.");
						textField.setText("");
						textField.setEditable(false);
					}
					
					int i;
					
					for(i = 0; i < frame.getLogic().getHighScoreManager().getScores().size(); i++) {
						String[] scoreArr = frame.getLogic().getHighScoreManager().getScores().get(i);
						scoreLabels[i][0].setText(scoreArr[0]);
						scoreLabels[i][1].setText(scoreArr[1]);
					}
					
					for(int j = i; j < scoreLabels.length; j++) {
						scoreLabels[j][0].setText(" ");
						scoreLabels[j][0].setText(" ");
					}
	
					pack();
					setLocationRelativeTo(frame);
				}
			});
		}
		
		private void closeWindow() {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
}

