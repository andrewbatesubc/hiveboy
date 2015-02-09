package Maps;

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

import Entities.HiveBoy;

public class House extends BasicGameState implements MapInterface{
	private HiveBoy hiveBoy;
	private TiledMap tiledMap;
	private Camera camera;
	private StateBasedGame game;

	public House(HiveBoy mainBoy) {
		// TODO Auto-generated constructor stub
		super();
		hiveBoy = mainBoy;
	}
	

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		hiveBoy.setMap(this);
		tiledMap = new TiledMap("resources/tilemaps/house.tmx");
		camera = new Camera(container, tiledMap, hiveBoy);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);

	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hiveBoy.setMap(this);
		hiveBoy.setX(365);
		hiveBoy.setY(475);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		//Draws current map
		camera.drawMap();

		//Centers view area over HiveBoy
		camera.translateGraphics();

		//Draws HiveBoy
		hiveBoy.drawHiveBoy();

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		//Ticks current hiveBoy animation
		hiveBoy.getCurrentAnimation().update(delta);

		//Receives current keyboard input
		Input input = container.getInput();

		//Ticks HiveBoy
		hiveBoy.tickAnimation();

		//Ticks hiveboy's actual movement
		hiveBoy.tickKeyHandler(input);
		
		if(checkDoor()){
			input.pause();
			game.enterState(1, new FadeOutTransition(Color.black), new EmptyTransition());		
			input.resume();
		}

		camera.centerOn(hiveBoy.getX(), hiveBoy.getY());

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}
	public TiledMap getTiledMap(){return tiledMap;}

	@Override
	public StateBasedGame returnGame() {
		// TODO Auto-generated method stub
		return game;
	}
	
	private boolean checkDoor() {
		int exitHouseCheckIndex = tiledMap.getLayerIndex("exitHouse");
		int enterID = tiledMap.getTileId(hiveBoy.getTileX(), hiveBoy.getTileY(), exitHouseCheckIndex);
		if(enterID !=0)
			return true;
		else return false;
	}

}
