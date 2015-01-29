import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Board {

	private int height, width;
	private List<Block> blocks;
	public static boolean debuggingFlag;
	private ArrayList<String> moves;

	public enum Direction {
		UP, LEFT, DOWN, RIGHT
	};

	/*
	 * Abstract: Board constructor with no dimensions Prerequisite: Input must
	 * be valid Input: None Return value: Instance of Board
	 */
	public Board() {
		height = 0;
		width = 0;
		blocks = new ArrayList<Block>();
		moves = new ArrayList<String>();
	}

	/*
	 * Abstract: Board constructor with dimensions Default constructor to
	 * contain a mutable configuration Prerequisite: Input must be valid Input:
	 * Dimensions of the board Return value: None
	 */
	public Board(int h, int w) {
		height = h;
		width = w;
		blocks = new ArrayList<Block>();
		moves = new ArrayList<String>();
	}

	public Board(Board currentBoard) {
		height = currentBoard.height;
		width = currentBoard.width;
		moves = new ArrayList<String>();
		blocks = new ArrayList<Block>();
		for (Block b : currentBoard.blocks) {
			blocks.add(new Block(b));
		}
		for(String s: currentBoard.moves){
			moves.add(s);
		}
	}

	/*
	 * Abstract: Getter for variable height Prerequisite: None Input: None
	 * Return value: Value of internal variable height
	 */
	public int height() {
		return height;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	/*
	 * Abstract: Getter for variable width Prerequisite: None Input: None Return
	 * value: Value of internal variable width
	 */
	public int width() {
		return width;
	}
	
	/*
	 * Abstract: Getter for variable blocks Prerequisite: None Input: None
	 * Return value: Value of internal variable blocks
	 */
	public List<Block> blocks() {
		return blocks;
	}

	/*
	 * Abstract: Add a block to the board Prerequisite: Valid input block Input:
	 * Block to add Return value: None
	 */
	public void addBlock(Block b) {
		blocks.add(b);
		Collections.sort(blocks);
	}

	/*
	 * Abstract: Check if the current configuration matches the objective
	 * Prerequisite: Valid current and input configurations Input: Final board
	 * objective Return value: True if the objective is achieved
	 */
	public boolean isGoalReached(Board goalBoard) {
		for (Block finalBlock : goalBoard.blocks) {
			boolean found = false;
			for (Block initBlock : blocks)
				if (initBlock.equals(finalBlock)) {
					found = true;
					break;
				}
			if (!found)
				return false;
		}
		return true;
	}

	/*
	 * Abstract: Generate all possible moves from the current configuration
	 * Prerequisite: Current configuration is valid Input: None Return value:
	 * List of the possible moves
	 */
	public List<String> generatePossibleMoves() {
		List<String> possibleMoves = new ArrayList<>();

		// Try to move each block in all directions
		// Store the move if it is legal
		for (Block block : blocks) {

			// Copy the current list of blocks and remove the block we are
			// trying to move
			ArrayList<Block> blocksCopy = new ArrayList<>(blocks);
			blocksCopy.remove(block);

			// Work on a copy of the current block to not alter the original
			// configuration
			// Move it in each direction, if it does not cross the limits of the
			// board
			// check if it overlaps with another block and finally keep it as a
			// possible move
			Block copy = new Block(block);
			for (Direction dir : Direction.values()) {
				copy.move(dir);
				if (!crossLimits(copy) && !overlaps(blocksCopy, copy))
					possibleMoves.add(stringFromMove(block, copy));
				copy.unmove(dir);
			}
		}

		return possibleMoves;
	}

	/*
	 * Abstract: Generate a string representing a given move Prerequisite: None
	 * Input: Original position of the block and its destination Return value:
	 * String representing the move
	 */
	private static String stringFromMove(Block o, Block d) {
		return "" + o.r1() + " " + o.c1() + " " + d.r1() + " " + d.c1();
	}

	/*
	 * Abstract: Check if the given block crosses the limits of the board
	 * Prerequisite: None Input: The block we want to test Return value: True if
	 * the block crosses the limits
	 */
	private boolean crossLimits(Block b) {
		return b.c1() < 0 || b.c2() >= width || b.r1() < 0 || b.r2() >= height;
	}

	/*
	 * Abstract: Check if the given block overlaps another one from the given
	 * list Prerequisite: None Input: List of blocks and the extra block we test
	 * for overlap Return value: True if an overlap is found
	 */
	private static boolean overlaps(List<Block> blocks, Block b) {
		for (Block block : blocks)
			if (block.overlaps(b))
				return true;
		return false;
	}

	/*
	 * Abstract: Move a block from the current configuration Prerequisite: The
	 * string is valid and represents a legal move Input: String representing
	 * the desired move Return value: None
	 */
	public void makeMove(String move) {
		Scanner scanner = new Scanner(move);
		int r1, c1, r2, c2;
		try {
			r1 = scanner.nextInt();
			c1 = scanner.nextInt();
			r2 = scanner.nextInt();
			c2 = scanner.nextInt();
		} finally {
			scanner.close();
		}
		
		moves.add(move);

		// Find the block we need to move
		for (Block b : blocks)
			if (b.r1() == r1 && b.c1() == c1) {
				Direction dir;

				// The block is moved vertically
				if (c1 == c2)
					dir = (r1 > r2 ? Direction.UP : Direction.DOWN);

				// The block is moved horizontally
				else
					dir = (c1 > c2 ? Direction.LEFT : Direction.RIGHT);

				// Move the block
				b.move(dir);
				Collections.sort(blocks);
				return;
			}
		// Execution should never reach this place
	}
	
	/*
	 * Abstract: Print the solution found Prerequisite: None Input: List of the
	 * solution moves Return value: None
	 */
	public void printSolution() {
		for (String move : moves)
			System.out.println(move);
	}

	/*
	 * Abstract: Computes hash code of the current configuration Prerequisite:
	 * The blocks list is sorted Input: None Return value: Computed hash code
	 */
	public int hashCode() {
		String s = "";
		for (Block b : blocks)
			s += b;
		return s.hashCode();
	}

	/*
	 * Abstract: Check if two boards are equal Prerequisite: The blocks list is
	 * sorted Input: Board to compare to the current one Return value: True if
	 * the boards are equal
	 */
	public boolean equals(Object o) {
		Board b = (Board) o;
		if (width != b.width || height != b.height
				|| blocks.size() != b.blocks.size())
			return false;
		for (int i = 0; i < blocks.size(); i++)
			if (!blocks.get(i).equals(b.blocks.get(i)))
				return false;
		return true;
	}

	/*
	 * Abstract: Give a string representation of the state of the board
	 * Prerequisite: None Input: None Return value: String representation
	 */
	public String toString() {
		char[][] matrix = new char[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				matrix[i][j] = ' ';

		// can display a maximum 62 different blocks
		// do not use for equality testing !
		String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int idx = 0;
		for (Block b : blocks) {
			for (int r = b.r1(); r <= b.r2(); r++)
				for (int c = b.c1(); c <= b.c2(); c++)
					matrix[r][c] = charset.charAt(idx % charset.length());
			idx++;
		}

		String s = "";
		for (char[] line : matrix) {
			for (char c : line)
				s += c;
			s += "\n";
		}
		return s;
	}

	/*
	 * Abstract: Check if the invariants of the class are respected
	 * Prerequisite: None Input: None Return value: An Exception is thrown if
	 * something is wrong
	 */
	private void isOK() throws IllegalStateException {
		for (Block b : blocks) {

			// All blocks must be strictly inside the board
			if (crossLimits(b))
				throw new IllegalStateException("Block crosses limits: " + b);

			// and don't overlap each other
			List<Block> blocksCopy = new ArrayList<>(blocks);
			blocksCopy.remove(b);
			if (overlaps(blocksCopy, b))
				throw new IllegalStateException("Block overlapping: " + b);
		}

		// All blocks must be sorted in the list
		for (int i = 0; i < blocks.size(); i++)
			for (int j = i + 1; j < blocks.size(); j++)
				if (blocks.get(i).compareTo(blocks.get(j)) > 0)
					throw new IllegalStateException("Blocks list is not sorted");
	}

}