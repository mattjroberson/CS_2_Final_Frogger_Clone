package graphics;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class AssetManager {
	private Map<String, List<Image>> imageDict;
	
	public AssetManager() {
		imageDict = new HashMap<String, List<Image>>();
	}
	
	public void load() {
		File directory = new File(System.getProperty("user.dir")+"\\assets");
		
		try {
			if(directory.exists() == false) throw new Exception("Unable to open asset folder.");
		
			for(File file : directory.listFiles()) {
				if(file.getName().split("\\.")[1].equals("png")) {
					
					String name = file.getName().split("\\.")[0];
					int number = getNumber(name.split("_")[0]);
										
					if(number != -1) {
						name = name.split("_", 2)[1];
																
						if(imageDict.containsKey(name)) {
							setIndex(imageDict.get(name), number-1, ImageIO.read(file));
						}
						else {
							List<Image> imgs = new ArrayList<Image>();
							imgs.add(ImageIO.read(file));
							imageDict.put(name,  imgs);
						}
					}
					else {	
						List<Image> imgs = new ArrayList<Image>();
						imgs.add(ImageIO.read(file));
						imageDict.put(name,  imgs);
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public List<Image> getImageSet(String key){
		return imageDict.get(key);
	}
	
	private int getNumber(String number) {
		try {
			return Integer.parseInt(number);
		}
		catch(NumberFormatException e) {
			return -1;
		}
	}
	
	private void setIndex(List<Image> list, int index, Image value) {
		while(list.size() < index) {
			list.add(null);
		}
		list.add(value);
	}
	
}
