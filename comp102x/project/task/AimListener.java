package comp102x.project.task;

//import comp102x.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import comp102x.project.view.GameScreen;

public class AimListener implements MouseMotionListener{
    
private double pan;
private double tilt;

public AimListener() {
}

public double getPan()
{
    return pan;
}

public double getTilt()
{
    return tilt;
}


public void mouseMoved(MouseEvent e)
{
    pan = (1.0*e.getX()/GameScreen.WIDTH)*180-90;
    tilt = (1.0*e.getY()/GameScreen.HEIGHT)*90;
}

public void mouseDragged(MouseEvent e) {}

}
    
