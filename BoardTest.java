import java.util.List;

import junit.framework.TestCase;


public class BoardTest extends TestCase {

	public void testAddBlock() {
		Board b = new Board();
		b.addBlock(new Block(2, 2, 2, 2));
		b.addBlock(new Block(3, 3, 3, 3));
		b.addBlock(new Block(1, 2, 1, 2));
		assertEquals(b.blocks().get(0).toString(), "1 2 1 2");
		assertEquals(b.blocks().get(1).toString(), "2 2 2 2");
		assertEquals(b.blocks().get(2).toString(), "3 3 3 3");
	}

	public void testIsGoalReached() {
		Board b1 = new Board();
		Board b2 = new Board();
		b1.addBlock(new Block(1, 2, 1, 2));
		b1.addBlock(new Block(2, 2, 3, 3));
		b1.addBlock(new Block(2, 0, 2, 0));

		assertTrue(b1.isGoalReached(b2));

		b2.addBlock(new Block(1, 2, 1, 2));
		assertTrue(b1.isGoalReached(b2));

		b2.addBlock(new Block(5, 5, 5, 5));
		assertFalse(b1.isGoalReached(b2));
	}

	public void testGeneratePossibleMoves() {
		Board b = new Board(1, 1);
		b.addBlock(new Block(0, 0, 0, 0));
		assertTrue(b.generatePossibleMoves().isEmpty());

		b = new Board(1, 2);
		b.addBlock(new Block(0, 1, 0, 1));
		assertTrue(b.generatePossibleMoves().size() == 1);

		b = new Board(2, 2);
		b.addBlock(new Block(1, 1, 1, 1));
		List<String> moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 2);
		assertTrue(moves.contains("1 1 0 1"));
		assertTrue(moves.contains("1 1 1 0"));

		b = new Board(3, 3);
		b.addBlock(new Block(1, 1, 1, 1));
		moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 4);
		assertTrue(moves.contains("1 1 0 1"));
		assertTrue(moves.contains("1 1 1 0"));
		assertTrue(moves.contains("1 1 2 1"));
		assertTrue(moves.contains("1 1 1 2"));

		b = new Board(4, 1);
		b.addBlock(new Block(1, 0, 1, 0));
		b.addBlock(new Block(2, 0, 2, 0));
		moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 2);
		assertTrue(moves.contains("1 0 0 0"));
		assertTrue(moves.contains("2 0 3 0"));
	}

	public void testMakeMove() {
		Board b = new Board(1, 2);
		b.addBlock(new Block(0, 1, 0, 1));

		List<String> moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 1);
		b.makeMove(moves.get(0));
		assertFalse(b.blocks().contains(new Block(0, 1, 0, 1)));
		assertTrue(b.blocks().contains(new Block(0, 0, 0, 0)));

		moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 1);
		b.makeMove(moves.get(0));
		assertTrue(b.blocks().contains(new Block(0, 1, 0, 1)));
		assertFalse(b.blocks().contains(new Block(0, 0, 0, 0)));

		b = new Board(2, 1);
		b.addBlock(new Block(1, 0, 1, 0));

		moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 1);
		b.makeMove(moves.get(0));
		assertFalse(b.blocks().contains(new Block(1, 0, 1, 0)));
		assertTrue(b.blocks().contains(new Block(0, 0, 0, 0)));

		moves = b.generatePossibleMoves();
		assertTrue(moves.size() == 1);
		b.makeMove(moves.get(0));
		assertTrue(b.blocks().contains(new Block(1, 0, 1, 0)));
		assertFalse(b.blocks().contains(new Block(0, 0, 0, 0)));
	}

//	public void testUnmakeMove() {
//		Board b = new Board(1, 2);
//		b.addBlock(new Block(0, 1, 0, 1));
//
//		List<String> moves = b.generatePossibleMoves();
//		assertTrue(moves.size() == 1);
//		b.makeMove(moves.get(0));
//		assertFalse(b.blocks().contains(new Block(0, 1, 0, 1)));
//		assertTrue(b.blocks().contains(new Block(0, 0, 0, 0)));
//
//		b.unmakeMove(moves.get(0));
//		assertTrue(b.blocks().contains(new Block(0, 1, 0, 1)));
//		assertFalse(b.blocks().contains(new Block(0, 0, 0, 0)));
//	}

	public void testEqualsBoard() {
		Board b1 = new Board();
		Board b2 = new Board();
		b1.addBlock(new Block(1, 2, 3, 4));
		b2.addBlock(new Block(1, 2, 3, 4));
		assertEquals(b1, b2);

		b1 = new Board(2, 2);
		b1.addBlock(new Block(1, 2, 3, 4));
		assertNotSame(b1, b2);

		b2 = new Board(2, 2);
		b1.addBlock(new Block(1, 1, 3, 4));
		assertNotSame(b1, b2);
	}

	public void testToString() {
		Board b = new Board(1, 1);
		assertEquals(b.toString(), " \n");

		b = new Board(1, 2);
		assertEquals(b.toString(), "  \n");

		b = new Board(3, 1);
		b.addBlock(new Block(1, 0, 1, 0));
		assertEquals(b.toString(), " \n0\n \n");

		b.addBlock(new Block(2, 0, 2, 0));
		assertEquals(b.toString(), " \n0\n1\n");

		b = new Board(2, 2);
		b.addBlock(new Block(0, 0, 0, 0));
		b.addBlock(new Block(1, 1, 1, 1));
		assertEquals(b.toString(), "0 \n 1\n");
	}

}
