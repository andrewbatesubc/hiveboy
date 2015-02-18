package Maps;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import Entities.Bee;
import Entities.BeeBox;
import Entities.Entity;
import Entities.HiveBoy;
import Entities.Seed;
import Entities.Shovel;
import Entities.Sprinkler;
import Entities.AddedTiles.AddedTile;
import Entities.AddedTiles.DirtTile;
import Entities.AddedTiles.SeedTile;

public class MainMap extends BasicGameState implements MapInterface{

	private HiveBoy hiveBoy;
	private Bee bee;
	private BeeBox beeBox;
	private TiledMap tiledMap;
	private Camera camera;
	private ArrayList<AddedTile> addedTiles;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toRender;
	private StateBasedGame game;
	private Input input;
	private Seed[] seeds;
	private Rectangle cameraRectangle;
	private Sound doorOpen, doorClose;

	public MainMap(HiveBoy mainBoy) {
		super();
		hiveBoy = mainBoy;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		setupMap(container, game);
		setupEntities();
	}

	private void setupEntities() {
		seeds = new Seed[10];
		bee = new Bee(86, 912, this);
		beeBox = new BeeBox(72,924, this);
		seeds[0] = new Seed(450, 915, this);
		seeds[1] = new Seed(310, 1015, this);
		seeds[2] = new Seed(590, 945, this);
		seeds[3] = new Seed(630, 1015, this);
		seeds[4] = new Seed(350, 935, this);

		for(int i = 0; i < 5; i++){
			entities.add(seeds[i]);
		}
		entities.add(beeBox);
	}

	private void setupMap(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		hiveBoy.setMap(this);
		entities = new ArrayList<Entity>();
		addedTiles = new ArrayList<AddedTile>();
		toRender = new ArrayList<Entity>();
		cameraRectangle = new Rectangle(0, 0, 800, 600);
		tiledMap = new TiledMap("resources/tilemaps/testedits.tmx");
		camera = new Camera(container, tiledMap, hiveBoy);
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		input = container.getInput();
		doorOpen = new Sound("resources/sounds/mainMap/doorOpen.wav");
		doorClose = new Sound("resources/sounds/mainMap/doorClose.wav");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		drawCamera();
		drawAddedTiles();
		hiveBoy.drawHiveBoy();
		drawBees();
		drawEntities();

	}

	private void drawCamera() {
		camera.drawMap();
		camera.translateGraphics();
	}

	private void drawEntities() {
		for(Entity e : toRender){
			e.getImage().draw(e.getX(), e.getY(), e.getScale());
		}
	}

	private void drawBees() {
		bee.getCurrentAnimation().draw(bee.getX(), bee.getY());
	}

	private void drawAddedTiles() {
		for(AddedTile tile : addedTiles){
			tile.getImage().draw(tile.getX(), tile.getY());
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		updateCameraCollisionRectangle();
		updateCollidableEntities();
		checkDiggable();
		tickHiveBoy(delta);
		checkIfEnteredDoor(game);
		centerCameraOnHiveBoy();
	}

	private void centerCameraOnHiveBoy() {
		camera.centerOn(hiveBoy.getX(), hiveBoy.getY());
	}

	private void checkIfEnteredDoor(StateBasedGame game) {
		if(checkDoor()){
			input.pause();
			doorOpen.play();
			game.enterState(2, new FadeOutTransition(Color.black), new EmptyTransition());		
			input.resume();
		}
	}

	private void checkDiggable() {
		if(input.isKeyPressed(Input.KEY_SPACE) && !hiveBoy.getDig()){
			timeToDig();
		}
	}

	private void tickHiveBoy(int delta) {
		hiveBoy.tickSound(1);
		hiveBoy.tickBusy();
		hiveBoy.tickAnimation();
		hiveBoy.getCurrentAnimation().update(delta);
		hiveBoy.tickKeyHandler(input);
	}

	private void updateCollidableEntities() {
		toRender.clear();
		for(Entity e : entities){
			if(cameraRectangle.intersects(e.getRectangle()))
				toRender.add(e);
		}
	}

	private void updateCameraCollisionRectangle() {
		if(400 < hiveBoy.getX() && hiveBoy.getX() < 1200)
			cameraRectangle.setCenterX(hiveBoy.getX());
		if(300 < hiveBoy.getY() && hiveBoy.getY() < 1300)
			cameraRectangle.setCenterY(hiveBoy.getY());
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hiveBoy.setMap(this);
		hiveBoy.setX(340);
		hiveBoy.setY(850);
		hiveBoy.setCollidable(toRender);
		doorClose.play();
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
		return 1;
	}

	@Override
	public StateBasedGame returnGame() {
		return game;
	}

	public void timeToDig(){
		int checkDirt = tiledMap.getLayerIndex("diggable");
		int enterID = tiledMap.getTileId(hiveBoy.getTileX(), hiveBoy.getTileY(), checkDirt);
		if(enterID !=0){

			AddedTile tileStandingOn = null;
			for(AddedTile tile: addedTiles){
				if(tile.getX()/32 == hiveBoy.getTileX() && tile.getY()/32 == hiveBoy.getTileY()){
					tileStandingOn = tile;
					break;
				}
			}

			Entity inventoryItem =  hiveBoy.getInventory().getSelectedItem();
			if(inventoryItem == null){
				startCrying();
			}

			else{
				if(tileStandingOn == null){
					if(inventoryItem.getClass().equals(Shovel.class)){
						beginDigging();
					}
					else startCrying();
				}

				else if(tileStandingOn.getClass().equals(DirtTile.class)){
					if(inventoryItem.getClass().equals(Seed.class)){
						plantSeeds(tileStandingOn);
					}
					else startCrying();
				}
				else if(tileStandingOn.getClass().equals(SeedTile.class)){
					if(!((SeedTile) tileStandingOn).isWatered() && inventoryItem.getClass().equals(Sprinkler.class)){
						((SeedTile) tileStandingOn).toggleWatered();
						hiveBoy.startBusy(System.nanoTime());
						hiveBoy.setCurrentAction("watering");
					}
				}
			}
		}
	}

	private void plantSeeds(AddedTile tileStandingOn) {
		hiveBoy.startBusy(System.nanoTime());
		hiveBoy.setCurrentAction("planting");
		addedTiles.remove(tileStandingOn);
		SeedTile seedTile = new SeedTile(tileStandingOn.getX(), tileStandingOn.getY(), this);
		addedTiles.add(seedTile);
		hiveBoy.getInventory().removeCurrentlySelectedItem();
	}

	private void beginDigging() {
		hiveBoy.startBusy(System.nanoTime());
		hiveBoy.setCurrentAction("digging");
		DirtTile tile = new DirtTile(hiveBoy.getTileX() * 32, hiveBoy.getTileY() * 32, this);
		if(tile != null && !getAddedTiles().contains(tile))
			addedTiles.add(tile);
	}

	private void startCrying() {
		hiveBoy.setCurrentAction("crying");
		hiveBoy.startBusy(System.nanoTime());
	}



	@Override
	public ArrayList<Entity> returnEntities() {
		return entities;
	}



}
