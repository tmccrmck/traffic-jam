import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;


public class ParserTest extends TestCase {

	public void testValidInitialConfigParse() {
		String[] validLineSet = {
				"5 4", "5 4\n", // no blocks
				"5 4\n0 0 1 0", "5 4\n0 0 1 0\n", // one block
				"5 4\n0 0 1 0\n0 3 1 3", // multiple blocks
		};

		for (String validLine : validLineSet) {
			Parser p = new Parser();
			InputStreamReader input = new InputStreamReader(new ByteArrayInputStream(validLine.getBytes()));

			try {
				p.parseStream(input, true);
			} catch (IllegalFileEntryException exc) {
				fail("Should be valid:\n" + validLine + "\n" + exc.getMessage());
			}
		}
	}

	public void testInvalidInitialConfigParse() {
		String[] invalidLineSet = {
				"", //empty
				"\n0 0 1 0", // no dimensions
				"\n", "\n0 0 1 0\n", // first line empty
				"5", "5\n", "5 4 6", // first line wrong format
				"5 4\n0", "5 4\n0\n", "5 4\n0 0 0\n", "5 4\n0 0 1 0 2", "5 4\n\n0 0 1 0 2", // regular line wrong format
				"a 5\n", "4 cd", "5 4\n0 0 e 0", // not integers
		};

		for (String invalidLine : invalidLineSet) {
			Parser p = new Parser();
			InputStreamReader input = new InputStreamReader(new ByteArrayInputStream(invalidLine.getBytes()));

			try {
				p.parseStream(input, true);
				fail("Should be invalid:\n" + invalidLine);
			} catch (IllegalFileEntryException exc) {
			}
		}
	}

	public void testValidGoalConfigParse() {
		String[] validLineSet = {
				"", // no blocks
				"0 0 1 0", "0 0 1 0\n", // one block
				"0 0 1 0\n0 3 1 3", "0 0 1 0\n0 3 1 3\n2 0 3 0\n", // multiple blocks
		};

		for (String validLine : validLineSet) {
			Parser p = new Parser();
			InputStreamReader input = new InputStreamReader(new ByteArrayInputStream(validLine.getBytes()));

			try {
				p.parseStream(input, false);
			} catch (IllegalFileEntryException exc) {
				fail("Should be valid:\n" + validLine + "\n" + exc.getMessage());
			}
		}
	}
}