package lang;

/**
 * This class is storing all english language-specific strings required in the application.
 * @author Daniel Gotzens
 */
public class Lang_EN {
	//settings-section
	public static String settings = "Game rules";
	public static String numberStepsL = "Moves";
	public static String applesL = "Apples";
	public static String vL = "Dice-modifier";
	public static String vTip = "<html>The number added to the result of rolling the dice.<br/>Anyone knows a better word for that?</html>";
	public static String charsAI = "Intelligent players";
	public static String aiTip = "Players move based on the game’s rules. Consult Help";
	//output-section
	public static String output = "Output";
	public static String outFiles = "Save output";
	public static String outFilesTip = "Saves the each moves's total number of apples in two files";
	public static String outFile1 = "Thomas's output-file";
	public static String outFile2 = "The elderly man's output-file";
	public static String fileBrowse = "Choose file...";
	public static String visualize = "Visualize simulation";
	public static String showGraphs = "Graphs";
	public static String graphsTip = "Show the results of the simulation in a total-apples-over-time-graph";
	public static String showSim = "Show game boards";
	public static String simTip = "Show the boards live during the Simulation";
	//window-titles
	public static String setUpWindow = "New Simulation";
	public static String pitchWindow = "Applewar-Board";
	public static String progressWindow = "Simulation running...";
	public static String graphWindow = "Results";
	public static String fileChoose1 = "Choose output-file for Thomas's data";
	public static String fileChoose2 = "Choose output-file for the Elderly Man's data";
	//misc
	public static String nameOld = "Elderly Man";
	public static String quit = "Quit";
	public static String start = "Start";
	public static String stepL = "Step";
	public static String done = "Done!";
	public static String nameL = "Player";
	public static String cancel = "Cancel";
	//error-messages
	public static String appleOverflow = "<HTML>To prevent the boards from overflowing,<BR/>" +
			"only a total of 100 apples are allowed.</HTML>";
	public static String outfileError = "You chose the same output-file twice.";
	//error-titles
	public static String appleOverflowT = "Too many apples";
	public static String outfileErrorT = "Identical output files";
}