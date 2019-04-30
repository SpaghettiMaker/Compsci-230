// Name: XunFan Zhou
// UPI: xzho684

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.File;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class A2 extends JFrame {
    private Font font;
    private JFrame app;
    private JMenu fileMenu;
    private JMenuBar menuBar;
    private JMenuItem fileMenuOpen, fileMenuQuit;
    private JFileChooser fileChooser;
    private JPanel radioButtonPanel;
    private ButtonGroup radioButtongroup;
    private JRadioButton radioButtonSourceHosts, radioButtonDestinationHosts;
    
    private GraphData mainGraph;
	private JPanel comboBoxPanel;
	
	private JComboBox<String> comboBoxIP;
	private SortedSet<String>  IPSourceList;
	private SortedSet<String> IPDestinationList;
	private Comparator<String> ipComparator;
	
    /**
     * Sets up all GUI components and creates the JFrame for the GUI components to be added to
     */
    public A2() {
    	font = new Font("Sans-serif", Font.PLAIN, 20);
    	app = new JFrame("Flow volume viewer");
    	app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	fileChooser = new JFileChooser(".");
    	app.setSize(1000, 500);
    	app.setLayout(null);
    	
        ipComparator = new Comparator<String>() {
            public int compare(String ip1, String ip2) {
                return toLong(ip1).compareTo(toLong(ip2));
            }       
        };
        IPSourceList = new TreeSet<String>(ipComparator);
        IPDestinationList = new TreeSet<String>(ipComparator);
        
    	setupMenu();
    	setupRadioButtons();
    	setupComboBox();
    	setupGraph();
    	app.setResizable(false);
    	app.setVisible(true);
    }
    /**
     * creates the Menu and Menubar defines the ActionListiners for each MenuItem and is added the main app
     */
	private void setupMenu() {
		menuBar = new JMenuBar();
		app.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.setFont(font);
		
		fileMenuOpen = new JMenuItem("Open Trace File");
		fileMenuOpen.setFont(font);
		fileMenuOpen.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
					// "." input refers to current directory
				int retval = fileChooser.showOpenDialog(A2.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					mainGraph.setupFileLocation(f);
					comboBoxIP.removeAllItems();
					radioButtonSourceHosts.setSelected(true);
					Graph.setCurrentSelectedType(true);
					IPSourceList.clear(); // important to reset
					IPDestinationList.clear();
			        IPSourceList.addAll(GraphData.sourceHashMap.keySet());
			        IPDestinationList.addAll(GraphData.destinationHashMap.keySet());
					for (String i : IPSourceList) {
						comboBoxIP.addItem(i);
					}
					comboBoxPanel.setVisible(true);
					app.setVisible(false); 
					app.setVisible(true);
					mainGraph.repaint();
				}
			}
		});
		fileMenu.add(fileMenuOpen);
		
		fileMenuQuit = new JMenuItem("Quit");
		fileMenuQuit.setFont(font);
		fileMenu.add(fileMenuQuit);
		fileMenuQuit.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(fileMenuQuit);
	}
    /**
     * creates the Radiobuttons defines the ActionListiners and is added to a JPanel which is added the main app
     */
	private void setupRadioButtons() {
		radioButtonPanel = new JPanel();
		radioButtongroup = new ButtonGroup();
		radioButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.WEST;
		
		radioButtonSourceHosts = new JRadioButton("Source Hosts");
		radioButtonSourceHosts.setFont(font);
		radioButtonSourceHosts.setSelected(true);
		radioButtongroup.add(radioButtonSourceHosts);
		radioButtonSourceHosts.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				comboBoxIP.removeAllItems();
				for (String i : IPSourceList) {
					comboBoxIP.addItem(i);
				}
				Graph.setCurrentSelectedType(true);
			}
		});
		
		radioButtonDestinationHosts = new JRadioButton("Destination Hosts");
		radioButtonDestinationHosts.setFont(font);
		radioButtonDestinationHosts.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				comboBoxIP.removeAllItems();
				for (String i : IPDestinationList) {
					comboBoxIP.addItem(i);
				}
				Graph.setCurrentSelectedType(false);
			}
		});
		radioButtongroup.add(radioButtonDestinationHosts);
		
		radioButtonPanel.add(radioButtonSourceHosts, c);
		radioButtonPanel.add(radioButtonDestinationHosts, c);
		//radioButtonPanel.setBackground(Color.BLACK);
		radioButtonPanel.setBounds(0, 0, 200, 100);
		app.add(radioButtonPanel);
	}
    /**
     * creates a new GraphData which will create a and is added to the main app
     */
	private void setupGraph() {
		mainGraph = new GraphData(null, 0, 100, 985, 325, Color.WHITE);
		app.add(mainGraph);
	}
    /**
     * creates the combobox sets up ActionListiner and adds it to a JPanel which is added to the main app
     */
	private void setupComboBox() {
		comboBoxPanel = new JPanel();
		comboBoxIP = new JComboBox<String>();
		comboBoxIP.setFont(font);
		comboBoxIP.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				if (comboBoxIP.getSelectedItem() != null) {
					GraphData.setCurrentSelectedGraph((String) comboBoxIP.getSelectedItem());
					mainGraph.repaint();
				}
			}
		});
		comboBoxPanel.setBounds(400, 0, 300, 100);
		comboBoxPanel.add(comboBoxIP);
		app.add(comboBoxPanel);
		comboBoxPanel.setVisible(false);
	}
	
	/**
	 * converts the String input into a Long object for comparison
	 * @param ip the IP String
	 * @return Long equivalent of IP address
	 */
    public static Long toLong(String ip) {
    	ip = ip.replace(".", "");
    	return Long.parseLong(ip);
    }
	/**
	 * the main entry point will create a thread for A2 so it runs smoothly 
	 * @param args command line args
	 */
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			new A2();
    		}
    	});
    }
}
