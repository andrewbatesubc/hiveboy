package Entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import Maps.MapInterface;
import Utilities.Inventory;

public class HiveBoy extends Entity{


	private boolean isLeft = false, isRight = false, isDown = false, isUp = false, 
			isDigging = false, inventorySelected = false, isMoving = false;
	private float dx = 0, dy = 0, moveSpeed = 2, scale = 2;
	private long digTimer = 0;
	private int animSpeed = 200, lastFacing = 0,tileX, tileY;
	private SpriteSheet backSheet, forwardSheet, leftSheet, rightSheet, digSheet;
	private Image backStill, forwardStill, leftStill, rightStill;
	private Animation backWalk, forwardWalk, leftWalk, rightWalk, currentAnimation, dig;
	private Inventory inventory;
	private ArrayList<Entity> collidable;
	private Sound digSound, grassStep, woodStep, inventorySelect, makeSelection, pickup;
	private Entity collidedWith;


	public HiveBoy(int x, int y, MapInterface currentMap){
		super(x, y, currentMap);
		inventory = new Inventory();
		setupImages();
		setupSounds();
	}

	private void setupSounds() {
		try {
			digSound = new Sound("resources/sounds/mainMap/dig.wav");
			grassStep = new Sound("resources/sounds/mainMap/grassStep.wav");
			woodStep = new Sound("resources/sounds/mainMap/woodStep.wav");
			inventorySelect = new Sound("resources/sounds/mainMap/inventorySelect.wav");
			makeSelection = new Sound("resources/sounds/mainMap/makeSelection.wav");
			pickup = new Sound("resources/sounds/mainMap/pickup.wav");

		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	private void setupImages() {
		try {
			backSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_backside.png", 32, 32);
			forwardSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_forward.png", 32, 32);
			leftSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_sideways_left.png", 32, 32);
			rightSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_sideways_right.png", 32, 32);
			digSheet = new SpriteSheet("resources/spritesheets/hiveboy/hiveboy_dig.png", 32, 32);
			backStill = new Image("resources/images/hiveboy/hiveboy_backside_still.png");
			backStill.setFilter(Image.FILTER_NEAREST);
			forwardStill = new Image("resources/images/hiveboy/hiveboy_still_forward.png");
			forwardStill.setFilter(Image.FILTER_NEAREST);
			leftStill = new Image("resources/images/hiveboy/hiveboy_sideways_left_still.png");
			leftStill.setFilter(Image.FILTER_NEAREST);
			rightStill = new Image("resources/images/hiveboy/hiveboy_sideways_right_still.png");
			rightStill.setFilter(Image.FILTER_NEAREST);
			setImage(rightStill, 2);
			setBounds();

			backWalk = new Animation(backSheet, animSpeed);
			forwardWalk = new Animation(forwardSheet, animSpeed);
			leftWalk = new Animation(leftSheet, animSpeed);
			rightWalk = new Animation(rightSheet, animSpeed);
			dig = new Animation(digSheet, animSpeed*3);
			currentAnimation = forwardWalk;

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void tickAnimation() {
		updateBounds();
		nextHiveBoyImage();
	}

	private void nextHiveBoyImage() {
		if(!inventorySelected){
			if(isDigging){currentAnimation = dig;}

			else {
				if(isRight) {dx = moveSpeed; currentAnimation = rightWalk; setImage(rightStill, scale); }
				else if(isLeft) {dx = -moveSpeed; currentAnimation = leftWalk; setImage(leftStill, scale); }
				else {dx = 0;}

				if(isUp) {dy = -moveSpeed; currentAnimation = backWalk; setImage(backStill, scale);}
				else if(isDown) {dy = moveSpeed; currentAnimation = forwardWalk; setImage(forwardStill, 2); }
				else {dy = 0;}

				if(dx != 0 || dy != 0){
					isMoving = true;
					MovePlayer(getX() + (int)dx, getY() + (int)dy);
				}
				else
					isMoving = false;
			}
		}
	}

	private void MovePlayer(int dx, int dy) {
		if(ValidMovement(dx, dy) && !isDigging){
			setX(dx);
			setY(dy);
		}
	}

	private boolean ValidMovement(int dx, int dy) {
		return (checkTilemapCollision(dx, dy) && checkEntityCollision(dx, dy));
	}

	private boolean checkTilemapCollision(int dx, int dy) {
		int tileWidth = getMap().getTiledMap().getTileWidth();
		tileX = dx/tileWidth + 1;
		tileY = dy/tileWidth + 2;
		int layerIndex = getMap().getTiledMap().getLayerIndex("collision");
		int tileID = getMap().getTiledMap().getTileId(tileX, tileY, layerIndex);
		if(tileID == 0) return true;
		else return false;
	}

	private boolean checkEntityCollision(int dx, int dy) {
		Rectangle toCheck = new Rectangle(dx, dy, 
				getRectangle().getWidth(), getRectangle().getHeight());

		for(Entity e : collidable){
			if(e.getRectangle().intersects(toCheck)){
				collidedWith = e;
				return false;
			}
		}
		collidedWith = null;
		return true;
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

	// Handles HiveBoy's movement based on key press. Movement disrupts digging
	public void tickKeyHandler(Input input) {
		if(input.isKeyPressed(Input.KEY_TAB)){
			inventorySelected = !inventorySelected;
			inventorySelect.play();
			isMoving = false;
			input.clearKeyPressedRecord();
		}

		else if(inventorySelected){
			handleInventoryKeys(input);
		}

		else if(isDigging){
			dx = 0;
			dy = 0;
			setImage(forwardStill, 2);
		}
		else
			handleMovementKeys(input);

	}

	private void handleInventoryKeys(Input input) {
		if(input.isKeyPressed(Input.KEY_RIGHT)){
			inventory.incrementSelected();
			makeSelection.play();
		}
		else if(input.isKeyPressed(Input.KEY_LEFT)){
			inventory.decrementSelected();
			makeSelection.play();
		}
	}

	private void handleMovementKeys(Input input) {
		// Key left
		if(input.isKeyDown(Input.KEY_LEFT)){
			isLeft = true;
			lastFacing = 2;
		}
		else isLeft = false;

		// Key right 
		if(input.isKeyDown(Input.KEY_RIGHT)){
			isRight = true;
			lastFacing = 3;
		}
		else isRight = false;

		// Key down 
		if(input.isKeyDown(Input.KEY_DOWN)){
			isDown = true;
			lastFacing = 1;
		}
		else isDown = false;

		// Key up 
		if(input.isKeyDown(Input.KEY_UP)){
			isUp = true;
			lastFacing = 0;
		}
		else isUp = false;

		if(input.isKeyPressed(Input.KEY_LSHIFT) && collidedWith != null && collidedWith.getID() != 0 && !inventory.isInventoryFull()){
			inventory.addItem(collidedWith);
			currentMap.returnEntities().remove(collidedWith);
			pickup.play();
			collidedWith = null;
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
	public void setCollidable(ArrayList<Entity> list){
		collidable = list;
	}

	public void startDigging(long nanoTime) {
		digTimer = nanoTime;
		isDigging = true;
	}

	public void tickDig() {
		if(System.nanoTime() - digTimer > 2000000000)
			isDigging = false;

	}

	public void tickSound(int mapCode) {
		if(mapCode == 1){
			if(isDigging && !digSound.playing())
				digSound.play();
			else if(isMoving && !grassStep.playing())
				grassStep.play();
		}

		else if(mapCode == 2){
			if(isMoving && !woodStep.playing())
				woodStep.play();
		}

	}
}

