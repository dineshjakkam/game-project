package comp102x.project.view;

import java.util.ArrayList;
import java.util.List;

import comp102x.CanvasObject;
import comp102x.project.control.GameEngine;

public abstract class View {

	protected List<CanvasObject> elements;

	public View() {
		elements = new ArrayList<CanvasObject>();
	}

	public List<CanvasObject> getElements() {
		return elements;
	}

	public abstract void performClickAction(int x, int y, GameEngine gameEngine);
	public abstract void performKeyAction(char key);
}
