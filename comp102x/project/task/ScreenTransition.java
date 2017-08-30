package comp102x.project.task;

import comp102x.ColorImage;

public class ScreenTransition {
    
    public void changeScreen(ColorImage image, int x, int y, int width, int height,
			int stripeSize) {
	
    	// Please write your code after this line
		ScreenTransition lab03Task1= new ScreenTransition();
    lab03Task1.animateStripe(image,x,y,width,stripeSize,"toRight");
    lab03Task1.animateStripe(image,width-stripeSize,y+stripeSize,height-stripeSize,stripeSize,"toBottom");
    lab03Task1.animateStripe(image,x,height-stripeSize,width-stripeSize,stripeSize,"toLeft");
    lab03Task1.animateStripe(image,x,y+stripeSize,height-2*stripeSize,stripeSize,"toTop");
    //firstUpdate=false;
    x = x+stripeSize; y=y+stripeSize;
    width=width-stripeSize;height=height-stripeSize;
    if( y+2*stripeSize < height-2*stripeSize && x+2*stripeSize < width - 2*stripeSize)
    {
    lab03Task1.changeScreen(image, x, y, width, height, stripeSize);
    }
    else
    lab03Task1.animateStripe(image,x,y,width,height,"toRight");
    }
    
    private void animateStripe(ColorImage image, int left, int top,
			int length, int stripeSize, String direction) {

		long delay = (long) Math.pow(10, 5);
		
		switch (direction) {

		case "toRight":
			for (int i = 0; i < length; i++) {
				image.drawRectangle(left + i, top, 1, stripeSize);
				pauseByTicks(delay);
			}
			break;

		case "toBottom":
			for (int i = 0; i < length; i++) {
				image.drawRectangle(left, top + i, stripeSize, 1);
				pauseByTicks(delay);
			}
			break;

		case "toLeft":
			for (int i = length - 1; i >= 0; i--) {
				image.drawRectangle(left + i, top, 1, stripeSize);
				pauseByTicks(delay);
			}
			break;

		case "toTop":
			for (int i = length - 1; i >= 0; i--) {
				image.drawRectangle(left, top + i, stripeSize, 1);
				pauseByTicks(delay);
			}
			break;

		default:
			System.err.println("Invalid direction: " + direction);
			System.err.println("Only \"toRight\", \"toBottom\", \"toLeft\", \"toTop\" are allowed!");
		}
	}

	private void pauseByTicks(long ticks) {
		while (ticks != 0) {
			ticks--;
		}
	}

}
