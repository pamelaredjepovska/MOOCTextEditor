/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a
 * Breadth First Search of Nearby words to create a path between two words.
 * 
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {
	private static final int THRESHOLD = 1000;

	// This is the root node of the WPTree
	private WPTreeNode root;
	// Used to search for nearby Words
	private NearbyWords nw;

	// This constructor is used by the Text Editor Application
	// You'll need to create your own NearbyWords object here.
	public WPTree() {
		this.root = null;
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(d, "data/dict.txt");
		this.nw = new NearbyWords(d);
	}

	// This constructor will be used by the grader code
	public WPTree(NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}

	// See method description in WordPath interface
	public List<String> findPath(String word1, String word2) {
		// Create a queue of WPTreeNodes to hold words to explore
		List<WPTreeNode> queue = new LinkedList<WPTreeNode>();

		// Create a visited set to avoid looking at the same word repeatedly
		HashSet<String> visited = new HashSet<String>();

		// If any of the words don't exist in the dictionary, return an empty list
		if (!nw.dict.isWord(word1) || !nw.dict.isWord(word2)) {
			return null;
		}

		// Set the root to be a WPTreeNode containing word1
		WPTreeNode root = new WPTreeNode(word1, null);

		// Add the initial word to visited
		visited.add(word1);

		// Add root to the queue
		queue.add(root);

		// While the queue has elements and we have not yet found word2
		while (!queue.isEmpty() && visited.size() < THRESHOLD) {
			// Remove the node from the start of the queue and assign to curr
			WPTreeNode curr = queue.remove(0);

			// Get a list of real word neighbors (one mutation from curr's word)
			List<String> neighbors = nw.distanceOne(curr.getWord(), true);

			// For each n in the list of neighbors
			for (String n : neighbors) {
				// If n is not visited
				if (!visited.contains(n)) {
					// Add n as a child of curr
					WPTreeNode child = curr.addChild(n);

					// Add n to the visited set
					visited.add(n);

					// Add the node for n to the back of the queue
					// WPTreeNode node = new WPTreeNode(n, curr);
					// queue.add(node);
					queue.add(child);

					// if n is word2
					if (n.equals(word2)) {
						return child.buildPathToRoot();
					}
				}
			}
		}

		return null;
	}

	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(List<WPTreeNode> list) {
		String ret = "[ ";

		for (WPTreeNode w : list) {
			ret += w.getWord() + ", ";
		}
		ret += "]";
		return ret;
	}

}

/*
 * Tree Node in a WordPath Tree. This is a standard tree with each node having
 * any number of possible children. Each node should only contain a word in the
 * dictionary and the relationship between nodes is that a child is one
 * character mutation (deletion, insertion, or substitution) away from its
 * parent
 */
class WPTreeNode {

	private String word;
	private List<WPTreeNode> children;
	private WPTreeNode parent;

	/**
	 * Construct a node with the word w and the parent p (pass a null parent to
	 * construct the root)
	 * 
	 * @param w The new node's word
	 * @param p The new node's parent
	 */
	public WPTreeNode(String w, WPTreeNode p) {
		this.word = w;
		this.parent = p;
		this.children = new LinkedList<WPTreeNode>();
	}

	/**
	 * Add a child of a node containing the String s precondition: The word is not
	 * already a child of this node
	 * 
	 * @param s The child node's word
	 * @return The new WPTreeNode
	 */
	public WPTreeNode addChild(String s) {
		WPTreeNode child = new WPTreeNode(s, this);
		this.children.add(child);
		return child;
	}

	/**
	 * Get the list of children of the calling object (pass a null parent to
	 * construct the root)
	 * 
	 * @return List of WPTreeNode children
	 */
	public List<WPTreeNode> getChildren() {
		return this.children;
	}

	/**
	 * Allows you to build a path from the root node to the calling object
	 * 
	 * @return The list of strings starting at the root and ending at the calling
	 *         object
	 */
	public List<String> buildPathToRoot() {
		WPTreeNode curr = this;
		List<String> path = new LinkedList<String>();
		while (curr != null) {
			path.add(0, curr.getWord());
			curr = curr.parent;
		}
		return path;
	}

	/**
	 * Get the word for the calling object
	 *
	 * @return Getter for calling object's word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * toString method
	 *
	 * @return The string representation of a WPTreeNode
	 */
	public String toString() {
		String ret = "Word: " + word + ", parent = ";
		if (this.parent == null) {
			ret += "null.\n";
		} else {
			ret += this.parent.getWord() + "\n";
		}
		ret += "[ ";
		for (WPTreeNode curr : children) {
			ret += curr.getWord() + ", ";
		}
		ret += (" ]\n");
		return ret;
	}

}
