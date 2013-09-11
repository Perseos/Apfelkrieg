package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import lang.Lang_EN;
import lang.Lang_FR;
import lang.Lang_GER;
import main.Garden;
import main.Player;

/**
 * This is the surface for displaying the game boards during the simulation.
 * @author Daniel Gotzens
 * @see ApfelGUI
 */
public class PitchUI extends JPanel {
	private static final long serialVersionUID = 1L;
	ApfelGUI gui;
	
	boolean tabbedView = false;

	JFrame f;
	JPanel mainP, buttonP, miscP, debugP;
	JTabbedPane boardsTP;
	JButton toggleTab, toggleDebug;
	DebugTable debug;
	BoardPane board0, board1;
	DicePane diceP;
		
	Garden g[];
	Player[] c;
	String nameOld, title, restartL;
	
	/**
	 * Class Constructor.
	 * @param w The application's window. Used to set its size and title.
	 */
	public PitchUI(ApfelGUI gui, JFrame w) {
		this.gui = gui;
		f = w;
	}
	
	/**
	 * Revalidates the user interface.
	 */
	public void refreshUI(){
		//refreshing
		board0.refresh();
		board1.refresh();
		debug.refresh();
		validate();
	}
	
	/**
	 * Sets the objects that of the simulation that will be represented on the boards etc.
	 * @param g The two <code>Garden</code> objects.
	 * @param c The two <code>Player</code> objects.
	 */
	public void setSimObjects(Garden[] g, Player[] c){
		//setting language-specific strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			nameOld = Lang_GER.nameOld;
			title = Lang_GER.pitchWindow;
			restartL = Lang_GER.setUpWindow;
		} else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE) {
			nameOld = Lang_FR.nameOld;
			title = Lang_FR.pitchWindow;
			restartL = Lang_FR.setUpWindow;
		} else {
			nameOld = Lang_EN.nameOld;
			title = Lang_EN.pitchWindow;
			restartL = Lang_EN.setUpWindow;
		}
		//setting up panels
		mainP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		miscP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//miscP.setLayout(new BoxLayout(miscP, BoxLayout.X_AXIS));
		//setting up Boards and a tabbedPane with them
		board0 = new BoardPane(g[0], c[0]);
		board1 = new BoardPane(g[1], c[1]);
		this.g = g;
		this.c = c;
		boardsTP = new JTabbedPane();
		boardsTP.add("Thomas", board0);
		boardsTP.add(nameOld, board1);
		mainP.add(board0);
		mainP.add(board1);
		//setting up dice & debug-table
		diceP = new DicePane();
		debug = new DebugTable(g,c);
		//setting up tab-toggling button
		toggleTab = new JButton("Collapse");
		toggleTab.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if(tabbedView){
					f.setSize(1050, 775);
					boardsTP.removeAll();
					mainP.removeAll();
					mainP.add(board0);
					mainP.add(board1);
					toggleTab.setText("Collapse");
				} else {
					f.setSize(580, 775);
					mainP.removeAll();
					boardsTP.addTab("Thomas", board0);
					boardsTP.addTab(nameOld, board1);
					mainP.add(boardsTP);
					toggleTab.setText("Expand");
				}
				setVisible(true);
				tabbedView = !tabbedView;
				refreshUI();
			}
		});
		//adding to top-level panes
		mainP.add(boardsTP);
		buttonP.add(toggleTab);
		miscP.add(diceP);
		miscP.add(debug);
		//setting up this
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(mainP);
		add(buttonP);
		add(miscP);
		//setting up window
		f.setVisible(true);
		f.setSize(1050, 775);
		f.setTitle(title);
		refreshUI();
	}
	
	/**
	 * Updates the player who is currently to move;
	 * @param currentPlayer The current player
	 * @see DebugTable
	 */
	public void updateCurrentPlayer(int currentPlayer){
		if(tabbedView){
			boardsTP.setSelectedIndex(currentPlayer);
		}
		debug.updateCurrentPlayer(currentPlayer);
		refreshUI();
	}
	
	/**
	 * Updates the dice so that is shows a specified number.
	 * @param dice the number the dice will show.
	 */
	public void updateDice(int dice){
		diceP.setDice(dice);
		validate();
	}

	public void addRestartButton() {
		JButton restart = new JButton(restartL);
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.drawUI(ApfelGUI.MENU);
			}
		});
		buttonP.add(restart);
		validate();
	}	
}
