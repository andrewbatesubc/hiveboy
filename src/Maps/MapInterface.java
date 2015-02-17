package Maps;

import java.util.ArrayList;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import Entities.Entity;

public interface MapInterface {
	public TiledMap getTiledMap();
	public StateBasedGame returnGame();
	public ArrayList<Entity> returnEntities();

}
