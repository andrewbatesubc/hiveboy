package Utilities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Entities.Entity;

public class Inventory {

	private Entity[] items;
	private Image itemBar, selected;
	private int selectedIndex, indexImageDifference, currentFreeIndex = 0;
	private boolean isInventoryFull = false;
	private Entity currentlySelectedItem;

	public Inventory(){
		//Loads images, sets selection  to -1 (no selection)
		selectedIndex = 0;
		indexImageDifference = 48;
		items = new Entity[6];
		currentlySelectedItem = items[selectedIndex];
		try {
			itemBar = new Image("resources/images/mainMap/inventoryScreen.png");
			selected = new Image("resources/images/mainMap/inventorySelected.png");
		} catch (SlickException e) {
			// cannot find image
			e.printStackTrace();
		}
	}



	public void addItem(Entity collidedWith) {
		if(!isInventoryFull){
			items[currentFreeIndex] = collidedWith;
			for(int i = 0; i < items.length; i++){
				isInventoryFull = true;
				if(items[i] == null){
					currentFreeIndex = i;
					isInventoryFull = false;
					break;
				}
			}

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
		for(int i = 0; i < items.length; i++){
			if(items[i] != null)
			items[i].getImage().draw(offsetX - 130 + i * indexImageDifference, offsetY - 9);
		}
	}

	public Image getImage(){
		return itemBar;
	}

	public void decrementSelected() {
		if(selectedIndex != 0)
			selectedIndex -= 1;
	}

	public Entity[] getItems(){
		return items;
	}
	
	public boolean isInventoryFull(){
		return isInventoryFull;
	}







}
