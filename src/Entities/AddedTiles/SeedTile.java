package Entities.AddedTiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MapInterface;

public class SeedTile extends AddedTile{
	private boolean isWatered = false;

	public SeedTile(int x, int y, MapInterface currentMap) {
		super(x, y, currentMap);
		try {
			setImage(new Image("resources/tilemaps/testTiles/dirtWithSeeds.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void toggleWatered(){

		try {
			if(isWatered)
				setImage(new Image("resources/tilemaps/testTiles/dirtWithSeeds.png"));
			
			else {
				setImage(new Image("resources/tilemaps/testTiles/dirtWithSeedsWatered.png"));
			}
			isWatered = !isWatered;
		
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWatered(){
		return isWatered;
	}




}
