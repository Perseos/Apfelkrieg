package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import lang.*;
import main.Garden;
import main.Player;

/**
 * This is the table shown in the surface <code>PitchUI</code>. It aggregates data about the Player's position etc.
 * @see PitchUI
 * @author Daniel Gotzens
 */
public class DebugTable extends JPanel{
	private static final long serialVersionUID = 1L;
	Garden[] g;
	Player[] c;
	
	String nameL;
   
    Border b = new CompoundBorder(
    		new LineBorder(Color.BLACK, 1),
    		new EmptyBorder(10,10,10,10)
    	);
    
	JLabel[] header = new JLabel[4];
	JLabel[] name = new JLabel[2];
	JLabel[][] value = new JLabel[2][3];
	
	/**
	 * Creates a table showing the Players's positions, their <code>appleCache</code> and the <code>totalApples</code> of their Garden.
	 * @param g An array of the two Gardens Ð used to access their <code>totalApples</code>
	 * @param c An array of the two Players Ð used to access their <code>appleCache, <code>xPos</code> and <code>yPos</code>
	 * @see Garden
	 * @see Player
	 */
	public DebugTable(Garden[] g, Player[] c){
		//setting language-specific string
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY)
			nameL = Lang_GER.nameL;	
		 else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE) 
			nameL = Lang_FR.nameL;	
		 else 
			nameL = Lang_EN.nameL;		
		setOpaque(false);
		this.g = g;
		this.c = c;
		setLayout(new GridLayout(3, 4));
		setBorder(new CompoundBorder(
				new EmptyBorder(10, 20, 10, 20),
				new LineBorder(Color.BLACK)
				)
		);		
		
		header[0] = new JLabel(nameL);
		header[1] = new JLabel("Position");
		header[2] = new JLabel("appleCache");
		header[3] = new JLabel("totalApples");
		for(int i = 0; i<4; i++){
			header[i].setFont(new Font("Sans", Font.BOLD, 12));
			header[i].setBorder(b);
			header[i].setOpaque(true);
			header[i].setBackground(new Color(58, 78, 126));
			header[i].setForeground(Color.WHITE);
			add(header[i]);
		}
		
		name[0] = new JLabel("Thomas");
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY)
			name[1] = new JLabel(Lang_GER.nameOld);  
		else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE)
			name[1] = new JLabel(Lang_FR.nameOld);
		else
			name[1] = new JLabel(Lang_EN.nameOld);
		
		for(int i = 0; i<=1; i++){
			value[i][0] = new JLabel(c[i].xPos + "," + c[i].yPos);
			value[i][1] = new JLabel(String.valueOf(c[i].appleCache));
			value[i][2] = new JLabel(String.valueOf(g[i].totalApples));
			name[i].setFont(new Font("Sans", Font.ITALIC, 12));
			name[i].setBorder(b);
			name[i].setOpaque(true);
			name[i].setBackground(new Color(94, 137, 177));
			name[i].setForeground(Color.WHITE);
			add(name[i]);
			for(int j = 0; j<=2; j++){
				value[i][j].setBorder(b);
				value[i][j].setOpaque(true);
				value[i][j].setBackground(Color.WHITE);
				add(value[i][j]);
			}
		}
	}
	
	/**
	 * Highlights the currently acting player's table row in a bright gray.
	 * @param p the current player
	 */
	public void updateCurrentPlayer(int p){
			for(int j = 0; j<=2; j++){
				if(p == 0){
					value[0][j].setBackground(new Color(0xcc, 0xcc, 0xcc));
					value[1][j].setBackground(Color.WHITE);
				} else {
					value[1][j].setBackground(new Color(0xcc, 0xcc, 0xcc));
					value[0][j].setBackground(Color.WHITE);
				}
			}
	}
	
	/**
	 * Refreshes the content of the different table cells.
	 */
	public void refresh(){
		for(int i = 0; i<=1; i++){
			value[i][0].setText(c[i].xPos + " | " + c[i].yPos);
			value[i][1].setText(String.valueOf(c[i].appleCache));
			value[i][2].setText(String.valueOf(g[i].totalApples));
		}
		validate();
	}
}
