package Maps;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
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
		hiveBoy.setCurrentMap(this);
		tiledMap = new TiledMap("resources/tilemaps/house.tmx");
		camera = new Camera(container, tiledMap);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		
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
		hiveBoy.tick(input);
		
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

}
