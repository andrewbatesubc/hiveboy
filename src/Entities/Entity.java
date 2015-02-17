package Entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import Maps.MapInterface;

public abstract class Entity {
	private int x, y;
	protected int entityID;
	//0 = beeBox, 1 = seed
	protected MapInterface currentMap;
	private Image image;
	private Rectangle collisionRectangle;
	private float imageScale = 1;
	
	public Entity(int x, int y, MapInterface currentMap){
		this.x = x;
		this.y = y;
		this.currentMap = currentMap;
	
	}
	
	public void setImage(Image image, float imageScale){
		this.image = image;
		this.imageScale = imageScale;
	}
	
	public Image getImage(){
		return image;
	}
	
	// Sets collision bounds for any entity
	public void setBounds(){
		collisionRectangle = new Rectangle(x, y, image.getWidth() * imageScale - 10, image.getHeight() * imageScale);
	}
	
	public void updateBounds(){
		collisionRectangle.setLocation(x, y);
	}
	
	public Rectangle getRectangle(){
		return collisionRectangle;
	}
	
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public int getX(){return x;}
	public int getY(){return y;}
	public MapInterface getMap(){
		return currentMap;
	}
	
	public void setMap(MapInterface map){
		currentMap = map;
	}
	
	public float getScale(){
		return imageScale;
	}
	
	public int getID(){
		return entityID;
	}
	

}
