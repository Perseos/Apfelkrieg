package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import lang.Lang_EN;
import lang.Lang_FR;
import lang.Lang_GER;

import main.Garden;
import main.Player;

/**
 * This is the surface for setting up a new simulation.
 * @author Daniel Gotzens  
 * @see ApfelGUI
 */
public class MenuUI extends JPanel{
	private static final long serialVersionUID = 1L;
	ApfelGUI gui;
	MenuListener ml;
	MenuDialog md;
	
	JFrame w;
	JPanel buttonP, mainP, showBoardsP, showGraphsP, numberStepsP;
	JButton advanced, start, quit;
	JCheckBox showBoards, showGraphs;
	JSpinner numberSteps;
	JLabel numberStepsL;
	
	public File[] file = new File[2];
	
	public int numberStepsN, thomasApplesN, oldApplesN, vThomasN, vOldN;
	
	public boolean 
		doAI,doOutFiles, doSim, doGraphs ,
		done, stepsValid, thomasApplesValid, oldApplesValid, vThomasValid, vOldValid, 
		path1Valid, path2Valid;
	
	String
		advancedL, startL, quitL,
		showGraphsL, graphsTip, showBoardsL, boardsTip,
		noStepsL;
	
	/**
	 * Class constructor.
	 * @param gui Later used for the construction of a <code>MenuListener</code>
	 * @param w To set the application window's size and title
	 */
	public MenuUI(ApfelGUI gui, JFrame w){
		this.gui = gui;
		this.w = w;		
	}
	
	/**
	 * Initiates the user interface.
	 */
	public void initUI() {
		//setting self up
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//setting up MenuDialog
		md = new MenuDialog(this);
		//setting language-specific strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			showGraphsL = Lang_GER.showGraphs;
			graphsTip = Lang_GER.graphsTip;
			showBoardsL = Lang_GER.showSim;
			boardsTip = Lang_GER.simTip;
			noStepsL = Lang_GER.numberStepsL;
			quitL = Lang_GER.quit;
			startL = Lang_GER.start;
		 } else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE){
			showGraphsL = Lang_FR.showGraphs;
			graphsTip = Lang_FR.graphsTip;
			showBoardsL = Lang_FR.showSim;
			boardsTip = Lang_FR.simTip;
			noStepsL = Lang_FR.numberStepsL;
			quitL = Lang_FR.quit;
			startL = Lang_FR.start;
		 } else {
			showGraphsL = Lang_EN.showGraphs;
			graphsTip = Lang_EN.graphsTip;
			showBoardsL = Lang_EN.showSim;
			boardsTip = Lang_EN.simTip;
			noStepsL = Lang_EN.numberStepsL;
			quitL = Lang_EN.quit;
			startL = Lang_EN.start;	 
		 }
		//setting up ActionListener
		ml = new MenuListener(gui);
		
		//setting up low-level components
		advanced = new JButton("Advanced Options");
		start = new JButton(startL);
		quit = new JButton(quitL);
		
		showBoardsP = new JPanel();
		showBoardsP.setLayout(new FlowLayout(FlowLayout.LEFT));
		showGraphsP = new JPanel();
		showGraphsP.setLayout(new FlowLayout(FlowLayout.LEFT));
		numberStepsP = new JPanel();
		numberStepsP.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		showBoards = new JCheckBox(showBoardsL);
		showBoards.setToolTipText(boardsTip);
		showGraphs = new JCheckBox(showGraphsL);
		showBoards.setToolTipText(graphsTip);
		numberSteps = new JSpinner(new SpinnerNumberModel(21, 0, 51000000, 1));
		numberStepsL = new JLabel(noStepsL);
		
		showBoardsP.add(showBoards);
		showGraphsP.add(showGraphs);
		numberStepsP.add(numberSteps);
		numberStepsP.add(numberStepsL);

		//setting up top-level components
		buttonP = new JPanel();
		buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
		
		mainP = new JPanel();
		mainP.setLayout(new GridLayout(2, 2));
		
		//adding to buttonP
		buttonP.add(quit);
		buttonP.add(Box.createHorizontalGlue());
		buttonP.add(advanced);
		buttonP.add(Box.createHorizontalGlue());
		buttonP.add(start);
		
		//adding to mainP
		mainP.add(showBoardsP);
		mainP.add(numberStepsP);
		mainP.add(showGraphsP);
		mainP.setMaximumSize(new Dimension(2000000, 70));
				
		//adding to self
		add(mainP);
		add(Box.createVerticalGlue());
		add(buttonP);
		
		//adding ActionListener to various components
		showBoards.addActionListener(ml);
		showGraphs.addActionListener(ml);
		quit.addActionListener(ml);
		advanced.addActionListener(ml);
		start.addActionListener(ml);
		
		//setting up window
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		w.setSize(420, 200);
		w.setMinimumSize(new Dimension(410, 130));	
	}
	
	/**
	 * Checks through the following preferences: the initial number of apples in the game (which can't be higher than 100 as one board can only contain this many)
	 * and the output-files (which need to be selected and not to be the same one).
	 * @return <code>true</code> if all preferences are valid.
	 */
	public boolean checkPrefs(){
		thomasApplesValid = oldApplesValid = path1Valid = path2Valid = true;
		if(file[0] == null && doOutFiles){
			md.outFile1.setForeground(Color.RED);
			path1Valid = false;
		}
		if(file[1] == null && doOutFiles){
			md.outFile2.setForeground(Color.RED);
			path2Valid = false;
		}
		if(!path1Valid || !path2Valid && doOutFiles){
			md.initUI();
			return false;
		}
 		if(doOutFiles && file[0].equals(file[1])){
			JOptionPane.showMessageDialog(
					w,
					"You chose the same output-file twice.",
					 "Identical output files",
					 JOptionPane.WARNING_MESSAGE);
			return false;
		}
 		if(md.initiated){
			numberStepsN = (Integer) numberSteps.getValue();
			thomasApplesN = (Integer) md.thomasApples.getValue();
			oldApplesN = (Integer) md.oldApples.getValue();
			vThomasN = (Integer) md.vThomas.getValue();
			vOldN = (Integer) md.vOld.getValue();
 		} else {
 			setDefaultValues();
 		}
		
		if(thomasApplesN + oldApplesN > 100){
				thomasApplesValid = oldApplesValid = false;
				JOptionPane.showMessageDialog(
						w,
						"<HTML>To prevent the boards from overflowing,<BR/>only a total of 100 apples are allowed.</HTML>",
						 "Too many apples",
						 JOptionPane.WARNING_MESSAGE);
		}
		if(md.initiated) md.refreshUI();
		return (thomasApplesValid && oldApplesValid && path1Valid && path2Valid);
	}
	
	public void setDefaultValues() {
		doOutFiles = false;
		file[0] = file[1] = null;
		numberStepsN = (Integer) numberSteps.getValue();
		thomasApplesN = 10;
		oldApplesN = 5;
		vThomasN = 1;
		vOldN = -1;
	}
	/*ApfelGUI a;
	
	MenuListener ml;
	JFrame w;
	JPanel mainPane, buttonsPane, rulesPane, outPane;
	JTabbedPane tp;
	JCheckBox charsAI, outFiles, visualize, showSim, showGraphs;
	JSpinner numberSteps, thomasApples, oldApples, vThomas, vOld;
	JLabel thomasL, oldL, numberStepsL, thomasApplesL, oldApplesL, vThomasL, vOldL, outFile1, outFile2;
	JButton file1Browse, file2Browse, quit, start;
	JProgressBar pb;
	JSeparator s1, s2;
	ImageIcon txtIcon;
	JFileChooser fc;
	
	public boolean 
		doAI,doOutFiles, doSim, doGraphs ,
		done, stepsValid, thomasApplesValid, oldApplesValid, vThomasValid, vOldValid, 
		path1Valid, path2Valid;
	
	public File[] file = new File[2];
	
	Garden[] garden;
	Player[] c;
	public int numberStepsN, thomasApplesN, oldApplesN, vThomasN, vOldN;
	
	String 
		settings, output,
		noStepsL, nameOld, applesL, vL, vTip, playersAI, aiTip,
		outFilesL, outFilesTip, outFile1L, outFile2L, fileBrowse, 
		visualizeL, showGraphsL, graphsTip, showSimL, simTip,
		quitL, startL,
		setUpWindow, pitchWindow, progressWindow, graphWindow, fileChoose1, fileChoose2;
	
	/**
	 * Class constructor.
	 * @param gui Later used for the construction of the MenuListener.
	 * @param window Later used to set the size and title of the window.
	 */
	/*public MenuUI(ApfelGUI gui, JFrame window) {
		w = window;
		a = gui;
		//initUI();
	}
	
	/**
	 * Sets up the user interface.
	 */
	/*public void initUI(){
		//setting language-specific strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			settings = Lang_GER.settings;
			output = Lang_GER.output;
			noStepsL = Lang_GER.numberStepsL;
			nameOld = Lang_GER.nameOld;
			applesL = Lang_GER.applesL;
			vL = Lang_GER.vL;
			vTip = Lang_GER.vTip;
			playersAI = Lang_GER.charsAI;
			aiTip = Lang_GER.aiTip;
			outFilesL = Lang_GER.outFiles;
			outFilesTip = Lang_GER.outFilesTip;
			outFile1L = Lang_GER.outFile1;
			outFile2L = Lang_GER.outFile2;
			fileBrowse = Lang_GER.fileBrowse;
			showGraphsL = Lang_GER.showGraphs;
			graphsTip = Lang_GER.graphsTip;
			showSimL = Lang_GER.showSim;
			simTip = Lang_GER.simTip;
			setUpWindow = Lang_GER.setUpWindow;
			pitchWindow = Lang_GER.pitchWindow;
			progressWindow = Lang_GER.progressWindow;
			graphWindow = Lang_GER.graphWindow;
			fileChoose1 = Lang_GER.fileChoose1;
			fileChoose2 = Lang_GER.fileChoose2;
			quitL = Lang_GER.quit;
			startL = Lang_GER.start;
		 } else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE){
 			settings = Lang_FR.settings;
	 		output = Lang_FR.output;
	 		noStepsL = Lang_FR.numberStepsL;
	 		nameOld = Lang_FR.nameOld;
	 		applesL = Lang_FR.applesL;
	 		vL = Lang_FR.vL;
	 		vTip = Lang_FR.vTip;
	 		playersAI = Lang_FR.charsAI;
	 		aiTip = Lang_FR.aiTip;
	 		outFilesL = Lang_FR.outFiles;
	 		outFilesTip = Lang_FR.outFilesTip;
	 		outFile1L = Lang_FR.outFile1;
	 		outFile2L = Lang_FR.outFile2;
	 		fileBrowse = Lang_FR.fileBrowse;
	 		showGraphsL = Lang_FR.showGraphs;
	 		graphsTip = Lang_FR.graphsTip;
	 		showSimL = Lang_FR.showSim;
	 		simTip = Lang_FR.simTip;
	 		setUpWindow = Lang_FR.setUpWindow;
	 		pitchWindow = Lang_FR.pitchWindow;
	 		progressWindow = Lang_FR.progressWindow;
	 		graphWindow = Lang_FR.graphWindow;
	 		fileChoose1 = Lang_FR.fileChoose1;
	 		fileChoose2 = Lang_FR.fileChoose2;
	 		quitL = Lang_FR.quit;
	 		startL = Lang_FR.start;
		 } else {
		 	settings = Lang_EN.settings;
			output = Lang_EN.output;
			noStepsL = Lang_EN.numberStepsL;
			nameOld = Lang_EN.nameOld;
			applesL = Lang_EN.applesL;
			vL = Lang_EN.vL;
			vTip = Lang_EN.vTip;
			playersAI = Lang_EN.charsAI;
			aiTip = Lang_EN.aiTip;
			outFilesL = Lang_EN.outFiles;
			outFilesTip = Lang_EN.outFilesTip;
			outFile1L = Lang_EN.outFile1;
			outFile2L = Lang_EN.outFile2;
			fileBrowse = Lang_EN.fileBrowse;
			showGraphsL = Lang_EN.showGraphs;
			graphsTip = Lang_EN.graphsTip;
			showSimL = Lang_EN.showSim;
			simTip = Lang_EN.simTip;
			setUpWindow = Lang_EN.setUpWindow;
			pitchWindow = Lang_EN.pitchWindow;
			progressWindow = Lang_EN.progressWindow;
			graphWindow = Lang_EN.graphWindow;
			fileChoose1 = Lang_EN.fileChoose1;
			fileChoose2 = Lang_EN.fileChoose2;
			quitL = Lang_EN.quit;
			startL = Lang_EN.start;
		 }
		//setting up main pane
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//and its contents
		quit = new JButton("Quit");
		start = new JButton("Start");
		buttonsPane = new JPanel();
		buttonsPane.setOpaque(true);
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
		buttonsPane.setPreferredSize(new Dimension(190, 40));
		//Setting up ActionListener
		ml = new MenuListener(a);
		//Setting up file chooser
		fc = new JFileChooser();	
		fc.setMultiSelectionEnabled(false);
		fc.setFileHidingEnabled(true);
		fc.addChoosableFileFilter(new FileFilter(){
			@Override
			public boolean accept(File f) {
				return 	f.getName().endsWith(".txt") ||
					f.getName().endsWith(".TXT") ||
					f.getName().endsWith(".text") ||
					f.getName().endsWith(".TEXT") ||
					f.isDirectory();
			}
	
				@Override
			public String getDescription() {
				if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY)
					return "Textdateien";
				else
					return "plain text files";
			}		
		});
		txtIcon = new ImageIcon("/Users/daniel/Downloads/plain_text_icon.png", "see file name");
		//initiating tabbedPane
		tp = new JTabbedPane();
		tp.setPreferredSize(new Dimension(190, 100));
		//initiating the contents of the two tabs
		rulesPane = new JPanel();
		rulesPane.setOpaque(false);
		rulesPane.setLayout(new BoxLayout(rulesPane, BoxLayout.Y_AXIS));
		outPane = new JPanel();
		outPane.setOpaque(false);
		outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
		//adding components to rulesPane
		//creating local JPanels
		JPanel cTable, c1, c2, nameP1, nameP2, applesP1, applesP2, vPane1, vPane2, movesP, aiPane;
		cTable = new JPanel();
		nameP1 = new JPanel();
		nameP2 = new JPanel();
		c1 = new JPanel();
		c2 = new JPanel();
		applesP1 = new JPanel();
		applesP2 = new JPanel();
		vPane1 = new JPanel();
		vPane2 = new JPanel();
		movesP = new JPanel();
		aiPane = new JPanel();
		//layouts, sizes and opacity
		cTable.setLayout(new GridLayout(1, 2));
		cTable.setPreferredSize(new Dimension(500, 100));
		cTable.setOpaque(false);
		
		nameP1.setPreferredSize(new Dimension(250, 30));
		nameP1.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameP1.setOpaque(false);
		
		nameP2.setPreferredSize(new Dimension(250, 30));
		nameP2.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameP2.setOpaque(false);
		
		c1.setLayout(new BoxLayout(c1, BoxLayout.Y_AXIS));
		c1.setPreferredSize(new Dimension(250, 120));
		c1.setOpaque(false);
		
		c2.setLayout(new BoxLayout(c2, BoxLayout.Y_AXIS));
		c2.setPreferredSize(new Dimension(250, 120));
		c2.setOpaque(false);
		
		applesP1.setPreferredSize(new Dimension(250, 30));
		applesP1.setLayout(new FlowLayout(FlowLayout.LEFT));
		applesP1.setOpaque(false);
		
		applesP2.setPreferredSize(new Dimension(250, 30));
		applesP2.setLayout(new FlowLayout(FlowLayout.LEFT));
		applesP2.setOpaque(false);
		
		vPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
		vPane1.setPreferredSize(new Dimension(250, 30));
		vPane1.setOpaque(false);
		
		vPane2.setLayout(new FlowLayout(FlowLayout.LEFT));
		vPane2.setPreferredSize(new Dimension(250, 30));
		vPane2.setOpaque(false);
		
		movesP.setLayout(new FlowLayout(FlowLayout.LEFT));
		movesP.setPreferredSize(new Dimension(500, 30));
		movesP.setOpaque(false);
		
		aiPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		aiPane.setPreferredSize(new Dimension(500, 30));
		aiPane.setOpaque(false);
		//Constructing the non-JPanel-components
		thomasApples = new JSpinner(new SpinnerNumberModel(10, 0, 99, 1));
		thomasApplesL = new JLabel(applesL);
		oldApples = new JSpinner(new SpinnerNumberModel(5, 0, 99, 1));
		oldApplesL = new JLabel(applesL);
		vThomas = new JSpinner(new SpinnerNumberModel(1, -1, 1000, 1));
		vThomasL = new JLabel(vL);
		vOld = new JSpinner(new SpinnerNumberModel(-1, -1, 1000, 1));
		vOldL = new JLabel(vL);
		thomasL = new JLabel("Thomas");
		oldL = new JLabel(nameOld);
		numberSteps = new JSpinner(new SpinnerNumberModel(21, 0, 51000000, 1));
		numberStepsL = new JLabel(noStepsL);
		charsAI = new JCheckBox(playersAI);
		//setting their sizes
		Dimension d = new Dimension(80, 30);
		thomasApples.setPreferredSize(d);
		oldApples.setPreferredSize(d);
		vThomas.setPreferredSize(d);
		vOld.setPreferredSize(d);
		numberSteps.setPreferredSize(d);
		//filling the JPanels
		nameP1.add(thomasL);
		nameP2.add(oldL);
		
		applesP1.add(thomasApples);
		applesP1.add(thomasApplesL);
		
		applesP2.add(oldApples);
		applesP2.add(oldApplesL);
		
		vPane1.add(vThomas);
		vPane1.add(vThomasL);
		
		vPane2.add(vOld);
		vPane2.add(vOldL);
		
		c1.add(nameP1);
		c1.add(applesP1);
		c1.add(vPane1);
		
		c2.add(nameP2);
		c2.add(applesP2);
		c2.add(vPane2);
		
		cTable.add(c1);
		cTable.add(c2);
		
		movesP.add(numberSteps);
		movesP.add(numberStepsL);
		
		aiPane.add(charsAI);
		
		rulesPane.setPreferredSize(new Dimension(500, 200));
		rulesPane.add(cTable);
		rulesPane.add(new JSeparator(JSeparator.HORIZONTAL));
		rulesPane.add(movesP);
		rulesPane.add(aiPane);
		//Setting font of headlines
		thomasL.setFont(new Font("Display", Font.BOLD, 14));
		oldL.setFont(new Font("Display", Font.BOLD, 14));
		//adding components to output-pane
		//creating local JPanels
		JPanel fc1, fc2;
		
		fc1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		fc1.setAlignmentX(Component.LEFT_ALIGNMENT);
		fc1.setOpaque(false);
		
		fc2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		fc2.setAlignmentX(Component.LEFT_ALIGNMENT);
		fc2.setOpaque(false);
		//adding contents to them
		fc1.add(Box.createRigidArea(d));
		fc1.add(file1Browse = new JButton(fileBrowse));
		fc1.add(outFile1 = new JLabel(outFile1L, txtIcon, JLabel.CENTER));
		
		fc2.add(Box.createRigidArea(d));
		fc2.add(file2Browse = new JButton(fileBrowse));
		fc2.add(outFile2 = new JLabel(outFile2L, txtIcon, JLabel.CENTER));
		
		outPane.add(showSim = new JCheckBox(showSimL, false));
		outPane.add(showGraphs = new JCheckBox(showGraphsL, false));
		outPane.add(new JSeparator(JSeparator.HORIZONTAL));
		outPane.add(outFiles = new JCheckBox(outFilesL, false));
		outPane.add(fc1);
		outPane.add(fc2);
		//tool tips
		vThomas.setToolTipText(vTip);
		vOld.setToolTipText(vTip);
		vThomasL.setToolTipText(vTip);
		vOldL.setToolTipText(vTip);
		charsAI.setToolTipText(aiTip);
		outFiles.setToolTipText(outFilesTip);
		showGraphs.setToolTipText(graphsTip);
		showSim.setToolTipText(simTip);
		//ChangeListener for the JTabbedPane
		tp.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	           if(tp.getSelectedComponent() == rulesPane){
	        	   w.setSize(600, 320);
	           } else if(tp.getSelectedComponent() == outPane){
	        	   w.setSize(600, 280);
	           }
	           refreshUI();
	        }
	    });

		//Adding ActionListeners to various components
		//charsAI
		outFiles.addActionListener(ml);
		showSim.addActionListener(ml);
		showGraphs.addActionListener(ml);
		file1Browse.addActionListener(ml);
		file2Browse.addActionListener(ml);
		quit.addActionListener(ml);		
		start.addActionListener(ml);
		
		tp.addTab(settings, rulesPane);
		tp.addTab(output, outPane);
		buttonsPane.add(quit);
		buttonsPane.add(Box.createHorizontalGlue());
		buttonsPane.add(start);
		add(tp);
		add(buttonsPane);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setTitle(setUpWindow);
		w.setVisible(true);		
		w.setSize(600, 300);
	}
	
	/**
	 * "Grays out" the components not needed if the user hasn't checked the text-file output option.
	 */
	/*public void refreshUI(){
		//Enabling low-level components
		outFile1.setEnabled(outFiles.isSelected());
		outFile2.setEnabled(outFiles.isSelected());
		file1Browse.setEnabled(outFiles.isSelected());
		file2Browse.setEnabled(outFiles.isSelected());
	}*/
}
