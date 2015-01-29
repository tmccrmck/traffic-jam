import junit.framework.TestCase;


public class BlockTest extends TestCase {

	public void testMove() {
		Block b = new Block(2, 2, 2, 2);

		b.move(Board.Direction.UP);
		assertTrue(b.r1() == 1 && b.c1() == 2);
		assertTrue(b.r2() == 1 && b.c2() == 2);
		b.unmove(Board.Direction.UP);
		assertTrue(b.r1() == 2 && b.c1() == 2);
		assertTrue(b.r2() == 2 && b.c2() == 2);

		b.move(Board.Direction.DOWN);
		assertTrue(b.r1() == 3 && b.c1() == 2);
		assertTrue(b.r2() == 3 && b.c2() == 2);
		b.unmove(Board.Direction.DOWN);
		assertTrue(b.r1() == 2 && b.c1() == 2);
		assertTrue(b.r2() == 2 && b.c2() == 2);

		b.move(Board.Direction.LEFT);
		assertTrue(b.r1() == 2 && b.c1() == 1);
		assertTrue(b.r2() == 2 && b.c2() == 1);
		b.unmove(Board.Direction.LEFT);
		assertTrue(b.r1() == 2 && b.c1() == 2);
		assertTrue(b.r2() == 2 && b.c2() == 2);

		b.move(Board.Direction.RIGHT);
		assertTrue(b.r1() == 2 && b.c1() == 3);
		assertTrue(b.r2() == 2 && b.c2() == 3);
		b.unmove(Board.Direction.RIGHT);
		assertTrue(b.r1() == 2 && b.c1() == 2);
		assertTrue(b.r2() == 2 && b.c2() == 2);
	}

	public void testOverlaps() {
		Block b1 = new Block(1, 1, 1, 2);
		Block b2 = new Block(1, 2, 1, 3);
		assertTrue(b1.overlaps(b2));

		b1 = new Block(1, 1, 1, 1);
		b2 = new Block(1, 1, 1, 1);
		assertTrue(b1.overlaps(b2));

		b1 = new Block(1, 1, 2, 2);
		b2 = new Block(0, 0, 3, 3);
		assertTrue(b1.overlaps(b2));

		b1 = new Block(0, 1, 1, 2);
		b2 = new Block(0, 0, 1, 0);
		assertFalse(b1.overlaps(b2));

		b1 = new Block(1, 1, 2, 2);
		b2 = new Block(3, 3, 4, 4);
		assertFalse(b1.overlaps(b2));

		b1 = new Block(1, 1, 2, 2);
		b2 = new Block(2, 3, 3, 4);
		assertFalse(b1.overlaps(b2));
	}

	public void testCompareTo() {
		Block b1 = new Block(1, 1, 2, 1);
		Block b2 = new Block(2, 1, 3, 1);
		assertTrue(b1.compareTo(b2) < 0);

		b1 = new Block(1, 1, 2, 2);
		b2 = new Block(0, 0, 3, 3);
		assertTrue(b1.compareTo(b2) > 0);

		b1 = new Block(2, 3, 2, 3);
		b2 = new Block(2, 4, 2, 4);
		assertTrue(b1.compareTo(b2) < 0);

		b1 = new Block(1, 1, 2, 2);
		b2 = new Block(1, 1, 3, 4);
		assertTrue(b1.compareTo(b2) == 0);
	}

	public void testEqualsBlock() {
		Block b1 = new Block(1, 1, 2, 1);
		Block b2 = new Block(b1);
		assertEquals(b1, b2);
	}

	public void testToString() {
		Block b1 = new Block(1, 2, 3, 4);
		assertEquals(b1.toString(), "1 2 3 4");
	}

}
