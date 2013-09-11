package gui;

import javax.swing.JFrame;

import main.Garden;
import main.Player;

/**
 * This class coordinates all off the application's user interface.
 * @author Daniel Gotzens
 */
public class ApfelGUI {
	public JFrame w;
	public MenuUI menu;
	public PitchUI pitch;
	public GraphUI graph;
	public ProgressUI prog;
	
	/** A setup-surface used to start a new Applewar. 
	 * @see #drawUI(int)
	 * */
	public final static int MENU = 0;
	/** A surface displaying the two Applewar-pitches during the simulation. 
	 * @see #drawUI(int) */
	public final static int PITCH = 1;
	/** A graph-surface aggregating the results of the Applewar.
	 * @see #drawUI(int)*/
	public final static int GRAPH = 2;
	/** A progress-bar shown during the simulation.
	 * @see #drawUI(int) */
	public final static int PROGRESS = 3;
	
	/**
	 * The class constructor creates the window and all surfaces to be used later.
	 */
	public ApfelGUI() {
		w = new JFrame();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu = new MenuUI(this, w);
		pitch = new PitchUI(this, w);
		graph = new GraphUI(this, w);
		prog = new ProgressUI(this, w);
	}
	
	/**
	 * This method is called to switch between the four surfaces. 
	 * @param type
	 * Specifies which surface to draw
	 */
	public void drawUI(int type){
		switch (type){
			case MENU:
				menu.initUI();
				w.setContentPane(menu);
				break;
			case PITCH:
				w.setContentPane(pitch);
				break;
			case GRAPH:
				graph.initUI();
				w.setContentPane(graph);
				break;
			case PROGRESS:
				prog.initUI();
				w.setContentPane(prog);
				break;
		}
	}
	
	/** Re-validates the current surface */
	public void updateUI(){
		try{
			pitch.refreshUI();
			prog.refreshUI();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gives the Objects taking part in the simulation to the surfaces that need to access their data.
	 * @param garden An array of the two gardens
	 * @param c An array of the two players
	 * @see GraphUI
	 * @see PitchUI
	 * @see Garden
	 * @see Player
	 */
	public void setSimObjects(Garden[] garden, Player[] c) {
		graph.setSimObjects(garden);
		pitch.setSimObjects(garden, c);
	}
	
	/**
	 * Sets the total steps of the simulation.
	 * @param totalMoves the total steps of the simulation
	 */
	public void setTotalMoves(int totalMoves) {
		graph.setTotalMoves(totalMoves, menu.thomasApplesN, menu.oldApplesN);
		prog.setTotalMoves(totalMoves);
	}
	
	/**
	 * Sets the current steps of the simulation.
	 * @param currentMoves the current steps of the simulation
	 */
	public void setCurrentMoves(int currentMoves){
		graph.setCurrentMoves(currentMoves);
		prog.updateCurrentMoves(currentMoves);
	}
}
