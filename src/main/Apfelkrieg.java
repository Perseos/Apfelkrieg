package main;

import gui.ApfelGUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 * This class runs the actual simulation.
 * @author Daniel Gotzens
 */
public class Apfelkrieg{
	
	FileWriter outFile1, outFile2;
    PrintWriter pw1, pw2;
    
    Timer t;
	
	private int dice,
	currentPlayer, currentStep = 0, totalSteps,
	thomasApples, oldApples, vThomas, vOld; 
	private boolean doAI, doBoards, doGraph, doOutFiles;
	
	public Garden[] g = new Garden[2];
	public Player[] c = new Player[2];
	
	ApfelGUI gui;
	private File[] file;
	
	/**
	 * The <code>SwingWorker</code> that does the computing.
	 * @author Daniel Gotzens
	 */
	private class SimulateWorker extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			while(currentStep < totalSteps){
				performStep();
				if(doBoards) Thread.sleep(500);
			}
			return null;
		}
		
		public void done(){
			if(!doBoards)
				gui.prog.updateCurrentMoves(currentStep);
			if(doGraph)
				gui.graph.setCurrentMoves(currentStep);
			if(doGraph) gui.drawUI(ApfelGUI.GRAPH);
			if(doOutFiles) {
				pw1.close();
				pw2.close();
			}
			if(!doGraph){
				if(doBoards) gui.pitch.addRestartButton();
				else gui.prog.addRestartButton();
			}
		}
	}
	
	/**
	 * Class constructor.
	 * @param gui The user interface this simulation will have to update.
	 * @see ApfelGUI
	 */
	public Apfelkrieg(ApfelGUI gui){
		this.gui = gui;
		
		totalSteps = gui.menu.numberStepsN;
		thomasApples = gui.menu.thomasApplesN;
		oldApples = gui.menu.oldApplesN;
		vThomas = gui.menu.vThomasN;
		vOld = gui.menu.vOldN;
		doOutFiles = gui.menu.doOutFiles;
		doBoards = gui.menu.doSim;
		doGraph = gui.menu.doGraphs;
		file = gui.menu.file;
		doAI = gui.menu.doAI;
		
		if(doOutFiles){
			try {
				outFile1 = new FileWriter(file[0]);
				outFile2 = new FileWriter(file[1]);
				System.out.println("filewriters");
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw1 = new PrintWriter(outFile1);
			pw2 = new PrintWriter(outFile2);
			System.out.println("printwriters");
		}
		
		g[0] =  new Garden(thomasApples);
		g[1] = new Garden(oldApples);
		
		c[0] = new Player(g[0], g[1]);
		c[1] = new Player(g[1], g[0]);
		
		if(doBoards) gui.pitch.setSimObjects(g, c);
		if(doGraph) gui.graph.setSimObjects(g);
		gui.setTotalMoves(totalSteps);
		gui.graph.setMaxApples(g[0].totalApples + g[1].totalApples);
	}
	
	/**
	 * Starts the simulation.
	 */
	public void start(){
		new SimulateWorker().execute();
	}	

	/**
	 * Performs one step in the Simulation. This method gets called by the <code>SwingWorker</code>.
	 */
	private void performStep() {
		if(dice > 0){
			if(doAI) c[currentPlayer].walk();
			else c[currentPlayer].walkRandom();
			if(doBoards) gui.pitch.refreshUI();
			dice--;
		} else {
			//throw Apples
			c[currentPlayer].throwApples();
			//end current step
			if(currentPlayer == 1){
				if(doOutFiles){
					//file out-put
		        	pw1.println("" + g[0].totalApples);
		        	pw2.println("" + g[1].totalApples);
		        }
				if(!doBoards)
					gui.prog.updateCurrentMoves(currentStep);
				if(doGraph)
					gui.graph.setCurrentMoves(currentStep);
				currentStep++;
			}
			//switch player
			if(currentPlayer == 0) currentPlayer = 1;
			else currentPlayer = 0;
			if(doBoards) gui.pitch.updateCurrentPlayer(currentPlayer);
			if(doGraph) gui.graph.updateCurrentPlayer(currentPlayer);
			//throw Dice
			dice = (int)(Math.random()*6 +1);
			if(doBoards) gui.pitch.updateDice(dice);
			//add dice-modifier
			if(currentPlayer == 0) dice += vThomas;
			else dice += vOld;
			//Select direction (AI only)
			if(doAI) c[currentPlayer].setDirection(dice);
		}	
	}
}