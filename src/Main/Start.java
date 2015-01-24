package Main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Maps.IntroScreen;
import Maps.MainMap;

public class Start extends StateBasedGame{

	public Start(String title){
		super(title);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new IntroScreen());
		addState(new MainMap());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Start("Test"));
		app.setDisplayMode(800, 600, false);
		app.setAlwaysRender(true);
		app.start();
	}

}
