package Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Maps.MainMap;

public class Bee extends Entity{
	
	private MainMap mainMap;
	private int animSpeed = 70;
	private SpriteSheet beeSheetLeft, beeSheetRight;
	private Animation beeBuzzLeft, beeBuzzRight, currentAnimation;
	public Bee(int x, int y, MainMap mainMap){
		this.mainMap = mainMap;
		setX(x);
		setY(y);
		try {
			beeSheetLeft = new SpriteSheet("resources/spritesheets/bee/bee_left_flying.png", 32, 32);
			beeSheetRight = new SpriteSheet("resources/spritesheets/bee/bee_right_flying.png", 32, 32);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beeBuzzLeft = new Animation(beeSheetLeft, animSpeed);
		beeBuzzRight = new Animation(beeSheetRight, animSpeed);
		currentAnimation = beeBuzzLeft;
		
	}
	
	public Animation getCurrentAnimation(){ return currentAnimation;}
}	
