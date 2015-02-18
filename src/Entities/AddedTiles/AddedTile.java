package Entities.AddedTiles;

import org.newdawn.slick.Image;

import Maps.MapInterface;

public abstract class AddedTile {
	private Image image;
	private MapInterface currentMap;
	private int x, y;

	public AddedTile(int x, int y, MapInterface currentMap){
		this.x = x;
		this.y = y;
		this.currentMap = currentMap;
	}

	public Image getImage(){
		return image;
	}

	public int getX(){
		return x;
	}
	public  int getY(){
		return y;
	}

	public void setImage(Image image) {
		this.image = image;
	}


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
		AddedTile other = (AddedTile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
