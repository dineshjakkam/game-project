package comp102x.project.view;

import comp102x.ColorImage;
import comp102x.Text;
import comp102x.project.control.GameEngine;
import comp102x.project.control.ThreadPool;
import comp102x.project.task.CharValidator;

public class EndGameView extends View {

	private ColorImage backgroundImage;
	private ColorImage overlay;
	private Text messageText;
	private Text nameText;
	private String name;

	private GameEngine gameEngine;

	public EndGameView() {

		name = "";

		backgroundImage = new ColorImage("images/background.png");
		int blurIntensity = 20;
		float[][] kernel = { { 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 }, };

		for (int i = 0; i < blurIntensity; i++)
			backgroundImage.convolve(kernel);

		backgroundImage.setMovable(false);
		
		overlay = new ColorImage(GameScreen.WIDTH * 3 / 4, 100);
		overlay.setMovable(false);
		overlay.setX(GameScreen.WIDTH / 8);
		overlay.setY(GameScreen.HEIGHT / 2 + 10);
		
		for (int i = 0; i < overlay.getWidth(); i++)
			for (int j = 0; j < overlay.getHeight(); j++) {
				overlay.setRGB(i, j, 32, 32, 32);
				overlay.setAlpha(i, j, 128);
			}
				

		messageText = new Text("Enter player's name:");
		messageText.setSize(60);
		messageText.setColor(157, 63, 255);
		messageText.setX(GameScreen.WIDTH / 2 - 544 / 2);
		messageText.setY(120);

		nameText = new Text("");
		nameText.setSize(60);
		nameText.setColor(255, 255, 255);
		nameText.setX(GameScreen.WIDTH / 2);
		nameText.setY(messageText.getY() + 180);

		elements.add(backgroundImage);
		elements.add(messageText);
		elements.add(overlay);
		elements.add(nameText);

	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void performClickAction(int x, int y, GameEngine gameEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performKeyAction(final char key) {

		ThreadPool.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int keyCode = (int) key;
				
				if (keyCode == 10 || keyCode == 13) {
									
					gameEngine.showHighScore();
					return;
				}

				boolean valid = new CharValidator().validateChar(key);

				if (valid && name.length() < 12) {
					name += key;
					nameText.setText(name);
					nameText.setX(GameScreen.WIDTH / 2 - nameText.getWidth() / 2 - 15);
					gameEngine.characterTyped();
					gameEngine.setPlayerName(name);
					
				} else if (keyCode == 8) {
					name = name.substring(0, name.length() - 1);
					nameText.setText(name);
					nameText.setX(GameScreen.WIDTH / 2 - nameText.getWidth() / 2 + 1);
					gameEngine.characterTyped();
					gameEngine.setPlayerName(name);
				}
			}
		});
	
	}

	public void reset() {
		// TODO Auto-generated method stub
		name = "";
		nameText.setText(name);
		nameText.setX(GameScreen.WIDTH / 2);
	}

}
