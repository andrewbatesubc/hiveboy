package Maps;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import Entities.Bee;
import Entities.BeeBox;
import Entities.HiveBoy;
import Entities.AddedTiles.AddedTile;

public class MainMap extends BasicGameState{

	private HiveBoy hiveBoy;
	private Bee bee;
	private BeeBox beeBox;
	private TiledMap tiledMap;
	private Camera camera;
	private ArrayList<AddedTile> addedTiles;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		hiveBoy = new HiveBoy(400, 400, this);
		bee = new Bee(300, 300, this);
		beeBox = new BeeBox(200, 200, this);
		tiledMap = new TiledMap("resources/tilemaps/testedits.tmx");
		camera = new Camera(container, tiledMap);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		tiledMap.getLayerIndex("collision");
		addedTiles = new ArrayList<AddedTile>();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//Draws the map
		camera.drawMap();
		
		//Renders the current area HiveBoy is occupying
		camera.translateGraphics();
		
		//Draws all added dirt tiles
		for(AddedTile tile : addedTiles){
			tile.getImage().draw(tile.getX(), tile.getY());
		}
		
		//Draws HiveBoy
		hiveBoy.drawHiveBoy();

		//Draws a bee
		bee.getCurrentAnimation().draw(bee.getX(), bee.getY());
		
		//Draw's a bee-box
		beeBox.getImage().draw(beeBox.getX(), beeBox.getY(), 2);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		// Ticks hiveboy Animation
		hiveBoy.getCurrentAnimation().update(delta);

		//Recieves keyboard input
		Input input = container.getInput();
		
		//Ticks hiveboy
		hiveBoy.tick(input);
		
		//Centers camera on HiveBoy
		camera.centerOn(hiveBoy.getX(), hiveBoy.getY());
	}

	

	public TiledMap getTiledMap(){return tiledMap;}
	public ArrayList getAddedTiles(){return addedTiles;}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}



}
