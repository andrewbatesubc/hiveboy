package Maps;

import java.awt.Container;

import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class IntroScreen extends BasicGameState{

	private Image startSelected, startUnselected, quitSelected, quitUnselected, backgroundScreen;
	private Sound selected, choose;
	private boolean isStartSelected = true;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Caps frame-rate at 60
		container.setTargetFrameRate(60);
		// Keeps game updating when out of focus
		container.setUpdateOnlyWhenVisible(false);
		// Initializes all start screen images
		startSelected = new Image("resources/images/introscreen/START_selected.png");
		startUnselected = new Image("resources/images/introscreen/START_unselected.png");
		quitSelected = new Image("resources/images/introscreen/QUIT_selected.png");
		quitUnselected = new Image("resources/images/introscreen/QUIT_unselected.png");
		backgroundScreen = new Image("resources/images/introscreen/startScreen.png");
		// Initializes intro screen sounds
		choose = new Sound("resources/sounds/introScreen/choose.wav");
		selected = new Sound("resources/sounds/introScreen/selected.wav");

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// Draws initial startScreen with start selected
		backgroundScreen.draw();

		// Draws menu buttons based on selection
		if(isStartSelected){// Highlights Start
		 	startSelected.drawCentered(400,300);
			quitUnselected.drawCentered(400, 425);
		}
		else{				// Highlights Quit
			startUnselected.drawCentered(400,300);
			quitSelected.drawCentered(400, 425);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		//Handles key input
		Input input = container.getInput();

		if(input.isKeyPressed(Input.KEY_DOWN))
		{
			if(isStartSelected){
			isStartSelected = false;
			choose.play();
			}
		}
		if(input.isKeyPressed(Input.KEY_UP))
		{
			if(!isStartSelected){
			isStartSelected = true;
			choose.play();
			}
		}
		// Enters or exits game based on selection
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			if(!isStartSelected)
				container.exit();
			else{
				selected.play();
				game.enterState(1, new FadeOutTransition(Color.black), new EmptyTransition());
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
