package Utilities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Inventory {

	private ArrayList<InventoryItem> items;
	private Image itemBar, selected;
	private int selectedIndex, indexImageDifference;

	public Inventory(){
		//Loads images, sets selection  to -1 (no selection)
		selectedIndex = 0;
		indexImageDifference = 48;
		try {
			itemBar = new Image("resources/images/mainMap/inventoryScreen.png");
			selected = new Image("resources/images/mainMap/inventorySelected.png");
		} catch (SlickException e) {
			// cannot find image
			e.printStackTrace();
		}
	}

	public void incrementSelected(){
		if(selectedIndex != 5)
			selectedIndex += 1;
	}

	public int getSelected(){
		return selectedIndex;
	}

	public void drawInventory(){
		itemBar.draw(400, 200);
	}
	// 139 for first slot, 91 for second, 48 diff?
	public void drawInventory(int offsetX, int offsetY){
		itemBar.drawCentered(offsetX, offsetY);
		selected.draw(offsetX - 139 + selectedIndex * indexImageDifference, offsetY - 18);
	}

	public Image getImage(){
		return itemBar;
	}

	public void decrementSelected() {
		if(selectedIndex != 0)
			selectedIndex -= 1;
	}
}
