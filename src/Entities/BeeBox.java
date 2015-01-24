package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MainMap;

public class BeeBox extends Entity{
	
	private MainMap mainMap;
	private Image hiveImage;
	
	public BeeBox(int x, int y, MainMap mainMap){
		setX(x);
		setY(y);
		this.mainMap = mainMap;
		try {
			hiveImage = new Image("resources/spritesheets/hive.png");
			hiveImage.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image getImage(){
		return hiveImage;
	}
}
