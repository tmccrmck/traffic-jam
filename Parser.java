import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	private ArrayList<Block> blocks;
	private int width, height;

	private final String[] msg = { "Can't open file: ",
			"Format for the first line is\tHEIGHT WIDTH",
			"Format for the normal line is\tX1 Y1 X2 Y2",
			"Block coordinates are incorrect" };

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public Parser() {
		height = 0;
		width = 0;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	// the boolean argument must be:
	// - True to parse the initialConfigFile
	// - False to parse the goalConfigFile
	public void parseFile(String fileName, boolean needDimensions)
			throws IllegalFileEntryException {
		InputStreamReader input;
		try {
			input = new InputStreamReader(new FileInputStream(fileName));
		} catch (Exception e) {
			throw new IllegalFileEntryException(msg[0] + fileName);
		}
		parseStream(input, needDimensions);
	}

	// this method should be private but is public to be called in JUnit tests
	// it's easier to have all tests in a JUnit test than adding multiple test
	// files to the project
	public void parseStream(InputStreamReader input, boolean needDimensions)
			throws IllegalFileEntryException {
		Scanner file = new Scanner(input), line;
		boolean firstLine = true;

		blocks = new ArrayList<Block>();
		try {

			// 1st line (needed only for the initialConfigFile)
			if (needDimensions) {
				line = new Scanner(file.nextLine());
				try {
					height = line.nextInt();
					width = line.nextInt();
					if (line.hasNext())
						throw new IllegalFileEntryException(msg[1]);
				} finally {
					line.close();
				}
			}

			// block defining lines
			firstLine = false;
			while (file.hasNextLine()) {
				line = new Scanner(file.nextLine());
				try {
					int x1 = line.nextInt(), y1 = line.nextInt();
					int x2 = line.nextInt(), y2 = line.nextInt();
					if (line.hasNext())
						throw new IllegalFileEntryException(msg[2]);
					blocks.add(new Block(x1, y1, x2, y2));
				} finally {
					line.close();
				}
			}

		} catch (IllegalFileEntryException exc) {
			throw exc;
		} catch (Exception exc) {
			throw new IllegalFileEntryException(msg[firstLine ? 1 : 2]);
		} finally {
			file.close();
		}
	}
}