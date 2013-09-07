package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Garden;
import main.Player;

/**
 * This is the Component displaying the Applewar-Board in the <code>PitchUI</code>-surface.
 * @see PitchUI
 * @author Daniel Gotzens
 */
public class BoardPane extends JPanel {
	private static final long serialVersionUID = 1L;
	Garden garden;
	Player player;
	
	/**
	 * This constructor assigns the board the <code>Garden</code> and <code>Player</code> it will display.
	 * @param garden
	 * @param player
	 */
	public BoardPane(Garden garden, Player player){
		this.garden = garden;
		this.player = player;
	}
	
	/**
	 * Draws the complete 10x10 Applewar-Board with Apples and the Player on top.
	 */
	public void paintComponent(Graphics g){
		for(int x = 0; x < 500; x += 50){
			for(int y = 0; y < 500; y += 50){
				g.setColor(Color.WHITE);
				g.fillRect(x, y, 50, 50);
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x + 50, y);
				g.drawLine(x, y, x, y + 50);
		 
				if(player.xPos == x/50 && player.yPos == y/50){
					g.setColor(Color.GRAY);
					g.fillRect(x + 10, y + 10, 30, 30);
					g.setColor(Color.BLACK);
					g.drawRect(x + 10, y + 10, 30, 30);
				} else if(garden.isAppleOnField(x/50, y/50)){
					g.setColor(Color.RED);
					g.fillRect(x + 10, y + 10, 30, 30);
					g.setColor(Color.BLACK);
					g.drawRect(x + 10, y + 10, 30, 30);				
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(x + 10, y + 10, 30, 30);
				}
			}
		}
		g.setColor(Color.BLACK);
		g.drawLine(0, 499, 499, 499);
		g.drawLine(499, 0, 499, 499);
	}
	
	/**
	 * Re-draws the player and the apples but not the board underneath
	 */
	public void refresh(){
		Graphics g = getGraphics();
		for(int x = 0; x < 500; x += 50){
			for(int y = 0; y < 500; y += 50){
				if(player.xPos == x/50 && player.yPos == y/50){
					g.setColor(Color.GRAY);
					g.fillRect(x + 10, y + 10, 30, 30);
					g.setColor(Color.BLACK);
					g.drawRect(x + 10, y + 10, 30, 30);
				} else if(garden.isAppleOnField(x/50, y/50)){
					g.setColor(Color.RED);
					g.fillRect(x + 10, y + 10, 30, 30);
					g.setColor(Color.BLACK);
					g.drawRect(x + 10, y + 10, 30, 30);				
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(x + 10, y + 10, 31, 31);
				}
			}
		}
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(500, 500);		
	}

}
