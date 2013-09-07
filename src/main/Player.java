package main;

/**
 * A simulated player that can either decide the Direction to walk into or walk around at random
 * @see	Garden
 */
public class Player {
	Garden ownGarden, enemyGarden;
	/**
	 * The number of apples the Player currently carries on him. Apples are thrown into the other garden after each move
	 * @see Player#walkRandom()
	 * @see Player#walk()
	 */
	public int appleCache;
	/** The player's current Position*/
	public int xPos, yPos;
	
	private byte[] direction = new byte[2];
	/*
	 * 		x	y
	 * N: 	0,	-1
	 * NE: 	1,	-1
	 * E:	1,	0
	 * SE:	1,	1
	 * S:	0,	1
	 * SW:	-1,	1
	 * W:	-1, 0
	 * NW:	-1,	-1
	 */
	
	/**
	 *  @param	ownGarden	The garden the player walks and collects apples on
	 *  @param	enemyGarden	The other player's garden that the Player will throw the collected apples on
	 *  @see	Garden
	 */
	public Player(Garden ownGarden, Garden enemyGarden){
		this.ownGarden = ownGarden;
		this.enemyGarden = enemyGarden;
	}
	
	/** The player walks to a randomly generated field of the eight fields surrounding him. */
	public void walkRandom(){
		int xRandom = 10, yRandom = 10;
		while(xPos + xRandom > 9 || xPos + xRandom < 0 || yPos + yRandom > 9 || yPos + yRandom < 0){
			switch((int)(Math.random()*8)){
				case 0: xRandom = 0; yRandom = -1; break;
				case 1: xRandom = 1; yRandom = -1; break;
				case 2: xRandom = 1; yRandom = 0; break;
				case 3: xRandom = 1; yRandom = 1; break;
				case 4: xRandom = 0; yRandom = 1; break;
				case 5: xRandom = -1; yRandom = 1; break;
				case 6: xRandom = -1; yRandom = 0; break;
				case 7: xRandom = -1; yRandom = -1; break;
			}
		}
		xPos += xRandom;
		yPos += yRandom;
		if(ownGarden.isAppleOnField(xPos, yPos)){
			ownGarden.removeApple(xPos, yPos);
			appleCache++;
		}
	}
	
	/** The player walks in the previously set direction; he stops once he either reaches the garden's boundaries or an apple.
	 * @see Player#setDirection(int)
	 */
	public void walk(){
		if(xPos + direction[0] > 9 || xPos + direction[0] < 0 || yPos + direction[1] > 9 || yPos + direction[1] < 0){
			xPos += direction[0];
			yPos += direction[1];
		}
		if(ownGarden.isAppleOnField(xPos, yPos)){
			ownGarden.removeApple(xPos, yPos);
			appleCache++;
		}
		

	}
	
	/** 
	 * The direction that the player will walk into is set.
	 * <p>
	 * If apples can be reached in multiple directions, the decision between them happens at random.
	 * The same applies if no apples at all can be reached.
	 * @param	dice	The distance the player can cover in this move.
	 */
	public void setDirection(int dice){
		boolean[] testDirect = new boolean[8];
		byte xTest = 0, yTest = 0, possibleDirections = 0;
		//checking if there are any apples reachable
		for(int i = 0; i < 8; i++) {
			switch(i){
				case 0: xTest = 0; yTest = -1; break;
				case 1: xTest = 1; yTest = -1; break;
				case 2: xTest = 1; yTest = 0; break;
				case 3: xTest = 1; yTest = 1; break;
				case 4: xTest = 0; yTest = 1; break;
				case 5: xTest = -1; yTest = 1; break;
				case 6: xTest = -1; yTest = 0; break;
				case 7: xTest = -1; yTest = -1; break;
			}
			for(int j = 0; j < dice; j++)
				if(xPos + xTest*j > 9 || xPos + xTest*j < 0 || yPos + yTest*j > 9 || yPos + yTest*j < 0)
					if(ownGarden.isAppleOnField(xPos + xTest*j, yPos + yTest*j)) testDirect[i] = true;
		}
		//counting possible directions
		for(boolean d : testDirect) if(d) possibleDirections++;
		//assigning values for direction
		if(possibleDirections > 1){
			int i;
			do i = (int) (Math.random()*8);
			while(!testDirect[i]);
			switch(i){
				case 0: direction[0] = 0; direction[1] = -1; break;
				case 1: direction[0] = 1; direction[1] = -1; break;
				case 2: direction[0] = 1; direction[1] = 0; break;
				case 3: direction[0] = 1; direction[1] = 1; break;
				case 4: direction[0] = 0; direction[1] = 1; break;
				case 5: direction[0] = -1; direction[1] = 1; break;
				case 6: direction[0] = -1; direction[1] = 0; break;
				case 7: direction[0] = -1; direction[1] = -1; break;
			}
		} else if(possibleDirections == 0){
			int i = (int) (Math.random()*8);
			switch(i){
				case 0: direction[0] = 0; direction[1] = -1; break;
				case 1: direction[0] = 1; direction[1] = -1; break;
				case 2: direction[0] = 1; direction[1] = 0; break;
				case 3: direction[0] = 1; direction[1] = 1; break;
				case 4: direction[0] = 0; direction[1] = 1; break;
				case 5: direction[0] = -1; direction[1] = 1; break;
				case 6: direction[0] = -1; direction[1] = 0; break;
				case 7: direction[0] = -1; direction[1] = -1; break;
			}
		} else {
			direction[0] = xTest;
			direction[1] = yTest;
		}
	}
	
	/**
	 * The player's <source>appleCache</source> is emptied into the other garden.
	 * @see Garden#spreadApples(int)
	 */
	public void throwApples(){
		enemyGarden.spreadApples(appleCache);
		appleCache = 0;
	}
}