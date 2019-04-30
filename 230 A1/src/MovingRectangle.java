// Name: XunFan Zhou
// UPI: xzho684

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class MovingRectangle extends MovingShape {
	public MovingRectangle() { 
	}
	
    public MovingRectangle(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, int pathType) {
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
		return ((p.x >= topLeft.x) && (p.y >= topLeft.y)) && 
				((p.x <= (topLeft.x + width)) && (p.y <= (topLeft.y + height)));
	}

	public void draw(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(topLeft.x, topLeft.y, width, height);
		g.setColor(borderColor);
		g.drawRect(topLeft.x, topLeft.y, width, height);
		drawHandles(g);
	}
}
