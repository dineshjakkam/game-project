package comp102x.project.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.Timer;

import comp102x.Canvas;
import comp102x.ColorImage;
import comp102x.project.model.Bullet;
import comp102x.project.model.GameModel;
import comp102x.project.model.GameRecord;
import comp102x.project.model.PerspectiveProjection;
import comp102x.project.model.Target;
import comp102x.project.model.Trajectory;
import comp102x.project.task.RecordManager;
import comp102x.project.task.SaveLoader;
import comp102x.project.view.EndGameView;
import comp102x.project.view.GameScreen;
import comp102x.project.view.HighscoreView;
import comp102x.project.view.PlayGameView;
import comp102x.project.view.StartGameView;

public class GameEngine {

	private GameScreen gameScreen;
	private GameModel gameModel;
	private PerspectiveProjection perspectiveProjection;
	private Timer gameTimer;
	private Timer targetTimer;

	private Runnable animateShooting;

	private StartGameView startGameView;
	private PlayGameView playGameView;
	private EndGameView endGameView;
	private HighscoreView highscoreView;
	private GameRecord[] records;
	
	private SoundEngine soundEngine;
	private static final int BGM = 0;
	private static final int CLICK = 1;
	private static final int TYPE = 2;
	private static final int SHOOT = 3;
	private static final int EXPLODE = 4;
	private static final int END = 5;
	
	private boolean hideCursor = false;

	public GameEngine(final GameScreen gameScreen, final GameModel gameModel) {

		this.gameScreen = gameScreen;
		this.gameScreen.setGameEngine(this);
		this.gameModel = gameModel;

		this.perspectiveProjection = new PerspectiveProjection();

		startGameView = new StartGameView();
		playGameView = new PlayGameView();
		highscoreView = new HighscoreView();

		endGameView = new EndGameView();
		endGameView.setGameEngine(this);
		
		soundEngine = new SoundEngine();
		loadSounds();

		GameActionListener listener = new GameActionListener();

		gameTimer = new Timer(1000, listener);
		gameTimer.setActionCommand("decrement time");

		targetTimer = new Timer(1000, listener);
		targetTimer.setActionCommand("update targets");

		animateShooting = new Runnable() {

			@Override
			public void run() {

				gameModel.setState(GameModel.SHOOTING);
				Trajectory trajectory = gameModel.getTrajectory();
				int ticks = (int) trajectory.getTotalT();

				int x = gameModel.getBullet().getX();
				int y = gameModel.getBullet().getY();
				int z = gameModel.getBullet().getZ();

				for (int t = 0; t < ticks; t++) {

					gameModel.getBullet().setX(x + (int) trajectory.getX(t));
					gameModel.getBullet().setY(y + (int) trajectory.getY(t));
					gameModel.getBullet().setZ(z + (int) trajectory.getZ(t));

					renderBullet();

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (gameModel.getState() != GameModel.SHOOTING)
					return;

				checkHit();

				gameScreen.showView(playGameView);
				gameModel.setState(GameModel.PLAYING);
				gameModel.getBullet().reset();

				renderBullet();
				playGameView.updateAim(gameModel.getBullet(),
						perspectiveProjection, gameModel.getTrajectory());

				if (gameModel.getHitCount() == gameModel.getTargets().length) {

					endGame();
				}
			}
		};

	}

	public void startGame() {

		startGameView.reset();
		gameScreen.showView(startGameView);
		gameModel.reset();
	}

	public void playGame() {

		toggleCursor();
		playGameView.loadView(gameModel.getTargets(), gameModel.getBullet());
		playGameView.setHitCount(gameModel.getHitCount());
		playGameView.setTime(gameModel.getRemainingTime());

		renderTargets();
		renderBullet();

		gameScreen.transitToView(playGameView);
		gameTimer.start();
		
		targetTimer.setDelay(gameModel.getUpdateTargetDelay());
		targetTimer.start();
		
		soundEngine.playSound(BGM);
		gameModel.setState(GameModel.PLAYING);
	}

	public synchronized void endGame() {
		
		gameTimer.stop();
		targetTimer.stop();
		gameModel.setState(GameModel.END);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		toggleCursor();
		
		soundEngine.stopAll();
		soundEngine.playSound(END);
		endGameView.reset();
		gameScreen.transitToView(endGameView);
	}

	private void renderBullet() {

		Bullet bullet = gameModel.getBullet();
		double[] coordinates = perspectiveProjection.translate(bullet.getX(),
				bullet.getY(), bullet.getZ());
		double scale = perspectiveProjection.getScale(bullet.getZ());

		int x = (int) (coordinates[0] - bullet.getImage().getWidth() * scale
				/ 2);
		int y = (int) (coordinates[1] - bullet.getImage().getHeight() * scale
				/ 2);

		bullet.getImage().setX(x);
		bullet.getImage().setY(y);
		bullet.getImage().setScale(scale);
	}

	private void renderTargets() {

		Target[] targets = gameModel.getTargets();

		for (Target target : targets) {

			double[] coordinates = perspectiveProjection.translate(
					target.getX(), target.getY(), target.getZ());
			double scale = perspectiveProjection.getScale(target.getZ());

			int x = (int) (coordinates[0] - target.getImage().getWidth()
					* scale / 2);
			int y = (int) (coordinates[1] - target.getImage().getHeight()
					* scale / 2);

			target.getImage().setX(x);
			target.getImage().setY(y);

			if (!target.isHit())
				target.getImage().setScale(scale);
			else
				target.getImage().setScale(0.0);
		}
	}

	private void toggleCursor() {

		Canvas canvas = gameScreen.getCanvas();
		JFrame frame = canvas.getJFrame();

		if (!hideCursor) {

			BufferedImage cursorImg = new BufferedImage(16, 16,
					BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit()
					.createCustomCursor(cursorImg, new Point(0, 0),
							"blank cursor");

			// Set the blank cursor to the JFrame.
			frame.getContentPane().setCursor(blankCursor);

			hideCursor = true;

		} else {
			frame.getContentPane().setCursor(Cursor.getDefaultCursor());
			hideCursor = false;
		}
	}
	
	public void elementClicked() {
		
		soundEngine.playSound(CLICK);
	}
	
	public void characterTyped() {
		
		soundEngine.playSound(TYPE);
	}

	public void chooseLevel(int level) {

		gameModel.setLevel(level);
	}

	public void setPlayerName(String playerName) {

		gameModel.setPlayerName(playerName);
	}

	public void aim(double tilt, double pan) {

		if (gameModel.getState() == GameModel.START
				|| gameModel.getState() == GameModel.PLAYING) {
			
			int originalState = gameModel.getState();
			
			gameModel.setState(GameModel.AIMING);
			gameModel.getTrajectory().setTiltAngle(tilt);
			gameModel.getTrajectory().setPanAngle(pan);

			playGameView.updateAim(gameModel.getBullet(),
					perspectiveProjection, gameModel.getTrajectory());
			gameModel.setState(originalState);
		}
	}

	public void shoot() {

		if (gameModel.getState() != GameModel.PLAYING)
			return;
		
		soundEngine.playSound(SHOOT);
		ThreadPool.run(animateShooting);
	}

	public void countDown() {

		gameModel.setRemainingTime(gameModel.getRemainingTime() - 1);
		playGameView.setTime(gameModel.getRemainingTime());

		if (gameModel.getRemainingTime() == 0) {
			endGame();
		}
	}

	public void updateTargets() {

		gameModel.updateTargetPositions();
		renderTargets();
	}

	private void checkHit() {

		final Target[] targets = gameModel.getTargets();
		Bullet bullet = gameModel.getBullet();

		for (int i = 0; i < targets.length; i++) {

			if (overlaps(bullet, targets[i])) {

				ColorImage oldImage = targets[i].getImage();
				targets[i].setHit(true);
				playGameView.updateElement(oldImage, targets[i].getImage());
				soundEngine.playSound(EXPLODE);

				final Target target = targets[i];
				ThreadPool.run(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						target.getImage().setScale(0.0);
					}
				});

				gameModel.setHitCount(gameModel.getHitCount() + 1);
				playGameView.setHitCount(gameModel.getHitCount());
				break;
			}
		}
	}

	private boolean overlaps(Bullet bullet, Target target) {

		ColorImage image1 = bullet.getImage();
		Rectangle r1 = new Rectangle(image1.getX(), image1.getY(),
				(int) (image1.getWidth() * image1.getScale()),
				(int) (image1.getHeight() * image1.getScale()));

		ColorImage image2 = target.getImage();
		Rectangle r2 = new Rectangle(image2.getX(), image2.getY(),
				(int) (image2.getWidth() * image2.getScale()),
				(int) (image2.getHeight() * image2.getScale()));

		int threshold = 5000;

		return r1.intersects(r2)
				&& Math.abs(bullet.getZ() - target.getZ()) < threshold;
	}

	public void showHighScore() {
		
		SaveLoader sld = new SaveLoader();
		
		try {
			if (records == null) {
				records = sld.loadGameRecords("highscore.txt");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot load highscore.txt");
		}
		
		String name = gameModel.getPlayerName();
		int level = gameModel.getLevel();
		int score = gameModel.getScore();
		
		GameRecord newRecord = new GameRecord(name, level, score);
		records = new RecordManager().updateGameRecords(records, newRecord);
		
		int count = 0;
		for (int i = 0; i < records.length; i++) {
			if (records[i].getLevel() == newRecord.getLevel())
				count++;
		}
		
		GameRecord[] recordsOfSameLevel = new GameRecord[count];
		for (int i = 0, j = 0; i < records.length; i++) {
			if (records[i].getLevel() == newRecord.getLevel()) {
				recordsOfSameLevel[j] = records[i];
				j++;
			}
		}
		
		highscoreView.setRecordTexts(recordsOfSameLevel, newRecord);
		
		try {
			if (records != null) {
				sld.saveGameRecords(records, "highscore.txt");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot save highscore.txt");
		}
		
		gameScreen.showView(highscoreView);
	}

	private class GameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {

			case "update targets":
				ThreadPool.run(new Runnable() {

					@Override
					public void run() {
						GameEngine.this.updateTargets();
					}
				});
				break;

			case "decrement time":
				ThreadPool.run(new Runnable() {

					@Override
					public void run() {
						GameEngine.this.countDown();
					}
				});
				break;

			}
		}
	}
	
	private void loadSounds() {
		
		soundEngine.loadSound("sounds/bgm.mp3", BGM);
		soundEngine.loadSound("sounds/click.mp3", CLICK);
		soundEngine.loadSound("sounds/type.mp3", TYPE);
		soundEngine.loadSound("sounds/shoot.mp3", SHOOT);
		soundEngine.loadSound("sounds/explode.mp3", EXPLODE);
		soundEngine.loadSound("sounds/end.mp3", END);		
	}

}
