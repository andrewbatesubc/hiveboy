package Entities.AddedTiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Maps.MainMap;

public class ScientistTile extends AddedTile{
	private Image image;
	private MainMap mainMap;
	private int x, y;
	
	public ScientistTile(int x, int y, MainMap mainMap){
		this.x = x;
		this.y = y;
		this.mainMap = mainMap;
		try {
			image = new Image("resources/tilemaps/testTiles/science.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScientistTile other = (ScientistTile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
