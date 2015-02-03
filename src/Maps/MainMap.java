package Maps;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import Entities.Bee;
import Entities.BeeBox;
import Entities.HiveBoy;
import Entities.AddedTiles.AddedTile;
import Entities.AddedTiles.DirtTile;

public class MainMap extends BasicGameState implements MapInterface{

	private HiveBoy hiveBoy;
	private Bee bee;
	private BeeBox beeBox;
	private TiledMap tiledMap;
	private Camera camera;
	private ArrayList<AddedTile> addedTiles;
	StateBasedGame game;
	Input input;

	public MainMap(HiveBoy mainBoy) {
		super();
		hiveBoy = mainBoy;
	}


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		hiveBoy.setCurrentMap(this);
		bee = new Bee(76, 912, this);
		beeBox = new BeeBox(72,924, this);
		tiledMap = new TiledMap("resources/tilemaps/testedits.tmx");
		camera = new Camera(container, tiledMap);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		addedTiles = new ArrayList<AddedTile>();
		input = container.getInput();
		
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
		
		//Digs!
		if(input.isKeyPressed(Input.KEY_SPACE))
			pressedSpace();
		
		
		// Ticks hiveboy Animation
		hiveBoy.getCurrentAnimation().update(delta);
	

		//Updates hiveboy's current animation
		hiveBoy.tickAnimation();

		//Ticks hiveboy's actual movement
		hiveBoy.tickHiveboyMovement(input);

		//Handles door entry event
		if(checkDoor()){
			input.pause();
			game.enterState(2, new FadeOutTransition(Color.black), new EmptyTransition());		
			input.resume();
		}

		//Centers camera on HiveBoy
		camera.centerOn(hiveBoy.getX(), hiveBoy.getY());

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hiveBoy.setCurrentMap(this);
		hiveBoy.setX(340);
		hiveBoy.setY(850);
	}

	private boolean checkDoor() {
		int enterHouseCheckIndex = tiledMap.getLayerIndex("enterHouse");
		int enterID = tiledMap.getTileId(hiveBoy.getTileX(), hiveBoy.getTileY(), enterHouseCheckIndex);
		if(enterID !=0)
			return true;
		else return false;
	}




	public TiledMap getTiledMap(){return tiledMap;}
	public ArrayList getAddedTiles(){return addedTiles;}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public StateBasedGame returnGame() {

		return game;
	}



	public void pressedSpace() {
		int checkDirt = tiledMap.getLayerIndex("diggable");
		int enterID = tiledMap.getTileId(hiveBoy.getTileX(), hiveBoy.getTileY(), checkDirt);
		if(enterID !=0){
			hiveBoy.setDigging(true);
			//Digs dirt at current tile if in garden
			DirtTile tile = null;
			tile = new DirtTile(hiveBoy.getTileX() * 32, hiveBoy.getTileY() * 32, this);

			if(tile != null && !getAddedTiles().contains(tile))
				addedTiles.add(tile);
		}

	}



}
