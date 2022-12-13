package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part 2),
	 * you should convert the string to all lower case before you insert it.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes into
	 * the trie, as described outlined in the videos for this week. It should
	 * appropriately use existing nodes in the trie, only creating new nodes when
	 * necessary. E.g. If the word "no" is already in the trie, then adding the word
	 * "now" would add only one additional node (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 *         in the dictionary.
	 */
	public boolean addWord(String word) {
		word = word.toLowerCase();
		TrieNode curr = root;

		/*
		 * Check each character of the word, until we find TrieNode that doesn't contain
		 * that character that is where we add the new TrieNode
		 */
		for (char c : word.toCharArray()) {
			/*
			 * If the set of characters that have links from this node contain the char c,
			 * then we move the curr TrieNode to its child
			 */
			if (curr.getValidNextCharacters().contains(c)) {
				curr = curr.getChild(c);
			} else {
				// Returns the new node or null if it's already there
				curr = curr.insert(c);
			}
		}

		// If it doesn't end a node, set the isWord variable to true
		if (!curr.endsWord()) {
			curr.setEndsWord(true);
			size++; // Only increases for complete words;
			return true;
		}

		return false;
	}

	/*
	 * Search for a TrieNode that contains our string w
	 * 
	 * @param w The string we are search for
	 * 
	 * @return The TrieNode (if present) or null
	 * 
	 */
	private TrieNode searchTrieNode(String w) {
		TrieNode curr = root;

		// For each character of our string
		for (char c : w.toCharArray()) {
			/*
			 * If the set of characters that have links from this node contain the char c,
			 * then we move the curr TrieNode to its child
			 */
			if (curr.getValidNextCharacters().contains(c)) {
				curr = curr.getChild(c);
			} else {
				return null;
			}
		}

		return curr;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week.
	 */
	@Override
	public boolean isWord(String s) {
		s = s.toLowerCase();
		TrieNode curr = searchTrieNode(s);

		// If it's a complete word, return true
		if (curr != null && curr.endsWord()) {
			return true;
		}

		return false;
	}

	/**
	 * Return a list, in order of increasing (non-decreasing) word length,
	 * containing the numCompletions shortest legal completions of the prefix
	 * string. All legal completions must be valid words in the dictionary. If the
	 * prefix itself is a valid word, it is included in the list of returned words.
	 * 
	 * The list of completions must contain all of the shortest completions, but
	 * when there are ties, it may break them in any order. For example, if there
	 * the prefix string is "ste" and only the words "step", "stem", "stew", "steer"
	 * and "steep" are in the dictionary, when the user asks for 4 completions, the
	 * list must include "step", "stem" and "stew", but may include either the word
	 * "steer" or "steep".
	 * 
	 * If this string prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix         The text to use at the word stem
	 * @param numCompletions The maximum number of predictions desired.
	 * @return A list containing the up to numCompletions best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// A list containing the up to numCompletions best predictions
		List<String> wordPredictions = new ArrayList<String>();
		prefix = prefix.toLowerCase();

		// 1. Find the stem in the trie. If the stem does not appear in the trie, return
		// an
		// empty list
		TrieNode curr = searchTrieNode(prefix); // This node will contain the prefix as text

		if (curr == null) {
			return wordPredictions;
		}

		// If our prefix is a complete word, add it to the final list

		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem to the
		// back
		// of the list.
		Queue<TrieNode> queue = new LinkedList<TrieNode>();
		queue.add(curr);

		// While the queue is not empty and you don't have enough completions
		while (!queue.isEmpty() && wordPredictions.size() < numCompletions) {
			// Remove the first Node from the queue
			TrieNode n = queue.remove();

			// If it is a word, add it to the completions list
			if (isWord(n.getText())) {
				wordPredictions.add(n.getText());

			}

			// Add all of its child nodes to the back of the queue
			// (these will be checked next)
			for (Character c : n.getValidNextCharacters()) {
				queue.add(n.getChild(c));
			}

		}

		// Return the list of completions
		return wordPredictions;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

}