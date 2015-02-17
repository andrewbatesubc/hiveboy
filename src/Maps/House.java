package Maps;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import Entities.Entity;
import Entities.HiveBoy;

public class House extends BasicGameState implements MapInterface{
	private HiveBoy hiveBoy;
	private TiledMap tiledMap;
	private Camera camera;
	private StateBasedGame game;
	private Input input;
	private Sound doorOpen, doorClose;
	private ArrayList<Entity> entities;

	public House(HiveBoy mainBoy) {
		super();
		hiveBoy = mainBoy;
	}
	

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		setupMap(container, game);
	}


	private void setupMap(GameContainer container, StateBasedGame game)
			throws SlickException {

		input = container.getInput();
		this.game = game;
		hiveBoy.setMap(this);
		tiledMap = new TiledMap("resources/tilemaps/house.tmx");
		camera = new Camera(container, tiledMap, hiveBoy);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		doorOpen = new Sound("resources/sounds/mainMap/doorOpen.wav");
		doorClose = new Sound("resources/sounds/mainMap/doorClose.wav");
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hiveBoy.setMap(this);
		hiveBoy.setX(365);
		hiveBoy.setY(475);
		doorClose.play();
	}
	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		drawCamera();
		hiveBoy.drawHiveBoy();

	}


	private void drawCamera() {
		camera.drawMap();
		camera.translateGraphics();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		tickHiveBoy(delta);
		checkIfEnteredDoor(game);
		centerCameraOnHiveBoy();
	}


	private void checkIfEnteredDoor(StateBasedGame game) {
		if(checkDoor()){
			input.pause();
			doorOpen.play();
			game.enterState(1, new FadeOutTransition(Color.black), new EmptyTransition());		
			input.resume();
		}
	}

	private void tickHiveBoy(int delta) {
		hiveBoy.getCurrentAnimation().update(delta);
		hiveBoy.tickAnimation();
		hiveBoy.tickKeyHandler(input);
		hiveBoy.tickSound(2);
	}

	private void centerCameraOnHiveBoy() {
		camera.centerOn(hiveBoy.getX(), hiveBoy.getY());
	}

	@Override
	public int getID() {
		return 2;
	}
	public TiledMap getTiledMap(){return tiledMap;}

	@Override
	public StateBasedGame returnGame() {
		return game;
	}
	
	private boolean checkDoor() {
		int exitHouseCheckIndex = tiledMap.getLayerIndex("exitHouse");
		int enterID = tiledMap.getTileId(hiveBoy.getTileX(), hiveBoy.getTileY(), exitHouseCheckIndex);
		if(enterID !=0)
			return true;
		else return false;
	}


	@Override
	public ArrayList<Entity> returnEntities() {
		return entities;
	}

}
