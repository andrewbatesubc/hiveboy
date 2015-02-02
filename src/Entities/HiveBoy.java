package Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Entities.AddedTiles.DirtTile;
import Entities.AddedTiles.ScientistTile;
import Maps.MainMap;
import Maps.MapInterface;

public class HiveBoy extends Entity{

	private MapInterface currentMap;
	private boolean isLeft = false, isRight = false, isDown = false, isUp = false, isDigging = false;
	private float dx = 0, dy = 0, moveSpeed = 2, scale = 2;
	private int animSpeed = 200, lastFacing = 0, facingTileX = 0, facingTileY = 0;
	private SpriteSheet backSheet, forwardSheet, leftSheet, rightSheet, digSheet;
	private Image backStill, forwardStill, leftStill, rightStill, currentStill;
	private Animation backWalk, forwardWalk, leftWalk, rightWalk, currentAnimation, dig;
	private Sound step;
	private long stepCounter = 0;

	public HiveBoy(int x, int y, MapInterface currentMap){
		setX(x);
		setY(y);
		this.currentMap = currentMap;
		try {
			// loads all spritesheets
			backSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_backside.png", 32, 32);
			forwardSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_forward.png", 32, 32);
			leftSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_sideways_left.png", 32, 32);
			rightSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_sideways_right.png", 32, 32);
			digSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_dig.png", 32, 32);

			// loads all images
			backStill = new Image("resources/images/hiveboy/hiveboy_backside_still.png");
			backStill.setFilter(Image.FILTER_NEAREST);
			forwardStill = new Image("resources/images/hiveboy/hiveboy_still_forward.png");
			forwardStill.setFilter(Image.FILTER_NEAREST);
			leftStill = new Image("resources/images/hiveboy/hiveboy_sideways_left_still.png");
			leftStill.setFilter(Image.FILTER_NEAREST);
			rightStill = new Image("resources/images/hiveboy/hiveboy_sideways_right_still.png");
			rightStill.setFilter(Image.FILTER_NEAREST);
			currentStill = forwardStill;

			// loads all animations
			backWalk = new Animation(backSheet, animSpeed);
			forwardWalk = new Animation(forwardSheet, animSpeed);
			leftWalk = new Animation(leftSheet, animSpeed);
			rightWalk = new Animation(rightSheet, animSpeed);
			dig = new Animation(digSheet, animSpeed*2);
			currentAnimation = forwardWalk;

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Animation getCurrentAnimation(){ return currentAnimation;}

	public void tick(Input input) {
		hiveboyMovement(input);
		//Updates HiveBoy's current animation based on behavior
		if(isDigging){currentAnimation = dig;}
		if(isRight) {dx = moveSpeed; currentAnimation = rightWalk; currentStill = rightStill;}
		else if(isLeft) {dx = -moveSpeed; currentAnimation = leftWalk; currentStill = leftStill;}
		else dx = 0; 

		if(isUp) {dy = -moveSpeed; currentAnimation = backWalk; currentStill = backStill;}
		else if(isDown) {dy = moveSpeed; currentAnimation = forwardWalk; currentStill = forwardStill;}
		else dy = 0;

		if(dx != 0 || dy != 0){
			MovePlayer(getX() + (int)dx, getY() + (int)dy);
		}

	}
	//Checks if tile is valid, and moves if possible
	private void MovePlayer(int dx, int dy) {
		if(ValidMovement(dx, dy)){
			setX(dx);
			setY(dy);
			facingTileX = dx/32;
			facingTileY = dy/32;

		}
	}

	//Determines if next tile to move is valid (not collidable)
	private boolean ValidMovement(int dx, int dy) {
		int tileWidth = currentMap.getTiledMap().getTileWidth();
		int tileX, tileY;
		if(tileWidth == 16){
			tileX = dx/16 + 1;
			tileY = dy/16 + 2;
		}
		else
		{
			tileX = dx/32 + 1;
			tileY = dy/32 + 2;
		}
		System.out.println(getX());
		System.out.println(getY());
		System.out.println(tileWidth);
		int eventCheckIndex = currentMap.getTiledMap().getLayerIndex("events");
		int eventID = currentMap.getTiledMap().getTileId(tileX, tileY, eventCheckIndex);
		if(eventID !=0){ 
			
			currentMap.returnGame().enterState(1, new FadeOutTransition(Color.black), new EmptyTransition());
			setX(342);
			setY(852);
		}
		int layerIndex = currentMap.getTiledMap().getLayerIndex("collision");
		int tileID = currentMap.getTiledMap().getTileId(tileX, tileY, layerIndex);
		if(tileID == 0) return true;
		else return false;

	}


	public float getScale(){return scale;}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public boolean isLeft() {
		return isLeft;
	}
	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}
	public boolean isDown() {
		return isDown;
	}
	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public Image getCurrentStill() {
		return currentStill;
	}
	public void pressedSpace() {
		//Digs dirt at current tile TODO: Validate dig location
		DirtTile tile = null;
		isDigging = true;
		tile = new DirtTile(facingTileX*32 + 32, facingTileY*32 + 64, currentMap);
		if(tile != null && !((MainMap) currentMap).getAddedTiles().contains(tile))
			((MainMap) currentMap).getAddedTiles().add(tile);

	}
	public void setFacing(int i) {
		lastFacing = i;
	}
	public boolean getDig() {
		return isDigging;
	}
	public void setDigging(boolean b) {
		isDigging = b;
	}
	public void hiveboyMovement(Input input) {
		// Key left
		if(input.isKeyDown(Input.KEY_LEFT)){
			isLeft = true;
			lastFacing = 2;
			isDigging = false; // TODO
		}
		else isLeft = false;

		// Key right 
		if(input.isKeyDown(Input.KEY_RIGHT)){
			isRight = true;
			lastFacing = 3;
			isDigging = false; // TODO
		}
		else isRight = false;

		// Key down 
		if(input.isKeyDown(Input.KEY_DOWN)){
			isDown = true;
			lastFacing = 1;
			isDigging = false; // TODO
		}
		else isDown = false;

		// Key up 
		if(input.isKeyDown(Input.KEY_UP)){
			isUp = true;
			lastFacing = 0;
			isDigging = false; // TODO
		}
		else isUp = false;

		// Key space
		if(input.isKeyPressed(Input.KEY_SPACE)){
			pressedSpace();
		}
	}




	public void drawHiveBoy() {
		//Draws HiveBoy's still picture if he isn't moving
		if(dx == 0 && dy == 0 && !isDigging)
			currentStill.draw(getX(), getY(), scale);
		//Otherwise draws HiveBoy's animation based on his activity
		else 	currentAnimation.draw(getX(), getY(), 
				currentAnimation.getWidth()*scale, 
				currentAnimation.getHeight()*scale);
	}

	public void setCurrentMap(MapInterface map){
		currentMap = map;
	}

}




