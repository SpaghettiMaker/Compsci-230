// Name: XunFan Zhou
// UPI: xzho684

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.String;
import java.util.ArrayList;

public class Graph extends JPanel {
	private Point topLeft;
	private Color bgColor;
	private static String currentSelectedGraph;
	private static boolean currentSelectedType;
	
	private int MAXTICKSY, XLENGTH, YHEIGHT, INTERVAL;
	private Point start;
	private ArrayList<Double> dataPoints;
	private Double maxDataSize;
	private int defaultkB;
	private String volumeLabel;
	private String timeLabel;
	private Font f1, f2;
	/**
	 * Initializes the Graph and sets all data necessary for plotting calculations
	 * @param x x coordinate topleft point of graph
	 * @param y y coordinate topleft point of graph
	 * @param width width of graph
	 * @param height height of graph
	 * @param color background color of graph
	 */
	public Graph(int x, int y, int width, int height, Color color) {
		this.bgColor = color;
		topLeft = new Point(x, y);
		setSize(width, height);
		maxDataSize = 0.0;
		MAXTICKSY = 8;
		INTERVAL = 2;
		XLENGTH = 900;
		YHEIGHT = 272;
		volumeLabel = "Volume [bytes]";
		timeLabel = "Time [s]";
		start = new Point(50, 32);
		setBounds(topLeft.x, topLeft.y, width, height);
		dataPoints = new ArrayList<Double>(1000);
		f1 = new Font("Sans-serif", Font.PLAIN, 14);
		f2 = new Font("Sans-serif", Font.PLAIN, 10);	
	}
	/**
	 * paints the graph function will determine which graph to paint depending if there is a current selection 
	 * and will draw the graph for that IP otherwise draw a default graph 
	 */
	public void paintComponent(Graphics g) {
		g.setColor(bgColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		dataPoints.clear();
		defaultkB = 100;
		g.setColor(Color.BLACK);
		g.setFont(f1);
		g.drawString(volumeLabel, 0, 20);
		g.drawString(timeLabel, 450, 320);
		g.drawLine(start.x, start.y, start.x, YHEIGHT);
		g.drawLine(start.x, YHEIGHT, start.x + XLENGTH, YHEIGHT);

		if (GraphData.sourceHashMap.size() < 1 || GraphData.maxTime == null) {
			Integer defaultSize = 0;
			int defaultXTick = 0;
			while (defaultXTick < 900) {
				g.drawLine(start.x + defaultXTick, YHEIGHT, start.x + defaultXTick, YHEIGHT + 8);
				g.drawString(Integer.toString(defaultSize), start.x + defaultXTick - 8, start.y + YHEIGHT);
				defaultSize += 50;
				defaultXTick += 65;
			} 	
		} else {
			Double timeInterval = 2.0;
			Double data = 0.0;
			int i = 0;
			maxDataSize = 0.0;
			dataPoints.clear();
			if (currentSelectedType) {	
				while (i < GraphData.sourceHashMap.get(currentSelectedGraph).size()) {
					if (timeInterval < GraphData.sourceHashMap.get(currentSelectedGraph).get(i)) {
						if (data > maxDataSize) {
							maxDataSize = data;
						}
						dataPoints.add(data);
						timeInterval += INTERVAL;
						data = 0.0;
					} else {
						i += 1;
						data += GraphData.sourceHashMap.get(currentSelectedGraph).get(i);
						i += 1;
					}
				}
				dataPoints.add(data);
				
			} else {
				while (i < GraphData.destinationHashMap.get(currentSelectedGraph).size()) {
					if (timeInterval < GraphData.destinationHashMap.get(currentSelectedGraph).get(i)) {
						if (data > maxDataSize) {
							maxDataSize = data;
						}
						dataPoints.add(data);
						timeInterval += INTERVAL;
						data = 0.0;
					} else {
						i += 1;
						data += GraphData.destinationHashMap.get(currentSelectedGraph).get(i);
						i += 1;
					}
				}
				dataPoints.add(data);
			}

			Integer timeSize = 0;
			int tempTicks = 0;
			int barWidth = 0;
			int xTickDistance = 0;
			
			if (GraphData.maxTime < 350.0) {
				barWidth = 4;
				xTickDistance = 5 * 25; 
			} else if (GraphData.maxTime < 500.0) {
				barWidth = 3;
				xTickDistance = 75;
			} else {
				barWidth = 2;
				xTickDistance = 50;
			}
			while (tempTicks < 900) {
				g.drawLine(start.x + tempTicks, YHEIGHT, start.x + tempTicks, YHEIGHT + 8);
				g.drawString(Integer.toString(timeSize), start.x + tempTicks - 8, start.y + YHEIGHT);
				timeSize += 50;
				tempTicks += xTickDistance;
				if (timeSize > 700) {
					break;
				}
			}
			while (maxDataSize / (1024 * MAXTICKSY) > defaultkB) {
				defaultkB = defaultkB * 2;
			}
			g.setFont(f2);
			Integer currentYSize = 0;
			int yTickDistance = 0;
			while (currentYSize < defaultkB * MAXTICKSY) {
				g.drawLine(start.x, YHEIGHT - yTickDistance, start.x - 8, YHEIGHT - yTickDistance );
				g.drawString(Integer.toString(currentYSize) + "k", start.x - 50, YHEIGHT + 5 - yTickDistance);
				yTickDistance += (int) (YHEIGHT - start.y) / MAXTICKSY;
				currentYSize += defaultkB;
			}
				
			int barHeight = 0;
				//note there are 0.0 data points
			g.setColor(Color.GREEN);
			int xPointTrack = 0;
			for (Double tempHeight : dataPoints) {
				tempHeight = tempHeight / 1024;
				tempHeight = (tempHeight / (defaultkB / ((YHEIGHT - start.y) / MAXTICKSY))); //hard coded

				barHeight = tempHeight.intValue();
				g.drawRect(start.x + 1 + xPointTrack, YHEIGHT - (barHeight)  , barWidth, (barHeight));
				xPointTrack += barWidth;
				if (barWidth == 4) {
					xPointTrack += 1;
				}
			}
		}
	}
	/**
	 * sets the current selected graph 
	 * @param currentSelectedGraph the current selected IP in the combobox
	 */
	public static void setCurrentSelectedGraph(String currentSelectedGraph) {
		Graph.currentSelectedGraph = currentSelectedGraph;
	}
	/**
	 * sets the current selected type true for source hosts false for destination hosts
	 * @param isSelected true for source hosts false for destinaton hosts
	 */
	public static void setCurrentSelectedType(boolean isSelected) { //true for source false for destination
		Graph.currentSelectedType = isSelected;
	}
}
