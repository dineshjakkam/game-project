package comp102x.project.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import comp102x.Canvas;
import comp102x.CanvasObject;
import comp102x.ColorImage;
import comp102x.project.control.GameEngine;
import comp102x.project.control.ThreadPool;
import comp102x.project.task.AimListener;
import comp102x.project.task.ScreenTransition;

public class GameScreen implements MouseListener, MouseMotionListener, KeyListener {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private Canvas canvas;
	private GameEngine gameEngine;
	private View currentView;
	private ColorImage transitionImage;
	
	private AimListener aimListener;
	private Method mouseMovedMethod;
	private Method getTiltMethod;
	private Method getPanMethod;
	
	public GameScreen() {

		canvas = new Canvas(WIDTH, HEIGHT);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		
		ThreadPool.run(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					canvas.getJFrame().addKeyListener(GameScreen.this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		transitionImage = new ColorImage(WIDTH, HEIGHT);
		transitionImage.setMovable(false);

		resetTransitionImage();
		aimListener = new AimListener();
		
		if (aimListener instanceof java.awt.event.MouseMotionListener) {

			try {
				mouseMovedMethod = aimListener.getClass().getMethod(
						"mouseMoved", new Class[] { MouseEvent.class });
			} catch (NoSuchMethodException e) {
				System.out
						.println("no mouseMoved() method in the AimListener class");
			} catch (SecurityException e) {
				e.printStackTrace();
			}

			try {
				getTiltMethod = aimListener.getClass().getMethod("getTilt");
			} catch (NoSuchMethodException e) {
				System.out
						.println("no getTilt() method in the AimListener class");
			} catch (SecurityException e) {
				e.printStackTrace();
			}

			try {
				getPanMethod = aimListener.getClass().getMethod("getPan");
			} catch (NoSuchMethodException e) {
				System.out
						.println("no getPan() method in the AimListener class");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public void setGameEngine(GameEngine gameEngine) {

		this.gameEngine = gameEngine;
	}

	public void showView(View view) {

		currentView = view;

		canvas.removeAll();
		for (CanvasObject co : currentView.getElements())
			canvas.add(co);
	}

	public void transitToView(View view) {

		canvas.add(transitionImage);
		ScreenTransition task1 = new ScreenTransition();
		task1.changeScreen(transitionImage, 0, 0, transitionImage.getWidth(), transitionImage.getHeight(), 50);
		
		pause(500);

		showView(view);
		resetTransitionImage();
	}

	private void resetTransitionImage() {

		for (int i = 0; i < transitionImage.getWidth(); i++)
			for (int j = 0; j < transitionImage.getHeight(); j++) {
				transitionImage.setRGB(i, j, 0, 0, 0);
				transitionImage.setAlpha(i, j, 0);
			}
	}

	private void pause(long milliseconds) {
		
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(final MouseEvent e) {

		ThreadPool.run(new Runnable() {

			@Override
			public void run() {
				currentView.performClickAction(e.getX(), e.getY(), gameEngine);
			}
		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		ThreadPool.run(new Runnable() {
			
			@Override
			public void run() {
				if (gameEngine != null)
				gameEngine.shoot();
			}
		});
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		if (mouseMovedMethod == null || getTiltMethod == null || getPanMethod == null) return;

		try {
			
			mouseMovedMethod.invoke(aimListener, e);
			final double tilt = (double) getTiltMethod.invoke(aimListener);
			final double pan = (double) getPanMethod.invoke(aimListener);

			ThreadPool.run(new Runnable() {

				@Override
				public void run() {
					if (gameEngine != null)
						gameEngine.aim(tilt, pan);
				}

			});
			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {

			e1.printStackTrace();
		}

	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		final char key = e.getKeyChar();
		
		ThreadPool.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				currentView.performKeyAction(key);
			}
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
