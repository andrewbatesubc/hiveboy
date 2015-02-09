package Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Maps.MapInterface;
import Utilities.Inventory;

public class HiveBoy extends Entity{


	private boolean isLeft = false, isRight = false, isDown = false, isUp = false, isDigging = false, inventorySelected = false;
	private float dx = 0, dy = 0, moveSpeed = 2, scale = 2;
	private int animSpeed = 200, lastFacing = 0,tileX, tileY;
	private SpriteSheet backSheet, forwardSheet, leftSheet, rightSheet, digSheet;
	private Image backStill, forwardStill, leftStill, rightStill;
	private Animation backWalk, forwardWalk, leftWalk, rightWalk, currentAnimation, dig;
	private Inventory inventory;
	// private Sound step; // TODO: Add to sound refactor

	public HiveBoy(int x, int y, MapInterface currentMap){
		super(x, y, currentMap);
		inventory = new Inventory();
	
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
			//currentStill = forwardStill;
			setImage(rightStill, 2);
			setBounds();

			// loads all animations
			backWalk = new Animation(backSheet, animSpeed);
			forwardWalk = new Animation(forwardSheet, animSpeed);
			leftWalk = new Animation(leftSheet, animSpeed);
			rightWalk = new Animation(rightSheet, animSpeed);
			dig = new Animation(digSheet, animSpeed*3);
			currentAnimation = forwardWalk;

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tickAnimation() {
		updateBounds();
		if(!inventorySelected){
		//Updates HiveBoy's current animation based on behavior
		if(isDigging){currentAnimation = dig; dx = 0;}
		if(isRight) {dx = moveSpeed; currentAnimation = rightWalk; setImage(rightStill, scale);}
		else if(isLeft) {dx = -moveSpeed; currentAnimation = leftWalk; setImage(leftStill, scale);}
		else dx = 0; 

		if(isUp) {dy = -moveSpeed; currentAnimation = backWalk; setImage(backStill, scale);}
		else if(isDown) {dy = moveSpeed; currentAnimation = forwardWalk; setImage(forwardStill, 2);}
		else dy = 0;

		if(dx != 0 || dy != 0){
			MovePlayer(getX() + (int)dx, getY() + (int)dy);
		}
		}

	}
	//Checks if tile is valid, and moves if possible
	private void MovePlayer(int dx, int dy) {
		if(ValidMovement(dx, dy)){
			setX(dx);
			setY(dy);
		}
	}

	//Determines if next tile to move is valid (not collidable)
	private boolean ValidMovement(int dx, int dy) {
		int tileWidth = getMap().getTiledMap().getTileWidth();
		tileX = dx/tileWidth + 1;
		tileY = dy/tileWidth + 2;
		int layerIndex = getMap().getTiledMap().getLayerIndex("collision");
		int tileID = getMap().getTiledMap().getTileId(tileX, tileY, layerIndex);
		if(tileID == 0) return true;
		else return false;

	}
	
	public void drawHiveBoy() {
		//Draws HiveBoy's still picture if he isn't moving
		if(dx == 0 && dy == 0 && !isDigging || inventorySelected)
			getImage().draw(getX(), getY(), scale);
		//Otherwise draws HiveBoy's animation based on his activity
		else 	currentAnimation.draw(getX(), getY(), 
				currentAnimation.getWidth()*scale, 
				currentAnimation.getHeight()*scale);
	}
	
	// Handles hiveboy's movement based on key press. Movement disrupts digging
	public void tickKeyHandler(Input input) {
		
		if(input.isKeyPressed(Input.KEY_TAB))
			inventorySelected = !inventorySelected;
		
		if(inventorySelected){
			
			if(input.isKeyPressed(Input.KEY_RIGHT))
				inventory.incrementSelected();
			if(input.isKeyPressed(Input.KEY_LEFT))
				inventory.decrementSelected();
		}
		
		else{
		
		// Key left
		if(input.isKeyDown(Input.KEY_LEFT)){
			isLeft = true;
			lastFacing = 2;
			isDigging = false;
		}
		else isLeft = false;

		// Key right 
		if(input.isKeyDown(Input.KEY_RIGHT)){
			isRight = true;
			lastFacing = 3;
			isDigging = false;
		}
		else isRight = false;

		// Key down 
		if(input.isKeyDown(Input.KEY_DOWN)){
			isDown = true;
			lastFacing = 1;
			isDigging = false;
		}
		else isDown = false;

		// Key up 
		if(input.isKeyDown(Input.KEY_UP)){
			isUp = true;
			lastFacing = 0;
			isDigging = false; // TODO: create better way of disabling dig animation
		}
		else isUp = false;
		}
	}
	public Animation getCurrentAnimation(){ return currentAnimation;}
	public int getTileX(){
		return tileX;
	}
	public int getTileY(){
		return tileY;
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
	public void setFacing(int i) {
		lastFacing = i;
	}
	public boolean getDig() {
		return isDigging;
	}
	public void setDigging(boolean b) {
		isDigging = b;
	}
	public int getFacing(){
		return lastFacing;
	}

	public boolean getInventorySelected(){
		return inventorySelected;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
}

