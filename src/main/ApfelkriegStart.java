package main;

import javax.swing.SwingUtilities;

import gui.ApfelGUI;

/**
 * Starts up a new Applewar.
 * @author Daniel Gotzens
 */
public class ApfelkriegStart {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  new ApfelGUI().drawUI(ApfelGUI.MENU);
		      }
		    });
	}
}