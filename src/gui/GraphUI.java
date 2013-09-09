package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Garden;

import lang.*;
/**
 * This surface aggregates the data of the simulation in a chart. 
 * Since this program is a model of the chemical equilibrium, it is a chart about the concentration of apples(<code>totalApples</code>) over time.
 * @author Daniel Gotzens
 * @see ApfelGUI
 * @see <a href="http://en.wikipedia.org/wiki/Chemical_equilibrium">The Wikipedia page about chemical equilibrium</a>
 */
public class GraphUI extends JPanel{
	private static final long serialVersionUID = 1L;
	Garden[] garden;
	ApfelGUI gui;
	
	int currentMoves = 0, maxApples, totalMoves, currentPlayer, movesPerX, xPerMove, yAmp = 99999;
	boolean skipMoves;
	byte[][] values;
	String applesL, nameOld, title, restartL;
	
	JFrame w;
	JPanel graphP, bottomP;
	JLabel thomasL, oldL;
	JButton restart;
	
	/**
	 * Class constructor.
	 * @param window The application's window. Used to set the title and size of it.
	 */
	public GraphUI(ApfelGUI gui, JFrame w) {
		this.gui = gui;
		this.w = w;
	}
	
	/**
	 * Sets up the user interface, consisting of the graph itself and its caption.
	 */
	public void initUI(){
		//setting language-specific Strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			nameOld = Lang_GER.nameOld;
			applesL = Lang_GER.applesL;
			title = Lang_GER.graphWindow;
			restartL = Lang_GER.setUpWindow;
		} else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE){
			nameOld = Lang_FR.nameOld;
			applesL = Lang_FR.applesL;
			title = Lang_FR.graphWindow;
			restartL = Lang_FR.setUpWindow;
		} else {
			nameOld = Lang_EN.nameOld;
			applesL = Lang_EN.applesL;
			title = Lang_EN.graphWindow;
			restartL = Lang_EN.setUpWindow;
		}
		//initiating the graph-component
		graphP = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g){
				g.setColor(Color.GRAY);
				for(int i = 0; i < 6; i++) g.drawLine(30, 60*i + 30, 930, 60*i + 30);
				g.setColor(Color.BLACK);
				g.drawLine(30, 330, 930, 330);
				//Lines structuring Chart
				g.setFont(new Font("SansSerif", Font.PLAIN, 12));
				if(maxApples <= 25){
					g.drawString("25", 10, 30);
					g.drawString("20", 10, 90);
					g.drawString("15", 10, 150);
					g.drawString("10", 10, 210);
					g.drawString(" 5", 10, 270);
					g.drawString(" 0", 10, 330);
				} else if(maxApples <= 50){
					g.drawString("50", 10, 30);
					g.drawString("40", 10, 90);
					g.drawString("30", 10, 150);
					g.drawString("20", 10, 210);
					g.drawString("10", 10, 270);
					g.drawString(" 0", 10, 330);
				} else if(maxApples <= 75){
					g.drawString("75", 10, 30);
					g.drawString("60", 10, 90);
					g.drawString("45", 10, 150);
					g.drawString("30", 10, 210);
					g.drawString("15", 10, 270);
					g.drawString(" 0", 10, 330);
				} else if(maxApples <= 100){
					g.drawString("100", 4, 30);
					g.drawString("80", 10, 90);
					g.drawString("60", 10, 150);
					g.drawString("40", 10, 210);
					g.drawString("20", 10, 270);
					g.drawString(" 0", 10, 330);
				}
				for(int i = 0; i < 300; i++){
					if(i+1 <= totalMoves){
						drawGraphLine(g, 0, i);
						drawGraphLine(g, 1, i);
					}
				}
				//Filling in the graphs
			}
			
			public Dimension getPreferredSize(){
				return new Dimension(960, 360);
			}
			
			public Dimension getMinimumSize(){
				return new Dimension(960, 360);
			}
			
			public Dimension getMaximumSize(){
				return new Dimension(960, 360);
			}
		};
		graphP.setAlignmentX(Component.CENTER_ALIGNMENT);
		//...and its caption
		bottomP = new JPanel();
		bottomP.setLayout(new BoxLayout(bottomP, BoxLayout.Y_AXIS));
		
		thomasL = new JLabel("<HTML>&#9473;&nbsp;Thomas</HTML>");
		thomasL.setForeground(Color.RED);
		
		oldL = new JLabel("<HTML>&#9473;&nbsp;" + nameOld);
		oldL.setForeground(Color.BLUE);
		
		restart = new JButton(restartL);
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.drawUI(ApfelGUI.MENU);
			}
		});
		
		bottomP.add(thomasL);
		bottomP.add(oldL);
		bottomP.add(restart);
		//setting up myself
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(graphP);
		add(bottomP);
		//setting up window
		w.setTitle(title);
		w.setVisible(true);
		w.setSize(960, 420);
	}
	
	/**
	 * Sets the Gardens that this object will collect data about during the simulation
	 * @param garden An array of the two Gardens. Used to access their <code>totalApples</code>.
	 */
	public void setSimObjects(Garden[] garden){
		this.garden = garden;
	}
	
	/**
	 * Sets the current number of steps of the game and also collects data about the "concentration" of apples at this time.
	 * @param currentMoves The current number of moves.
	 */
	public void setCurrentMoves(int currentMoves){
		this.currentMoves = currentMoves;
		values[0][currentMoves] = (byte)garden[0].totalApples;
		values[1][currentMoves] = (byte)garden[1].totalApples;
	}
	
	/**
	 * Sets the total number of moves. Based on that information, an array to store the data of the simulation is created.
	 * The first value in this array Ð the number of apples that are in the garden to start with Ð is also set here.
	 * @param total The total number of moves.
	 * @param tom The amount of apples Thomas initially has in his Garden.
	 * @param old The amount of apples the elderly man initially has in his Garden.
	 */
	public void setTotalMoves(int total, int tom, int old){
		values = new byte[2][total+1];
		values[0][0] = (byte)tom;
		values[1][0] = (byte)old;
		totalMoves = total;
	}
	
	/**
	 * Sets the maximum amount of apples that can be in the game. Used to determine the scaling of the graph's y-axis.
	 * @param maxApples The maximum amount of apples.
	 */
	public void setMaxApples(int maxApples){
		this.maxApples = maxApples;
		if(maxApples <= 25) yAmp = 12;
		else if(maxApples <= 50) yAmp = 6;
		else if(maxApples <= 75) yAmp = 4;
		else if(maxApples <= 100) yAmp = 3;
	}
	
	/**
	 * Draws the line of the graph.
	 * @param g The <code>Graphics</code> object used to draw.
	 * @param c The current player.
	 * @param i The position on the x-axis.
	 */
	private void drawGraphLine(Graphics g, int c, int i){
		if(c == 0) 
			g.setColor(Color.RED);
		else 
			g.setColor(Color.BLUE);
		
		if(totalMoves > 300)
				g.drawLine(
						i*3 + 30,
						330 - values[c][(int)(i*totalMoves/900)]*yAmp,
						i*3 + 33,
						330 - values[c][(int)((i+1)*totalMoves/900)]*yAmp
						);
		else
				g.drawLine(
						(int)(i*900/totalMoves) + 30,
						330 - values[c][i]*yAmp,
						(int)((i+1)*900/totalMoves + 30),
						330 - values[c][i+1]*yAmp);
		//g.drawLine(xa, ya, xb, yb)
		/*try{
			g.drawLine(i*3 + 30, 330 - values[c][i]*20, (i+1)*3 + 30, 330 - values[c][i+1]*20);
			g.drawLine(i*3 + 29, 330 - values[c][i]*20 - 1, (i+1)*3 + 29, 330 - values[c][i+1]*20 - 1);
		} catch(ArrayIndexOutOfBoundsException e){}*/
		
	}
	
	/**
	 * Sets the player that currently is to move.
	 * @param currentPlayer The current player (duh).
	 */
	public void updateCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
