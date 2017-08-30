package comp102x.project.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import comp102x.CanvasObject;
import comp102x.ColorImage;
import comp102x.Text;
import comp102x.project.control.GameEngine;
import comp102x.project.model.GameRecord;

public class HighscoreView extends View {

	private ColorImage backgroundImage;
	private ColorImage playAgainButton;
	private ColorImage overlay;
	private ColorImage recordHighlightNew;
	private ColorImage recordHighlightCurrent;
	
	private Text[][] recordTexts;
	
	private int yMargin = 35;
	private int maxRecordCount = 10;

	public HighscoreView() {

		backgroundImage = new ColorImage("images/background.png");
		int blurIntensity = 20;
		float[][] kernel = { { 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 },
				{ 1.0f / 9, 1.0f / 9, 1.0f / 9 }, };

		for (int i = 0; i < blurIntensity; i++)
			backgroundImage.convolve(kernel);
		
		backgroundImage.setMovable(false);

		playAgainButton = new ColorImage("images/playAgain.png");
		playAgainButton
				.setX((GameScreen.WIDTH - playAgainButton.getWidth()) / 2);
		playAgainButton
				.setY((GameScreen.HEIGHT - playAgainButton.getHeight()) / 2);
		playAgainButton.setMovable(false);
		
		overlay = new ColorImage(GameScreen.WIDTH, GameScreen.HEIGHT);
		for (int i = 0; i < overlay.getWidth(); i++)
			for (int j = 0; j < overlay.getHeight(); j++) {
				overlay.setRGB(i, j, 32, 32, 32);
				overlay.setAlpha(i, j, 128);
			}
		overlay.setMovable(false);
		
		recordHighlightNew = new ColorImage(GameScreen.WIDTH, yMargin);
		for (int i = 0; i < recordHighlightNew.getWidth(); i++)
			for (int j = 0; j < recordHighlightNew.getHeight(); j++) {
				recordHighlightNew.setRGB(i, j, 255, 0, 0);
				recordHighlightNew.setAlpha(i, j, 128);
			}
		recordHighlightNew.setMovable(false);
		
		recordHighlightCurrent = new ColorImage(GameScreen.WIDTH, yMargin);
		for (int i = 0; i < recordHighlightCurrent.getWidth(); i++)
			for (int j = 0; j < recordHighlightCurrent.getHeight(); j++) {
				recordHighlightCurrent.setRGB(i, j, 0, 255, 0);
				recordHighlightCurrent.setAlpha(i, j, 128);
			}
		recordHighlightCurrent.setMovable(false);
		
		elements.add(backgroundImage);
		elements.add(overlay);
		elements.add(recordHighlightNew);
		elements.add(recordHighlightCurrent);
		elements.add(playAgainButton);
		
		int lines = maxRecordCount + 4;
		
		recordTexts = new Text[lines][3];
		
		for (int i = 0; i < lines; i++) {
			
			recordTexts[i] = new Text[3];
					
			int y = (i + 1) * yMargin;
			
			for (int j = 0; j < 3; j++) {
				
				int x;
				
				if (j == 0) x = 80;
				else if (j == 1) x = 520;
				else x = 620;
				
				recordTexts[i][j] = new Text("");
				recordTexts[i][j].setColor(255, 255, 0);
				recordTexts[i][j].setX(x);
				recordTexts[i][j].setY(y);
				recordTexts[i][j].setMovable(false);
				elements.add(recordTexts[i][j]);
			}
		}
		
		recordTexts[0][0].setText("Player Name");
		recordTexts[0][1].setText(String.format("%10s", "Level"));
		recordTexts[0][2].setText(String.format("%10s", "Score"));
		
		recordTexts[1][0].setText("------------------------------------------------------------------------");
		recordTexts[lines - 2][0].setText("------------------------------------------------------------------------");
	}
	
	private void resetRecordTexts() {
		
		for (int i = 0; i < maxRecordCount; i++) {
			for (int j = 0; j < 3; j++) {
				recordTexts[i + 2][j].setText("");
			}
		}
	}

	public void setRecordTexts(GameRecord[] records, GameRecord newRecord) {
		
		if (records == null) {
			return;
		}
		
		resetRecordTexts();
		
		String name;
		String level;
		String score;
		
		recordHighlightNew.setScale(0.0);
		
		for (int i = 0; i < records.length; i++) {
			
			name = String.format("%s", records[i].getName());
			level = String.format("%10s", records[i].getLevel());
			score = String.format("%10s", records[i].getScore());
			
			recordTexts[i + 2][0].setText(name);
			recordTexts[i + 2][1].setText(level);
			recordTexts[i + 2][2].setText(score);
			
			if (newRecord.getName().equals(records[i].getName()) && newRecord.getScore() == records[i].getScore()) {
				recordHighlightNew.setScale(1.0);
				recordHighlightNew.setY(recordTexts[i + 2][0].getY() + 10);
			}	
		}
		
		name = String.format("%s", newRecord.getName());
		level = String.format("%10s", newRecord.getLevel());
		score = String.format("%10s", newRecord.getScore());
		
		recordTexts[maxRecordCount + 4 - 1][0].setText(name);
		recordTexts[maxRecordCount + 4 - 1][1].setText(level);
		recordTexts[maxRecordCount + 4 - 1][2].setText(score);
		recordHighlightCurrent.setY(recordTexts[maxRecordCount + 4 - 1][0].getY() + 10);
	}

	@Override
	public void performClickAction(int x, int y, GameEngine gameEngine) {
		// TODO Auto-generated method stub
		for (CanvasObject co : elements) {

			int lowerXBound = co.getX();
			int upperXBound = co.getX() + co.getWidth();
			int lowerYBound = co.getY();
			int upperYBound = co.getY() + co.getHeight();

			if (x >= lowerXBound && x <= upperXBound && y >= lowerYBound
					&& y <= upperYBound) {

				if (co == playAgainButton) {
					gameEngine.elementClicked();
					gameEngine.startGame();
					break;
				}

			}
		}
	}

	@Override
	public void performKeyAction(char key) {
		
	}
}
