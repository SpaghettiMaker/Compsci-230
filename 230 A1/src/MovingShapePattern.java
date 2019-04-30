// Name: XunFan Zhou
// UPI: xzho684

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.Math;

public class MovingShapePattern extends MovingOval {
	public MovingShapePattern() { 
	}
	
    public MovingShapePattern(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, int pathType) {
        topLeft = new Point(x,y);
        width = w;
        height = h;
        marginWidth = mw;
        marginHeight = mh;
        borderColor = bc;
        fillColor = fc;
        setPath(pathType);
    }

	public void draw(Graphics g) {
		// draws a oval then within the oval draw a maximal rectangle
		g.setColor(borderColor);
		g.fillOval(topLeft.x, topLeft.y , width, height);
		int squarewidth = (int) ((width / 2) * Math.sqrt(2));
		int squareheight = (int) ((height / 2) * Math.sqrt(2));
		g.setColor(fillColor);
		g.fillRect(topLeft.x + (int)(squarewidth / 5) + 1, topLeft.y + (int)(squareheight / 5) + 1, squarewidth, squareheight);
		drawHandles(g);
	}
}
