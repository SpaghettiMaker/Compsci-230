// Name: XunFan Zhou
// UPI: xzho684

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class MovingChecker extends MovingRectangle {
	protected int xNumBlock;
	protected int yNumBlock;
	protected Point tempPoint;
	public MovingChecker() { 
	}
	
    public MovingChecker(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, int pathType) {
        topLeft = new Point(x,y);
        width = w;
        height = h;
        marginWidth = mw;
        marginHeight = mh;
        borderColor = bc;
        fillColor = fc;
        setPath(pathType);
        Random rand = new Random();
        xNumBlock = rand.nextInt(11 - 2 + 1) + 2; // min is inclusive  
        yNumBlock = rand.nextInt(11 - 2 + 1) + 2; // rand.nextInt((max - min) + 1) + min;
        tempPoint = new Point(topLeft.x, topLeft.y);
    }

	public void draw(Graphics g) {
		// draw evenly but there will be remainder so just 
		// draw the uneven ones at the bottom and to the right side
		tempPoint.x = topLeft.x;
		tempPoint.y = topLeft.y;
		int sizeWidth = width / xNumBlock;
		int sizeHeight = height / yNumBlock;

		int spare_width = width - ((xNumBlock - 1) * sizeWidth);
		int spareHeight = height - ((yNumBlock - 1) * sizeHeight);
		//System.out.printf("no.: %d size_width: %d size_height: %d spare_width: %d spare_height: %d\n", xNumBlock, 
		//		size_width, size_height, spare_width, spare_height);
		
		int startColour = 0;
		int currentColour = 0;
		int otherHeight = sizeHeight;
		
		for (int i = yNumBlock ; i > 0 ; i--) {
			if (i == 1) {
				otherHeight = spareHeight;
			}
			// always draw an alternating colour independent of row
			if (startColour == 1) {
				startColour = 0;
				g.setColor(borderColor);
				currentColour = 0;
			} else {
				startColour = 1;
				g.setColor(fillColor);
				currentColour = 1;
			}
			for (int j = xNumBlock; j > 1 ; j--) {
				g.fillRect(tempPoint.x, tempPoint.y, sizeWidth, otherHeight);
				tempPoint.x = tempPoint.x + sizeWidth;
				if (currentColour == 0) {
					currentColour = 1;
					g.setColor(fillColor);
				} else {
					currentColour = 0;
					g.setColor(borderColor);
				}
			}
			g.fillRect(tempPoint.x, tempPoint.y, spare_width, otherHeight);
			tempPoint.y += sizeHeight;
			tempPoint.x = topLeft.x;
			drawHandles(g);
		}
	}
}
