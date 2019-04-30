// Name: XunFan Zhou
// UPI: xzho684

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class MovingOval extends MovingShape {
	public MovingOval() { 
	}
	
    public MovingOval(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, int pathType) {
        topLeft = new Point(x,y);
        width = w;
        height = h;
        marginWidth = mw;
        marginHeight = mh;
        borderColor = bc;
        fillColor = fc;
        setPath(pathType);
    }

	public boolean contains(Point p) {
		Point EndPt = new Point(topLeft.x + width, topLeft.y + height);
		double dx = (2 * p.x - topLeft.x - EndPt.x) / (double) width;
		double dy = (2 * p.y - topLeft.y - EndPt.y) / (double) height;
		return dx * dx + dy * dy < 1.0; 
	}

	public void draw(Graphics g) {
		g.setColor(fillColor);
		g.fillOval(topLeft.x, topLeft.y , width, height);
		g.setColor(borderColor);
		g.drawOval(topLeft.x, topLeft.y, width, height);
		drawHandles(g);
	}
}
