// Name: XunFan Zhou
// UPI: xzho684

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

public class GraphData extends Graph {
    /**
	 * File path of input file
     */
	protected File fileLocation;
	/**
	 * sourceHashMap A LinkedHashMap for source IP address where IP address is the key and values are the 
     * bytes received in a ArrayList<Double>
	 */
	protected static LinkedHashMap<String, ArrayList<Double>> sourceHashMap;
    /**
	 * destinationHashMap A LinkedHashMap for destination IP address where IP address is the key and values 
     * are the bytes received in a ArrayList<Double> 
     */
	protected static LinkedHashMap<String, ArrayList<Double>> destinationHashMap;
    /**
	 * the maximum time of the input file 
     */
	protected static Double maxTime;
    /** Constructor to initialize GraphData and Graph
     *  @param fileLocation file path for input file
     *  @param x x coordinate for the topleft side of where the Graph JPanel starts
     *  @param y y coordinate for the topleft side of where the Graph JPanel starts
     *  @param width width of Graph
     *  @param height height of Graph
     *  @param color bgColor of graph of Graph
     */
	public GraphData(File fileLocation, int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		this.fileLocation = fileLocation;
        sourceHashMap = new LinkedHashMap<String, ArrayList<Double>>(200);
        destinationHashMap = new LinkedHashMap<String, ArrayList<Double>>(200);
	}
	/**
	 * Opens the path fileLocation handles and sorts the data into the desired format and calculates the maximum time
     * @see {@link GraphData#sourceHashMap}
     * @see {@link GraphData#destinationHashMap}
     * @see {@link GraphData#maxTime}
     */
	public void sortData() {
		sourceHashMap.clear();
		destinationHashMap.clear();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Pattern pattern = Pattern.compile("(\\S+)\\t(\\S+)\\t(\\S+)\\t(\\S*)\\t(\\S+)\\t(\\S*)"
            		+ "\\t(\\S+)\\t(\\S+)\\t(\\S*)\\t(\\S*)\\t(\\S*)\\t(\\S*)\\t(\\S*)\\t(\\S*)\\t(\\S*)\\t(\\S*)");
            while((line = bufferedReader.readLine()) != null) {
            	Matcher match = pattern.matcher(line);
            	if (match.matches()) {
            		Double timeTaken = Double.parseDouble(match.group(2));
            		Double bytesSent = Double.parseDouble(match.group(8));
            		String IPSource = match.group(3);
            		String IPDestination = match.group(5);
            		maxTime = timeTaken;
            		
            		ArrayList<Double> tempKeySource = sourceHashMap.get(IPSource);		
            		if(tempKeySource != null) {
            			tempKeySource.add(timeTaken);
            			tempKeySource.add(bytesSent);
            		} else {
            			sourceHashMap.put(IPSource, new ArrayList<Double>(200000));
            			sourceHashMap.get(IPSource).add(timeTaken);
            			sourceHashMap.get(IPSource).add(bytesSent);
            		}
            		
            		ArrayList<Double> tempKeyDestination = destinationHashMap.get(IPDestination);	
            		if(tempKeyDestination != null) {
            			tempKeyDestination.add(timeTaken);
            			tempKeyDestination.add(bytesSent);
            		} else {
            			destinationHashMap.put(IPDestination, new ArrayList<Double>(200000));
            			destinationHashMap.get(IPDestination).add(timeTaken);
            			destinationHashMap.get(IPDestination).add(bytesSent);
            		}
            	}           	
            }
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileLocation + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileLocation + "'");
        }
	}
	
	/**
	 * @param location sets the path of the file to fileLocation {@link GraphData#fileLocation}
	 */
	public void setupFileLocation(File location) {
		fileLocation = location;
		sortData();
	}
	/**
	 * @return fileLocation path
	 */
	public String toString() {
		return fileLocation.getPath();
	}
}
