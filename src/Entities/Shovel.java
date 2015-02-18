package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MapInterface;

public class Shovel extends Entity{

	public Shovel(int x, int y, MapInterface currentMap) {
		super(x, y, currentMap);
		entityID = 2;
		try {
			setImage(new Image("resources/images/mainMap/shovel.png"), 1);
			getImage().setFilter(Image.FILTER_NEAREST);
			setBounds();
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
