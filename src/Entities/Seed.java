package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MainMap;

public class Seed extends Entity{
	Image seedImage;

	public Seed(int x, int y, MainMap mainMap) {
		super(x, y, mainMap);
		try {
			seedImage = new Image("resources/images/mainMap/seeds.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
