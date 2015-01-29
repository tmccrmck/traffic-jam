import java.util.*;

public class BinaryTree {

	protected TreeNode myRoot;
	private ArrayList alreadySeen;

	public BinaryTree() {
		myRoot = null;
	}

	public BinaryTree(TreeNode t) {
		myRoot = t;
	}

	public BinaryTree(ArrayList<Object> pre, ArrayList<Object> in) {
		if (pre.isEmpty() && in.isEmpty()) {
			myRoot = null;
		}
		myRoot = new TreeNode(null);
		myRoot = binaryTreeHelper(pre, in);
	}

	private static TreeNode binaryTreeHelper(ArrayList<Object> pre,
			ArrayList<Object> in) {
		if (in.isEmpty()) {
			return null;
		}
		ArrayList<Object> leftSide = new ArrayList<Object>();
		ArrayList<Object> rightSide;
		Object rootValue = pre.get(0);
		while (in.size() > 0) {
			if (in.get(0).equals(rootValue)) {
				break;
			} else {
				leftSide.add(in.get(0));
				in.remove(0);
			}
		}
		in.remove(0);
		rightSide = in;
		pre.remove(0);
		return new TreeNode(rootValue, binaryTreeHelper(pre, leftSide),
				binaryTreeHelper(pre, rightSide));

	}

	// Print the values in the tree in preorder: root value first,
	// then values in the left subtree (in preorder), then values
	// in the right subtree (in preorder).
	public void printPreorder() {
		printPreorder(myRoot);
		System.out.println();
	}

	private static void printPreorder(TreeNode t) {
		if (t != null) {
			System.out.print(t.myItem + " ");
			printPreorder(t.myLeft);
			printPreorder(t.myRight);
		}
	}

	// Print the values in the tree in inorder: values in the left
	// subtree first (in inorder), then the root value, then values
	// in the right subtree (in inorder).
	public void printInorder() {
		printInorder(myRoot);
		System.out.println();
	}

	private static void printInorder(TreeNode t) {
		if (t != null) {
			printInorder(t.myLeft);
			System.out.print(t.myItem + " ");
			printInorder(t.myRight);
		}
	}

	public void fillSampleTree1() {
		myRoot = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
	}

	public void fillSampleTree2() {
		myRoot = new TreeNode("a", new TreeNode("b", new TreeNode("d",
				new TreeNode("e"), new TreeNode("f")), null), new TreeNode("c"));
	}

	public void fillSampleTree3() {
		myRoot = new TreeNode("a", new TreeNode("a"), new TreeNode("b")); // illegal
																			// state
																			// tree
	}
	public void fillSampleTree4() {
		myRoot = new TreeNode("a",
					new TreeNode("b", null, new TreeNode("d", new TreeNode("e"), new TreeNode("f"))), new TreeNode("c"));
	}

	public static void main(String[] args) {
		BinaryTree t;
		t = new BinaryTree();
		print(t, "the empty tree");
		t.fillSampleTree1();
		print(t, "sample tree 1");
		t.fillSampleTree2();
		print(t, "sample tree 2");
		t.print();

		BinaryTree x = fibTree(5);
		print(x, "Fib tree");

		System.out.println(t.isOK());
		BinaryTree t2 = new BinaryTree();
		t2.fillSampleTree3();
		System.out.println(t2.isOK()); // illegal tree should print false

		t = exprTree("((a+(5*(a+b)))+(6*5))");
		t.print();

		t = exprTree("((a+(5*(9+1)))+(6*5))");
		t.optimize();
		t.print();
		
		System.out.println();
		t.fillSampleTree4();
		t.print();
		t.prune();
		t.print();
	}

	private static void print(BinaryTree t, String description) {
		System.out.println(description + " in preorder");
		t.printPreorder();
		System.out.println(description + " in inorder");
		t.printInorder();
		System.out.println();
	}

	public void print() {
		if (myRoot != null) {
			printHelper(myRoot, 0);
		}
	}

	private static final String indent1 = "    ";

	private static void printHelper(TreeNode root, int indent) {
		if (root.myRight != null) {
			printHelper(root.myRight, indent + 1);
		}
		println(root.myItem, indent);
		if (root.myLeft != null) {
			printHelper(root.myLeft, indent + 1);
		}
	}

	private static void println(Object obj, int indent) {
		for (int k = 0; k < indent; k++) {
			System.out.print(indent1);
		}
		System.out.println(obj);
	}

	public static BinaryTree fibTree(int n) {
		BinaryTree result = new BinaryTree();
		result.myRoot = result.fibTreeHelper(n);
		return result;
	}

	private TreeNode fibTreeHelper(int n) {
		if (n == 0) {
			return new TreeNode(new Integer(0));
		} else if (n == 1) {
			return new TreeNode(new Integer(1));
		} else {
			return new TreeNode(new Integer(getFib(n)), fibTreeHelper(n - 1),
					fibTreeHelper(n - 2));
		}
	}

	// created by me to find the fibonacci number
	public int getFib(int n) {
		if (n < 2) {
			return n;
		} else {
			return getFib(n - 1) + getFib(n - 2);
		}
	}

	public boolean isOK() {
		alreadySeen = new ArrayList();
		try {
			check(myRoot);
			return true;
		} catch (IllegalStateException e) {
			return false;
		}
	}

	private void check(TreeNode t) throws IllegalStateException {
		if (alreadySeen.contains(t.myItem)) {
			throw new IllegalStateException();
		} else {
			alreadySeen.add(t.myItem);
			if (t.myLeft != null)
				check(t.myLeft);
			else if (t.myRight != null)
				check(t.myRight);
		}
	}

	public static BinaryTree exprTree(String s) {
		BinaryTree result = new BinaryTree();
		result.myRoot = result.exprTreeHelper(s);
		return result;
	}

	// Return the tree corresponding to the given arithmetic expression.
	// The expression is legal, fully parenthesized, contains no blanks,
	// and involves only the operations + and *.
	private TreeNode exprTreeHelper(String expr) {
		if (expr.charAt(0) != '(') {
			// you fill this in
			return new TreeNode(expr);
		} else {
			// expr is a parenthesized expression.
			// Strip off the beginning and ending parentheses,
			// find the main operator (an occurrence of + or * not nested
			// in parentheses, and construct the two subtrees.
			int nesting = 0;
			int opPos = 0;
			for (int k = 1; k < expr.length() - 1; k++) {
				// you supply the missing code
				if (expr.charAt(k) == '(') {
					nesting++;
				}
				if (expr.charAt(k) == ')') {
					nesting--;
				}
				if (nesting == 0
						&& (expr.charAt(k) == '+' || expr.charAt(k) == '*')) {
					opPos = k;
					break;
				}
			}
			String opnd1 = expr.substring(1, opPos);
			String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
			String op = expr.substring(opPos, opPos + 1);
			System.out.println("expression = " + expr);
			System.out.println("operand 1  = " + opnd1);
			System.out.println("operator   = " + op);
			System.out.println("operand 2  = " + opnd2);
			System.out.println();
			// you fill this in
			return new TreeNode(op, exprTreeHelper(opnd1),
					exprTreeHelper(opnd2));
		}
	}

	public void optimize() {
		if (myRoot != null) {
			optimizeHelper(myRoot);
		}
	}

	public static TreeNode optimizeHelper(TreeNode t) {
		if (t.myLeft != null && t.myRight != null) {
			optimizeHelper(t.myLeft);
			optimizeHelper(t.myRight);
			if (t.myLeft.myLeft == null && t.myRight.myRight == null) {
				try {
					Integer op1 = Integer.parseInt((String) t.myLeft.myItem);
					Integer op2 = Integer.parseInt((String) t.myRight.myItem);
					if (((String) t.myItem).equals("+")) {
						t.myItem = "" + (op1 + op2);
					} else if (((String) t.myItem).equals("*")) {
						t.myItem = "" + (op1 * op2);
					}
					t.myLeft = null;
					t.myRight = null;
					return t;
				} catch (NumberFormatException e) {
					return t;
				}
			}
		}
		return t;
	}

	protected static class TreeNode {

		public Object myItem;
		public TreeNode myLeft;
		public TreeNode myRight;

		public TreeNode(Object obj) {
			myItem = obj;
			myLeft = myRight = null;
		}

		public TreeNode(Object obj, TreeNode left, TreeNode right) {
			myItem = obj;
			myLeft = left;
			myRight = right;
		}
	}

	// Method for the BinaryTree class
	public Iterator iterator() {
		return new InorderIterator();
	}

	// Inner class inside of the BinaryTree class.
	// Also, it uses java.util.Iterator and java.util.Stack.
	private class InorderIterator implements Iterator {
		private Stack<TreeNode> nodeStack;
		private TreeNode currentNode;

		public InorderIterator() {
			nodeStack = new Stack<TreeNode>();
			currentNode = myRoot;
			constructorHelper(currentNode);
		} // end default constructor

		public void constructorHelper(TreeNode t) {
			if (t != null) {
				if (t.myRight != null) {
					constructorHelper(t.myRight);
				}
				nodeStack.push(t);
				if (t.myLeft != null) {
					constructorHelper(t.myLeft);
				}
			}
		}

		public boolean hasNext() {
			return !nodeStack.isEmpty();
		} // end hasNext

		public Object next() {
			TreeNode nextNode = null;
			nextNode = nodeStack.pop();
			return nextNode.myItem;
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end InorderIterator

	public void prune() {
		if (myRoot != null) {
			pruneHelper(myRoot);
		}
	}

	private static void pruneHelper(TreeNode t) {
		if (t == null)
			return;
		if (t.myLeft != null) {
			if (t.myLeft.myLeft != null && t.myLeft.myRight == null) {
				t.myLeft = t.myLeft.myLeft;
			}
			if (t.myLeft.myLeft == null && t.myLeft.myRight != null) {
				t.myLeft = t.myLeft.myRight;
			}
			pruneHelper(t.myLeft);
		}
		if (t.myRight != null) {
			if (t.myRight.myLeft != null && t.myRight.myRight == null) {
				t.myRight = t.myRight.myLeft;
			}
			if (t.myRight.myLeft == null && t.myRight.myRight != null) {
				t.myRight = t.myRight.myRight;
			}
			pruneHelper(t.myRight);
		}
	}
}