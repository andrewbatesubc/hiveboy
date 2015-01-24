package Main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Maps.MainMap;

public class StarterClass extends StateBasedGame{

	public StarterClass(String title){
		super(title);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MainMap());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new StarterClass("Test"));
		app.setDisplayMode(800, 600, false);
		app.setAlwaysRender(true);
		app.start();
	}

}
