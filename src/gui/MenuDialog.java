package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.File;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import lang.Lang_EN;
import lang.Lang_FR;
import lang.Lang_GER;

/**
 * A dialog of the <code>MenuUI</code> surface that allows the user to make advanced preferences,
 * such as the "speed" of the Players or the amount of apples in their gardens.
 * @author Daniel Gotzens
 * @see MenuUI
 */
public class MenuDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	MenuUI menu;
	JPanel cp, rulesP, fileOutputP, buttonP;
	
	JSpinner thomasApples, oldApples, vThomas, vOld;
	JLabel 
		thomasL, oldL, thomasApplesL, oldApplesL, vThomasL, vOldL,
		outFile1, outFile2;
	JCheckBox outFiles;
	JButton file1Browse, file2Browse, cancel, ok;
	JFileChooser fc;
	ImageIcon textIcon;
	
	String
		nameOld, applesL, vL, vTip,
		outFilesL, outFilesTip, outFile1L, outFile2L, fileBrowse, fileChoose1, fileChoose2,
		cancelL;
	
	public boolean initiated;
	/**
	 * Class constructor.
	 * @param menu Later used to transfer output
	 */
	public MenuDialog(MenuUI menu){
		this.menu = menu;
	}
	
	/**
	 * Initiates the user interface.
	 */
	public void initUI(){
		//setting language-specific strings
		if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY){
			nameOld = Lang_GER.nameOld;
			applesL = Lang_GER.applesL;
			vL = Lang_GER.vL;
			vTip = Lang_GER.vTip;
			outFilesL = Lang_GER.outFiles;
			outFilesTip = Lang_GER.outFilesTip;
			outFile1L = Lang_GER.outFile1;
			outFile2L = Lang_GER.outFile2;
			fileBrowse = Lang_GER.fileBrowse;
	 		fileChoose1 = Lang_GER.fileChoose1;
	 		fileChoose2 = Lang_GER.fileChoose2;
			cancelL = Lang_GER.cancel;
		 } else if(Locale.getDefault() == Locale.FRENCH || Locale.getDefault() == Locale.FRANCE){
	 		nameOld = Lang_FR.nameOld;
	 		applesL = Lang_FR.applesL;
	 		vL = Lang_FR.vL;
	 		vTip = Lang_FR.vTip;
	 		outFilesL = Lang_FR.outFiles;
	 		outFilesTip = Lang_FR.outFilesTip;
	 		outFile1L = Lang_FR.outFile1;
	 		outFile2L = Lang_FR.outFile2;
	 		fileBrowse = Lang_FR.fileBrowse;
	 		fileChoose1 = Lang_FR.fileChoose1;
	 		fileChoose2 = Lang_FR.fileChoose2;
	 		cancelL = Lang_FR.cancel;
		 } else {
			nameOld = Lang_EN.nameOld;
			applesL = Lang_EN.applesL;
			vL = Lang_EN.vL;
			vTip = Lang_EN.vTip;
			outFilesL = Lang_EN.outFiles;
			outFilesTip = Lang_EN.outFilesTip;
			outFile1L = Lang_EN.outFile1;
			outFile2L = Lang_EN.outFile2;
			fileBrowse = Lang_EN.fileBrowse;
	 		fileChoose1 = Lang_EN.fileChoose1;
	 		fileChoose2 = Lang_EN.fileChoose2;
			cancelL = Lang_EN.cancel;
		 }
		//Setting up file chooser
		fc = new JFileChooser();
		FileFilter filter = new FileFilter(){

			@Override
			public boolean accept(File f) {
				return 	f.getName().endsWith(".txt") ||
					f.getName().endsWith(".TXT") ||
					f.getName().endsWith(".text") ||
					f.getName().endsWith(".TEXT") ||
					f.isDirectory();
			}
			
			@Override
			public String getDescription() {
				if(Locale.getDefault() == Locale.GERMAN || Locale.getDefault() == Locale.GERMANY)
					return "Textdateien";
				else
					return "plain text files";
			}		
		};
		
		fc.setMultiSelectionEnabled(false);
		fc.setFileHidingEnabled(true);
		fc.addChoosableFileFilter(filter);
		textIcon = new ImageIcon("/Users/daniel/Downloads/plain_text_icon.png", "a plain text icon");
		System.out.println("hell yeah");
		
		//setting up rulesP's contents
		//creating local JPanels
		JPanel cTable, c1, c2, nameP1, nameP2, applesP1, applesP2, vPane1, vPane2;
		cTable = new JPanel();
		nameP1 = new JPanel();
		nameP2 = new JPanel();
		c1 = new JPanel();
		c2 = new JPanel();
		applesP1 = new JPanel();
		applesP2 = new JPanel();
		vPane1 = new JPanel();
		vPane2 = new JPanel();
		//layouts, sizes and opacity
		cTable.setLayout(new GridLayout(1, 2));
		cTable.setPreferredSize(new Dimension(500, 100));
		cTable.setOpaque(false);
		
		nameP1.setPreferredSize(new Dimension(250, 30));
		nameP1.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameP1.setOpaque(false);
		
		nameP2.setPreferredSize(new Dimension(250, 30));
		nameP2.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameP2.setOpaque(false);
		
		c1.setLayout(new BoxLayout(c1, BoxLayout.Y_AXIS));
		c1.setPreferredSize(new Dimension(250, 120));
		c1.setOpaque(false);
		
		c2.setLayout(new BoxLayout(c2, BoxLayout.Y_AXIS));
		c2.setPreferredSize(new Dimension(250, 120));
		c2.setOpaque(false);
		
		applesP1.setPreferredSize(new Dimension(250, 30));
		applesP1.setLayout(new FlowLayout(FlowLayout.LEFT));
		applesP1.setOpaque(false);
		
		applesP2.setPreferredSize(new Dimension(250, 30));
		applesP2.setLayout(new FlowLayout(FlowLayout.LEFT));
		applesP2.setOpaque(false);
		
		vPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
		vPane1.setPreferredSize(new Dimension(250, 30));
		vPane1.setOpaque(false);
		
		vPane2.setLayout(new FlowLayout(FlowLayout.LEFT));
		vPane2.setPreferredSize(new Dimension(250, 30));
		vPane2.setOpaque(false);
		
		//Constructing the non-JPanel-components
		thomasApples = new JSpinner(new SpinnerNumberModel(10, 0, 99, 1));
		thomasApplesL = new JLabel(applesL);
		oldApples = new JSpinner(new SpinnerNumberModel(5, 0, 99, 1));
		oldApplesL = new JLabel(applesL);
		vThomas = new JSpinner(new SpinnerNumberModel(1, -1, 1000, 1));
		vThomasL = new JLabel(vL);
		vOld = new JSpinner(new SpinnerNumberModel(-1, -1, 1000, 1));
		vOldL = new JLabel(vL);
		thomasL = new JLabel("Thomas");
		oldL = new JLabel(nameOld);
		//setting their sizes
		Dimension d = new Dimension(80, 30);
		thomasApples.setPreferredSize(d);
		oldApples.setPreferredSize(d);
		vThomas.setPreferredSize(d);
		vOld.setPreferredSize(d);
		//Setting font of headlines
		thomasL.setFont(new Font("Display", Font.BOLD, 14));
		oldL.setFont(new Font("Display", Font.BOLD, 14));
		//filling the JPanels
		nameP1.add(thomasL);
		nameP2.add(oldL);
		
		applesP1.add(thomasApples);
		applesP1.add(thomasApplesL);
		
		applesP2.add(oldApples);
		applesP2.add(oldApplesL);
		
		vPane1.add(vThomas);
		vPane1.add(vThomasL);
		
		vPane2.add(vOld);
		vPane2.add(vOldL);
		
		c1.add(nameP1);
		c1.add(applesP1);
		c1.add(vPane1);
		
		c2.add(nameP2);
		c2.add(applesP2);
		c2.add(vPane2);
		
		cTable.add(c1);
		cTable.add(c2);
		
		//setting up fileOutputP's contents
		//creating local JPanels
		JPanel outFilesP, file1BrowseP, file2BrowseP;
		
		outFilesP = new JPanel();
		outFilesP.setLayout(new FlowLayout(FlowLayout.LEFT));
		file1BrowseP = new JPanel();
		file1BrowseP.setLayout(new FlowLayout(FlowLayout.LEFT));
		file2BrowseP = new JPanel();
		file2BrowseP.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//setting up low-level components
		outFiles = new JCheckBox(outFilesL);
		file1Browse = new JButton(fileBrowse);
		outFile1 = new JLabel(outFile1L, textIcon, JLabel.CENTER);
		file2Browse = new JButton(fileBrowse);
		outFile2 = new JLabel(outFile1L, textIcon, JLabel.CENTER);
		
		//adding ActionListener to them
		outFiles.addActionListener(menu.ml);
		file1Browse.addActionListener(menu.ml);
		file2Browse.addActionListener(menu.ml);
		
		//adding to top-level panels
		outFilesP.add(outFiles);
	
		file1BrowseP.add(file1Browse);
		file1BrowseP.add(outFile1);
		
		file2BrowseP.add(file2Browse);
		file2BrowseP.add(outFile2);
		
		//setting up buttonP's contents
		cancel = new JButton(cancelL);
		cancel.addActionListener(menu.ml);
		ok = new JButton("OK");
		ok.addActionListener(menu.ml);
		
		//adding components to rulesP
		rulesP = new JPanel();
		rulesP.add(cTable);
		
		//adding to fileOutputP
		fileOutputP = new JPanel();
		fileOutputP.setLayout(new BoxLayout(fileOutputP, BoxLayout.Y_AXIS));
		fileOutputP.setMaximumSize(new Dimension(20000, 100));
		
		fileOutputP.add(outFilesP);
		fileOutputP.add(file1BrowseP);
		fileOutputP.add(file2BrowseP);
		
		//adding to buttonP
		buttonP = new JPanel();
		buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
		buttonP.setMaximumSize(new Dimension(400000, 40));
		
		buttonP.add(cancel);
		buttonP.add(Box.createHorizontalGlue());
		buttonP.add(ok);
		
		//setting up self
		cp = new JPanel();
		setContentPane(cp);
		
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cp.add(rulesP);
		cp.add(new JSeparator(JSeparator.HORIZONTAL));
		cp.add(fileOutputP);
		cp.add(Box.createVerticalGlue());
		//cp.add(buttonP);
		
		refreshUI();
		setMinimumSize(new Dimension(510, 280));
		setSize(520, 350);
		setVisible(true);
		initiated = true;
	}

	public void refreshUI() {
		outFile1.setEnabled(outFiles.isSelected());
		outFile2.setEnabled(outFiles.isSelected());
		file1Browse.setEnabled(outFiles.isSelected());
		file2Browse.setEnabled(outFiles.isSelected());		
	}
}
