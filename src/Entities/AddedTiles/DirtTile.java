package Entities.AddedTiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;

import Maps.MainMap;
import Maps.MapInterface;

public class DirtTile extends AddedTile{
	private Image image;
	private MapInterface currentMap;
	private int x, y;
	
	public DirtTile(int x, int y, MapInterface currentMap){
		this.x = x;
		this.y = y;
		this.currentMap = currentMap;
		try {
			image = new Image("resources/tilemaps/testTiles/dirt.png");
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
		DirtTile other = (DirtTile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
