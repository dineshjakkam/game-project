package comp102x.project.view;

import comp102x.CanvasObject;
import comp102x.ColorImage;
import comp102x.Text;
import comp102x.project.control.GameEngine;

public class StartGameView extends View {

	private ColorImage title;
	private ColorImage startButton;
	private ColorImage backgroundImage;
	private ColorImage levelOneButton;
	private ColorImage levelTwoButton;
	private ColorImage levelThreeButton;

	public StartGameView() {
		
		title = new ColorImage("images/title.png");
		startButton = new ColorImage("images/start.png");
		backgroundImage = new ColorImage("images/background.png");
		levelOneButton = new ColorImage("images/level1.png");
		levelTwoButton = new ColorImage("images/level2.png");
		levelThreeButton = new ColorImage("images/level3.png");

		elements.add(backgroundImage); // must be added first
		elements.add(title);
		elements.add(startButton);
		elements.add(levelOneButton);
		elements.add(levelTwoButton);
		elements.add(levelThreeButton);

		initElements();
	}

	private void initElements() {

		// set not movable
		for (CanvasObject co : elements)
			co.setMovable(false);

		// set positions
		int yMargin = 0;
		yMargin += title.getHeight();
		yMargin += startButton.getHeight();
		yMargin += levelOneButton.getHeight();
		yMargin = GameScreen.HEIGHT - yMargin;
		yMargin /= 4;

		title.setX((GameScreen.WIDTH - title.getWidth()) / 2);
		title.setY(yMargin);

		startButton.setX((GameScreen.WIDTH - startButton.getWidth()) / 2);
		startButton.setY(title.getHeight() + 2 * yMargin);

		int xMargin = (GameScreen.WIDTH - 3 * levelOneButton.getWidth()) / 4;
		yMargin = title.getHeight() + startButton.getHeight() + 3 * yMargin;

		levelOneButton.setX(xMargin);
		levelOneButton.setY(yMargin);

		levelTwoButton.setX(levelOneButton.getWidth() + 2 * xMargin);
		levelTwoButton.setY(yMargin);

		levelThreeButton.setX(levelOneButton.getWidth() * 2 + 3 * xMargin);
		levelThreeButton.setY(yMargin);

		int blurIntensity = 20;

		// add visual effects
		float[][] kernel = { { 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 }, };

		for (int i = 0; i < blurIntensity; i++)
			backgroundImage.convolve(kernel);

		setImageAlpha(levelTwoButton, 64);
		setImageAlpha(levelThreeButton, 64);
		
	}
	
	public void reset() {
		
		setImageAlpha(levelOneButton, 255);
		setImageAlpha(levelTwoButton, 64);
		setImageAlpha(levelThreeButton, 64);
	}

	public void performClickAction(int x, int y, GameEngine gameEngine) {

		for (CanvasObject co : elements) {

			int lowerXBound = co.getX();
			int upperXBound = co.getX() + co.getWidth();
			int lowerYBound = co.getY();
			int upperYBound = co.getY() + co.getHeight();

			if (x >= lowerXBound && x <= upperXBound && y >= lowerYBound
					&& y <= upperYBound) {

				if (co == startButton) {
					gameEngine.elementClicked();
					gameEngine.playGame();
					break;
				}

				else if (co == levelOneButton) {

					setImageAlpha(levelOneButton, 255);
					setImageAlpha(levelTwoButton, 64);
					setImageAlpha(levelThreeButton, 64);
					gameEngine.elementClicked();
					gameEngine.chooseLevel(1);
					break;
				}

				else if (co == levelTwoButton) {

					setImageAlpha(levelOneButton, 64);
					setImageAlpha(levelTwoButton, 255);
					setImageAlpha(levelThreeButton, 64);
					gameEngine.elementClicked();
					gameEngine.chooseLevel(2);
					break;
				}

				else if (co == levelThreeButton) {

					setImageAlpha(levelOneButton, 64);
					setImageAlpha(levelTwoButton, 64);
					setImageAlpha(levelThreeButton, 255);
					gameEngine.elementClicked();
					gameEngine.chooseLevel(3);
					break;
				}
			}
		}

	}
	
	@Override
	public void performKeyAction(char key) {
		// TODO Auto-generated method stub
		
	}

	private void setImageAlpha(ColorImage image, int alphaValue) {

		for (int i = 0; i < image.getWidth(); i++)
			for (int j = 0; j < image.getHeight(); j++)
				if (image.getAlpha(i, j) != 0)
					image.setAlpha(i, j, alphaValue);
	}

}
