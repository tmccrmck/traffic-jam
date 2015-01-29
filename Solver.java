import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solver {
	private Board currentBoard;
	private Board goalBoard;
	public static boolean debuggingFlag;

	/*
	 * Abstract: Solver constructor Prerequisite: None Input: Initial and final
	 * board configuration file names, level of debugging wanted Return value:
	 * Board representing the configuration
	 */
	public Solver(String initialConfigFileName, String goalConfigFileName) {
		currentBoard = getBoardFromFile(initialConfigFileName, true);
		goalBoard = getBoardFromFile(goalConfigFileName, false);
	}

	/*
	 * Abstract: Parse the configuration file and creates the corresponding
	 * board Prerequisite: None Input: File name and its type (initial or final
	 * board) Return value: Board representing the configuration
	 */
	private Board getBoardFromFile(String filename, boolean isInitFile) {
		Scanner file, line;
		int width = 0, height = 0;
		Board newBoard;

		try {
			file = new Scanner(new File(filename));
		} catch (FileNotFoundException exc) {
			throw new IllegalArgumentException("File not found: " + filename);
		}

		try {

			// First line is only required for the initialConfigFile
			if (isInitFile) {
				line = new Scanner(file.nextLine());
				try {
					height = line.nextInt();
					width = line.nextInt();
				} finally {
					line.close();
				}
			}

			newBoard = width != 0 ? new Board(height, width) : new Board();

			// block defining lines
			while (file.hasNextLine()) {
				line = new Scanner(file.nextLine());
				try {
					if (line.hasNext()) {
						int r1 = line.nextInt(), c1 = line.nextInt();
						int r2 = line.nextInt(), c2 = line.nextInt();
						newBoard.addBlock(new Block(r1, c1, r2, c2));
					}
				} finally {
					line.close();
				}
			}

		} catch (Exception exc) {
			throw new IllegalArgumentException("Invalid input file: "
					+ filename);
		} finally {
			file.close();
		}

		return newBoard;
	}

	/*
	 * Abstract: Check and parse the arguments Prerequisite: None Input: Initial
	 * arguments of the application Return value: Level of debugging wanted, 0
	 * by default
	 */
	private static void checkArguments(String[] args) {
		if (args.length != 2
				&& (args.length != 3 || args[0].length() < 2 || !args[0]
						.substring(0, 2).equals("-o"))) {
			System.out
					.println("Usage: java Solver [-ooptions] initialConfigFile goalConfigFile");
			System.exit(-1);
		}

		if (args.length == 3) {
			String option = args[0].substring(2);
			if (option.equalsIgnoreCase("options")) {
				System.out
						.println("You can call this application with different debugging parameters:");
				System.out.println(" -ooptions:\tprints this help");
				System.out.println(" -o0:\tno debuggings information");
				System.out
						.println(" -o1:\tshows the different steps of the solving");
				System.out
						.println(" -o2:\tshows operations at the board level");
				System.out
						.println(" -o3:\tshows operations at the block level");
				System.out
						.println("A higher level of debugging information will also print the under levels information");
				System.exit(-1);
			}

			int level = 0;
			try {
				level = Integer.parseInt(option);
			} catch (NumberFormatException exc) {
				System.out.println("Incorrect parameter");
				System.out
						.println("Use -options to print the different options");
				System.exit(-1);
			}

			if (level > 0) {
				Solver.debuggingFlag = true;
				if (level > 1) {
					Board.debuggingFlag = true;
					if (level > 2)
						Block.debuggingFlag = true;
				}
			}
		}
	}

	/*
	 * Abstract: Solve the puzzle with an A* recursive algorithm Prerequisite:
	 * Current board is valid Input: None Return value: True if a solution has
	 * been found
	 */
	private boolean solve() {
		if (debuggingFlag)
			System.out.println("Start solving");
		PriorityQueue<Board> fringe = new PriorityQueue<Board>(11, comparator);
		List<Integer> knownConfigurationsHashes = new ArrayList<Integer>();
		fringe.add(currentBoard);
		knownConfigurationsHashes.add(currentBoard.hashCode());

		while (!fringe.isEmpty()) {
			// sorts using our heuristic
			// Collections.sort(fringe, comparator);

			Board current = fringe.poll();
			if (debuggingFlag) {
				System.out.println("Current board:");
				System.out.print(current);
				System.out.print("Have we reached the objective? ");
			}
			if (current.isGoalReached(goalBoard)) {
				if (debuggingFlag)
					System.out.println("Yes, a solution has been found !");
				current.printSolution();
				return true;
			}
			if (debuggingFlag)
				System.out.println("No");

			// Generate possible moves and sort them with heuristics
			if (debuggingFlag)
				System.out
						.println("Generate possible moves and sort with heuristics");
			List<String> possibleMoves = current.generatePossibleMoves();

			for (Iterator<String> it = possibleMoves.iterator(); it.hasNext();) {
				String move = it.next();
				if (debuggingFlag)
					System.out.println("Make the move: " + move);

				Board copy = new Board(current);
				copy.makeMove(move);

				// Skip this move if it leads to an already known configuration
				int hash = copy.hashCode();
				if (knownConfigurationsHashes.contains(hash)) {
					if (debuggingFlag)
						System.out
								.println("Configuration known, skip to next move");
					// current.unmakeMove(move);
					continue;
				}

				// Otherwise, add it to the known configurations
				// and continue recursive solving
				knownConfigurationsHashes.add(hash);
				fringe.add(copy);
			}

		}
		return false;
	}

	/*
	 * Abstract: Heuristics for A* algorithm Prerequisite: None Input: Two moves
	 * (string) to compare Return value: Return value is smaller if one or both
	 * of the moves put a block in a correct position
	 */
	Comparator<Board> comparator = new Comparator<Board>() {
		public int compare(Board b1, Board b2) {
			int rtn = 0;
			for (Block goalBlock : goalBoard.blocks()) {
				for (Block block1: b1.blocks()) {
					if(block1.equals(goalBlock))
						rtn--;
				}
				for(Block block2: b2.blocks()){
					if(block2.equals(goalBlock))
						rtn++;
				}				
			}
			return rtn;
		}

	};

	/*
	 * Abstract: Main function Prerequisite: None Input: Arguments from the
	 * command line Return value: The application can exit with different error
	 * codes, -1 if the command line arguments are incorrect 0 if a solution has
	 * been found 1 otherwise
	 */
	public static void main(String[] args) {
		// Check the arguments and determine the debugging level
		checkArguments(args);

		// Initiate the solver
		String initialFile = args[0 + (args.length == 3 ? 1 : 0)];
		String finalFile = args[1 + (args.length == 3 ? 1 : 0)];
		Solver solver = new Solver(initialFile, finalFile);

		// Start the solving

		if (!solver.solve())
			System.exit(1);

	}

}
