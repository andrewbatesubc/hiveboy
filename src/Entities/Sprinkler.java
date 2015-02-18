package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MapInterface;

public class Sprinkler extends Entity{

	public Sprinkler(int x, int y, MapInterface currentMap) {
		super(x, y, currentMap);
		entityID = 3;
		try {
			setImage(new Image("resources/images/mainMap/waterSprinkler.png"), 1);
			getImage().setFilter(Image.FILTER_NEAREST);
			setBounds();
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
