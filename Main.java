import comp102x.project.control.GameEngine;
import comp102x.project.model.GameModel;
import comp102x.project.task.AimListener;
import comp102x.project.view.GameScreen;
import comp102x.project.view.PlayGameView;
import comp102x.project.view.StartGameView;

public class Main {

	public static void main(String[] args) {

		try {
			GameScreen gs = new GameScreen();
			GameModel gm = new GameModel();
			GameEngine ge = new GameEngine(gs, gm);
			
			ge.startGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
