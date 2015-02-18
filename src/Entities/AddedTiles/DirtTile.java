package Entities.AddedTiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;

import Maps.MainMap;
import Maps.MapInterface;

public class DirtTile extends AddedTile{


	public DirtTile(int x, int y, MapInterface currentMap){
		super(x, y, currentMap);
		try {
			setImage(new Image("resources/tilemaps/testTiles/dirt.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


}
