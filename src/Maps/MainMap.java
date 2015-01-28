package Maps;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import Entities.Bee;
import Entities.BeeBox;
import Entities.HiveBoy;
import Entities.AddedTiles.AddedTile;
import Entities.AddedTiles.DirtTile;

public class MainMap extends BasicGameState{

	private HiveBoy hiveBoy;
	private Bee bee;
	private BeeBox beeBox;
	private TiledMap tiledMap;
	private Camera camera;
	private ArrayList<AddedTile> addedTiles;
	private int timer = 0;

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
		camera.drawMap();
		camera.translateGraphics();


		for(AddedTile tile : addedTiles){
			tile.getImage().draw(tile.getX(), tile.getY());
		}
		if(hiveBoy.getDx() == 0 && hiveBoy.getDy() == 0 && !hiveBoy.getDig())
			hiveBoy.getCurrentStill().draw(hiveBoy.getX(), hiveBoy.getY(), hiveBoy.getScale());

		else 	hiveBoy.getCurrentAnimation().draw(hiveBoy.getX(), hiveBoy.getY(), 
				hiveBoy.getCurrentAnimation().getWidth()*hiveBoy.getScale(), 
				hiveBoy.getCurrentAnimation().getHeight()*hiveBoy.getScale());

		bee.getCurrentAnimation().draw(bee.getX(), bee.getY());
		beeBox.getImage().draw(beeBox.getX(), beeBox.getY(), 2);


	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Ticks hiveboy Animation
		hiveBoy.getCurrentAnimation().update(delta);

		// Handles input
		Input input = container.getInput();

		// Key left
		if(input.isKeyDown(Input.KEY_LEFT)){
			hiveBoy.setLeft(true);
			hiveBoy.setFacing(2);
			hiveBoy.setDigging(false); // TODO
		}
		else hiveBoy.setLeft(false);

		// Key right 
		if(input.isKeyDown(Input.KEY_RIGHT)){
			hiveBoy.setRight(true);
			hiveBoy.setFacing(3);
			hiveBoy.setDigging(false); // TODO
		}
		else hiveBoy.setRight(false);

		// Key down 
		if(input.isKeyDown(Input.KEY_DOWN)){
			hiveBoy.setDown(true);
			hiveBoy.setFacing(1);
			hiveBoy.setDigging(false); // TODO
		}
		else hiveBoy.setDown(false);

		// Key up 
		if(input.isKeyDown(Input.KEY_UP)){
			hiveBoy.setUp(true);
			hiveBoy.setFacing(0);
			hiveBoy.setDigging(false); // TODO
		}
		else hiveBoy.setUp(false);

		// Key space
		if(input.isKeyPressed(Input.KEY_SPACE)){
			hiveBoy.pressedSpace();
		}



		// Ticks hiveboy
		hiveBoy.tick();
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
