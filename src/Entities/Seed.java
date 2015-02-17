package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MainMap;

public class Seed extends Entity{


	public Seed(int x, int y, MainMap mainMap) {
		super(x, y, mainMap);
		entityID = 1;
		try {
			setImage(new Image("resources/images/mainMap/seeds.png"), 1);
			getImage().setFilter(Image.FILTER_NEAREST);
			setBounds();
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
