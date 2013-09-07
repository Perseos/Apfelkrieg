package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class displays a dice. It is used in the surface <code>PitchUI</code>
 * @author Daniel Gotzens
 * @see PitchUI
 */
public class DicePane extends JPanel{
	private static final long serialVersionUID = 1L;
	/** The current number displayed on the dice */
	public int dice;
	private int v1, v2, v3;
	
	/** Class constructor */
	public DicePane(){
		v1 = 9;
		v2 = 36;
		v3 = 63;
	}
	
	/**
	 * Paints the dice.
	 */
	public void paintComponent(Graphics g){	
		switch(dice){
		case 1:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v2, v2, 9, 9);
			break;				
		case 2:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v1, v1, 9, 9);
			g.fillRect(v3, v3, 9, 9);
			break;
		case 3:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v1, v1, 9, 9);
			g.fillRect(v2, v2, 9, 9);
			g.fillRect(v3, v3, 9, 9);
			break;
		case 4:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v1, v1, 9, 9);
			g.fillRect(v1, v3, 9, 9);
			g.fillRect(v3, v1, 9, 9);
			g.fillRect(v3, v3, 9, 9);
			break;
		case 5:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v1, v1, 9, 9);
			g.fillRect(v1, v3, 9, 9);
			g.fillRect(v2, v2, 9, 9);
			g.fillRect(v3, v1, 9, 9);
			g.fillRect(v3, v3, 9, 9);
			break;
		case 6:
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 81, 81);
			g.setColor(Color.WHITE);
			g.fillRect(v1, v1, 9, 9);
			g.fillRect(v3, v1, 9, 9);
			g.fillRect(v1, v2, 9, 9);
			g.fillRect(v3, v2, 9, 9);
			g.fillRect(v1, v3, 9, 9);
			g.fillRect(v3, v3, 9, 9);			
			break;
		default:
			g.setColor(Color.RED);
			g.drawString("Error", 5, 40);
			
		}
		//draws Dice
	}
	
	/**
	 * Sets the number the dice should display.
	 * @param d The number of spots on the dice.
	 */
	public void setDice(int d){
		dice = d;
		paintComponent(getGraphics());
	}
	
	public Dimension getMinimumSize(){
		return new Dimension(81, 81);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(81, 81);
	}
	
	public Dimension getMaximumSize(){
		return new Dimension(81, 81);
	}
}
