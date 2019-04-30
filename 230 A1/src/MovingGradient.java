// Name: XunFan Zhou
// UPI: xzho684

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class MovingGradient extends MovingRectangle {
	protected GradientPaint gradient;
	
	public MovingGradient() { 
	}
	
    public MovingGradient(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, int pathType) {
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
		Graphics2D g2 = (Graphics2D) g;
		gradient = new GradientPaint(topLeft.x, topLeft.y, fillColor, topLeft.x + width, topLeft.y + height, borderColor);
		g2.setPaint(gradient);
		g2.fillRect(topLeft.x, topLeft.y, width, height);
		drawHandles(g2);
	}

}
