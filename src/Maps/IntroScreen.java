package Maps;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class IntroScreen extends BasicGameState{

	private Image startSelected, startUnselected, quitSelected, quitUnselected, backgroundScreen;
	private Sound selected, choose;
	private boolean isStartSelected = true;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		setupContainer(container);
	}

	private void setupContainer(GameContainer container) throws SlickException {
		container.setTargetFrameRate(60);
		container.setUpdateOnlyWhenVisible(false);
		startSelected = new Image("resources/images/introscreen/START_selected.png");
		startUnselected = new Image("resources/images/introscreen/START_unselected.png");
		quitSelected = new Image("resources/images/introscreen/QUIT_selected.png");
		quitUnselected = new Image("resources/images/introscreen/QUIT_unselected.png");
		backgroundScreen = new Image("resources/images/introscreen/startScreen.png");
		choose = new Sound("resources/sounds/introScreen/choose.wav");
		selected = new Sound("resources/sounds/introScreen/selected.wav");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		drawScreenElements();
	}

	private void drawScreenElements() {
		backgroundScreen.draw();

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
		handleInput(container, game);
	}

	private void handleInput(GameContainer container, StateBasedGame game) {
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
		return 0;
	}

}
