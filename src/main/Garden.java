package main;

/**
 * This is the Garden Thomas and the Elderly Man campaign on.
 * Its purpose is mainly to store the data about the apples on it.
 * @author Daniel Gotzens
 *
 */
public class Garden {
	boolean[][] appleOnField = new boolean[10][10]; //x, y
	public int totalApples;
	
	/**
	 * Class constructor.
	 * @param startApples The apples that initially are in the Garden.
	 */
	public Garden(int startApples) {
		this.spreadApples(startApples);
	}
	
	/**
	 * Spreads a certain amount of apples at random in the garden.
	 * @param numberOfApples The number of apples to spread.
	 */
	public void spreadApples(int numberOfApples) {
		totalApples += numberOfApples;
		int xTestPos = (int)(Math.random()*10);
		int yTestPos = (int)(Math.random()*10);
		for(int i = 0; i < numberOfApples; i++){
			while(appleOnField[xTestPos][yTestPos] == true){
				xTestPos = (int)(Math.random()*10);
				yTestPos = (int)(Math.random()*10);
			}
			appleOnField[xTestPos][yTestPos] = true;
		}
	}
	
	/**
	 * Removes an apple at the specified coordinates.
	 * @param x The x-coordinate of the apple.
	 * @param y The y-coordinate of the apple.
	 */
	public void removeApple(int x, int y) {
		if(appleOnField[x][y]) totalApples--;
		appleOnField[x][y] = false;
	}
	
	/**
	 * To find out whether or not there is an apple at the specified coordinates.
	 * @param x The x-coordinate of the apple.
	 * @param y The y-coordinate of the apple.
	 * @return <code>true</code> if there is an apple, <code>false</code> if there ain't.
	 */
	public boolean isAppleOnField(int x, int y) {
		return appleOnField[x][y];
	}
}