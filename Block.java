
public class Block implements Comparable<Block> {

	private int r1, c1, r2, c2;
	public static boolean debuggingFlag = false;

	/*
	 * Abstract:		Block constructor
	 * Prerequisite: 	Coordinates must be valid
	 * Input:			Upper left and bottom right coordinates
	 * Return value:	Instance of Block
	 */
	public Block(int r1, int c1, int r2, int c2) {
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
	}


	/*
	 * Abstract:		Block copy constructor
	 * Prerequisite: 	None
	 * Input:			Block to copy
	 * Return value:	Instance of Block
	 */
	public Block(Block o) {
		r1 = o.r1;
		c1 = o.c1;
		r2 = o.r2;
		c2 = o.c2;
	}


	/*
	 * Abstract:		Getter for variable r1
	 * Prerequisite: 	None
	 * Input:			None
	 * Return value:	Value of internal variable r1
	 */
	public int r1() {
		return r1;
	}


	/*
	 * Abstract:		Getter for variable c1
	 * Prerequisite: 	None
	 * Input:			None
	 * Return value:	Value of internal variable c1
	 */
	public int c1() {
		return c1;
	}


	/*
	/*
	 * Abstract:		Getter for variable r2
	 * Prerequisite: 	None
	 * Input:			None
	 * Return value:	Value of internal variable r2
	 */
	public int r2() {
		return r2;
	}


	/*
	 * Abstract:		Getter for variable c2
	 * Prerequisite: 	None
	 * Input:			None
	 * Return value:	Value of internal variable c2
	 */
	public int c2() {
		return c2;
	}


	/*
	 * Abstract:		Move the block one step in the given direction
	 * Prerequisite: 	None
	 * Input:			Direction of the wanted move
	 * Return value:	None
	 */
	public void move(Board.Direction dir) {
		switch (dir) {
		case DOWN: r1++; r2++; break;
		case LEFT: c1--; c2--; break;
		case UP: r1--; r2--; break;
		case RIGHT: c1++; c2++; break;
		}
	}


	/*
	 * Abstract:		Cancel the move operated in the given direction
	 * Prerequisite: 	None
	 * Input:			Direction of the initial move
	 * Return value:	None
	 */
	public void unmove(Board.Direction dir) {
		switch (dir) {
		case DOWN: r1--; r2--; break;
		case LEFT: c1++; c2++; break;
		case UP: r1++; r2++; break;
		case RIGHT: c1--; c2--; break;
		}
	}


	/*
	 * Abstract:		Check if the given block overlaps the current one
	 * Prerequisite: 	None
	 * Input:			Block to test
	 * Return value:	True if there is an overlap
	 */
	public boolean overlaps(Block o) {
		return r1 <=  o.r2 && r2 >= o.r1 && c1 <= o.c2 && c2 >= o.c1;
	}


	/*
	 * Abstract:		Compute an integer used to sort blocks
	 * Prerequisite: 	None
	 * Input:			Another block
	 * Return value:	Integer representing the difference between the given
	 * 					block and the current one
	 */
	public int compareTo(Block o) {
		// x and y must be smaller than 256 = 2^8
		return ((r1 - o.r1) << 8) + c1 - o.c1;
	}


	/*
	 * Abstract:		Check if two blocks are equal
	 * Prerequisite: 	None
	 * Input:			Block to compare to the current one
	 * Return value:	True if the block are equal
	 */
	public boolean equals(Object o) {
		Block b = (Block) o;
		return r1 == b.r1 && c1 == b.c1 && r2 == b.r2 && c2 == b.c2;
	}


	/*
	 * Abstract:		Give a string representation of the state of the block
	 * Prerequisite: 	None
	 * Input:			None
	 * Return value:	String representation
	 */
	public String toString() {
		return "" + r1 + " " + c1 + " " + r2 + " " + c2;
	}

}