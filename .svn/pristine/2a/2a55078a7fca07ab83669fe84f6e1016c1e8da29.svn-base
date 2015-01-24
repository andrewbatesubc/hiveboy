package Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Entities.AddedTiles.DirtTile;
import Entities.AddedTiles.ScientistTile;
import Maps.MainMap;

public class HiveBoy extends Entity{
	
	private MainMap mainMap;
	private boolean isLeft = false, isRight = false, isDown = false, isUp = false;;
	private float dx = 0, dy = 0, moveSpeed = 2, scale = 2;
	private int animSpeed = 200, lastFacing = 0, facingTileX = 0, facingTileY = 0;
	private SpriteSheet backSheet, forwardSheet, leftSheet, rightSheet;
	private Image backStill, forwardStill, leftStill, rightStill, currentStill;
	private Animation backWalk, forwardWalk, leftWalk, rightWalk, currentAnimation;
	

	
	public HiveBoy(int x, int y, MainMap mainMap){
		setX(x);
		setY(y);
		this.mainMap = mainMap;
		try {
			
			// loads all spritesheets
			backSheet = new SpriteSheet("resources/spritesheets/hiveboy_backside.png", 32, 32);
			forwardSheet = new SpriteSheet("resources/spritesheets/hiveboy_forward.png", 32, 32);
			leftSheet = new SpriteSheet("resources/spritesheets/hiveboy_sideways_left.png", 32, 32);
			rightSheet = new SpriteSheet("resources/spritesheets/hiveboy_sideways_right.png", 32, 32);
			
			// loads all images
			backStill = new Image("resources/spritesheets/hiveboy_backside_still.png");
			backStill.setFilter(Image.FILTER_NEAREST);
			forwardStill = new Image("resources/spritesheets/hiveboy_still_forward.png");
			forwardStill.setFilter(Image.FILTER_NEAREST);
			leftStill = new Image("resources/spritesheets/hiveboy_sideways_left_still.png");
			leftStill.setFilter(Image.FILTER_NEAREST);
			rightStill = new Image("resources/spritesheets/hiveboy_sideways_right_still.png");
			rightStill.setFilter(Image.FILTER_NEAREST);
			currentStill = forwardStill;
			
			// loads all animations
			backWalk = new Animation(backSheet, animSpeed);
			forwardWalk = new Animation(forwardSheet, animSpeed);
			leftWalk = new Animation(leftSheet, animSpeed);
			rightWalk = new Animation(rightSheet, animSpeed);
			currentAnimation = forwardWalk;
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Animation getCurrentAnimation(){ return currentAnimation;}

	public void tick() {
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
	private void MovePlayer(int dx, int dy) {
		if(ValidMovement(dx, dy)){
			setX(dx);
			setY(dy);
			facingTileX = dx/32;
			facingTileY = dy/32;
		}
		
	}


	private boolean ValidMovement(int dx, int dy) {
		int tileX = dx/32 + 1;
		int tileY = dy/32 + 2;
		int tileId = mainMap.getTiledMap().getTileId(tileX, tileY, 3);
		if(tileId == 0) return true;
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
		DirtTile tile = null;
		if(lastFacing == 2)
			tile = new DirtTile(facingTileX*32, facingTileY*32 + 64, mainMap);
		
		else if(lastFacing == 3)
			tile = new DirtTile(facingTileX*32 + 64, facingTileY*32 + 64, mainMap);
		else if(lastFacing == 1)
			tile = new DirtTile(facingTileX*32 + 32, facingTileY*32 + 64, mainMap);
		else if(lastFacing == 0)
			tile = new DirtTile(facingTileX*32 + 32, facingTileY*32 - 32, mainMap);
	
		if(tile != null && !mainMap.getAddedTiles().contains(tile))
			mainMap.getAddedTiles().add(tile);
	}
		
	


	public void setFacing(int i) {
		lastFacing = i;
		
	}

	
	
	

}
