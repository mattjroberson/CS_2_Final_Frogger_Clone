package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HighScoreManager {

	private List<String[]> scores;
	private File scoreFile;
	
	public HighScoreManager() {
		scoreFile = new File(System.getProperty("user.dir")+"\\highscores.txt");
	}

	public void load(){
		List<String[]> highScores = new ArrayList<String[]>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
			
			String line = reader.readLine();
			while (line != null) {
				String[] stringArr = line.split(":", 2);
				if(stringArr.length < 2) throw new Exception("High Scores currupted");
				
				highScores.add(stringArr);
				line = reader.readLine();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			scores = highScores;
		}
	}
	
	public int getHighScoreIndex(int newScore) {
		int i;
		
		for(i = 0; i < scores.size(); i++) {
			if(newScore > Integer.parseInt(scores.get(i)[1])) {
				return i;
			}
		}
		
		if(scores.size() < 10) return i;
		
		return -1;
	}
	
	
	public void write(String[] insert, int newIndex) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(scoreFile));			
			boolean wroteNewScore = false;
					
			for(int i = 0; i < scores.size(); i++) {
				if(i == newIndex) {
					out.println(insert[0]+":"+insert[1]);
					wroteNewScore = true;
				}
				if(wroteNewScore == false || i < scores.size()-1) {
					out.println(scores.get(i)[0]+":"+scores.get(i)[1]);
				}
			}
			
			if(!wroteNewScore) out.println(insert[0]+":"+insert[1]);
			
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String[]> getScores() { return scores; }
}
