package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MainMap;

public class BeeBox extends Entity{
	
	
	public BeeBox(int x, int y, MainMap mainMap){
		super(x, y, mainMap);
		
		try {
			setImage(new Image("resources/images/hive/hive.png"), 2);
			getImage().setFilter(Image.FILTER_NEAREST);
			setBounds();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
