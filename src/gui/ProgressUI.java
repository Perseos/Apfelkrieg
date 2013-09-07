package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import lang.Lang_EN;
import lang.Lang_FR;
import lang.Lang_GER;
  
/**
 * This surface shows a progress bar while the simulation is running.
 * @author Daniel Gotzens
 * @see ApfelGUI
 */
public class ProgressUI extends JPanel{
	private static final long serialVersionUID = 1L;
	boolean initiated; 
	JFrame w;
	JPanel p;
	JLabel l;
	JProgressBar pb;
	JButton quit;
	int currentMoves, totalMoves;
	String title, stepL, done, quitL;

	/**
	 * Class constructor.
	 * @param window The application's window whose size and title will have to be set later on.
	 */
	public ProgressUI(JFrame window) {
		w = window;
	}
	
	/**
	 * Sets up the user interface.
	 */
	public void initUI() {
		//setting language-specific Strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			title = Lang_GER.progressWindow;
			stepL = Lang_GER.stepL;
			done = Lang_GER.done;
			quitL = Lang_GER.quit;
		} else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE) {
			title = Lang_FR.progressWindow;
			stepL = Lang_FR.stepL;
			done = Lang_FR.done;
			quitL = Lang_FR.quit;	
		} else {
			title = Lang_EN.progressWindow;
			stepL = Lang_EN.stepL;
			done = Lang_EN.done;
			quitL = Lang_EN.quit;		
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		l = new JLabel();
		l.setText(stepL + String.valueOf(currentMoves) + "/" + String.valueOf(totalMoves));
		l.setBorder(new EmptyBorder(20, 20, 20, 20));
		pb = new JProgressBar(0, 100);
		pb.setBorder(new EmptyBorder(20, 70, 20, 70));
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		quit = new JButton(quitL);
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		p.add(quit);
		p.add(Box.createRigidArea(new Dimension(55, 0)));
		//adding to this
		add(l);
		add(pb);
		add(p);
		//setup window
		w.setSize(600, 200);
		w.setTitle(title);
		initiated = true;
	}
	
	/**
	 * Refreshes the user interface. 
	 */
	public void refreshUI(){
		l.setText(stepL + " " + String.valueOf(currentMoves) + "/" + String.valueOf(totalMoves));
		if(currentMoves >= totalMoves)
			l.setText(done);
	}
	
	/**
	 * Sets the total amount of steps in the simulation. Required to make the <code>JProgressBar</code> work.
	 * @param totalMoves The total amount of steps.
	 */
	public void setTotalMoves(int totalMoves){
		if(initiated){
			this.totalMoves = totalMoves;
			pb.setMaximum(totalMoves);
		}
	}
	
	/**
	 * Sets the current number of steps.
	 * @param currentMoves the current number of steps.
	 */
	public void updateCurrentMoves(int currentMoves){
		this.currentMoves = currentMoves +1;
		pb.setValue(currentMoves +1);
		refreshUI();
	}
}
