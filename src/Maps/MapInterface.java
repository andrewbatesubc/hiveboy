package Maps;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public interface MapInterface {
	public TiledMap getTiledMap();
	public StateBasedGame returnGame();

}
