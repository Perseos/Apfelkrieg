package gui;

//import java.awt.Graphics;
import gui.ApfelGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.Apfelkrieg;

/**
 * This is the <code>ActionListener</code> used for various interactions with the surface <code>MenuUI</code>
 * @author Daniel Gotzens
 *
 */
public class MenuListener implements ActionListener{
	ApfelGUI gui;
	Apfelkrieg ak;
	String[] path = new String[2];
	String s;
	int currentPlayer;
	
	JButton c0, c1, refresh, swap;
	boolean[] output = new boolean[3];
	
	/**
	 * Class constructor.
	 * @param a The user interface this class interacts with.
	 * @see ApfelGUI
	 */
	public MenuListener(ApfelGUI a){
		gui = a;
	}
	
	/**
	 * When this method gets called, it will cause different reactions based on the soure of the <code>ActionEvent</code>
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gui.menu.start) start();	
		else if(e.getSource() == gui.menu.charsAI) charsAI();
		else if(e.getSource() == gui.menu.outFiles) outFiles();
		else if(e.getSource() == gui.menu.showSim) showSim();
		else if(e.getSource() == gui.menu.showGraphs) showGraphs();
		else if(e.getSource() == gui.menu.file1Browse) fileBrowse(0);
		else if(e.getSource() == gui.menu.file2Browse) fileBrowse(1);
		else if(e.getSource() == gui.menu.quit) System.exit(0);
	}
	
	/**
	 * Starts up the Applewar-simulation.
	 * After checking through preferences, the UI will either show a <code>PitchUI</code> or a <code>ProgressUI</code>.
	 */
	public void start(){
		gui.updateUI();
		gui.menu.done = gui.menu.checkPrefs();
		if(gui.menu.done){
				SwingUtilities.invokeLater(new Runnable(){
				      public void run() {
				    	  if(gui.menu.doSim) gui.drawUI(ApfelGUI.PITCH);
				    	  else gui.drawUI(ApfelGUI.PROGRESS);
				      }      
				});
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						ak = new Apfelkrieg(gui);
						ak.start();
					}
				});			
		}		
	}
	
	/**
	 * Toggles the option of intelligent Players.
	 */
	public void charsAI(){
		gui.menu.doSim = !gui.menu.doSim;
		//toggles
		gui.menu.refreshUI();
	}
	
	/**
	 * Toggles the option of intelligent Players.
	 */
	public void outFiles(){
		gui.menu.doOutFiles = !gui.menu.doOutFiles;
		//toggles
		gui.menu.refreshUI();
	}
	
	/**
	 * Toggles the option of showing the Applewar-boards.
	 */
	public void showSim(){
		gui.menu.doSim = !gui.menu.doSim;
		//toggles
		gui.menu.refreshUI();
	}
	
	/**
	 * Toggles the option of aggregating the results of the simulation in a graph.
	 */
	public void showGraphs(){
		gui.menu.doGraphs = !gui.menu.doGraphs;
			//toggles
	}
	
	/**
	 * For the option of a text-file output, this method opens up a <code>JFileChooser</code> dialog.
	 * @param id Because there is a button to choose an output file for each of the players, the source-button is specified with this parameter.
	 */
	public void fileBrowse(int id){
		if(id == 0) gui.menu.fc.setDialogTitle(gui.menu.fileChoose1);
		else gui.menu.fc.setDialogTitle(gui.menu.fileChoose2);
		int returnVal = gui.menu.fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION){
			if(id == 0){
				gui.menu.outFile1.setText(gui.menu.fc.getSelectedFile().getName());
				gui.menu.outFile1.setForeground(Color.BLACK);
			}
			else{
				gui.menu.outFile2.setText(gui.menu.fc.getSelectedFile().getName());
				gui.menu.outFile2.setForeground(Color.BLACK);

			}
			gui.menu.file[id] = gui.menu.fc.getSelectedFile();
		}
		gui.menu.refreshUI();
	}
}
