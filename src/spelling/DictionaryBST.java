package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary {
	private TreeSet<String> dict;

	// Implement the dictionary interface using a TreeSet.
	// You'll need a constructor here
	public DictionaryBST() {
		dict = new TreeSet<String>();
	}

	/**
	 * Add this word to the dictionary. Convert it to lowercase first for the
	 * assignment requirements.
	 * 
	 * @param word The word to add
	 * @return true if the word was added to the dictionary (it wasn't already
	 *         there).
	 */
	public boolean addWord(String word) {
		word = word.toLowerCase();

		/*
		 * The add() method only works if the word is not in the dict otherwise, it
		 * returns false
		 */
		if (dict.add(word)) {
			return true;
		}

		return false;

		// The two following implementations also work
		/*
		 * If the dict contains the word, return false otherwise, add the word to the
		 * dict and return true
		 */
		/*
		 * if (!dict.contains(word)) { dict.add(word); return true; }
		 * 
		 * return false;
		 */

		// If any word in the dict matches our word, return false
		/*
		 * for (String w : dict) { if (w.equals(word)) { return false; } }
		 * 
		 * // If the word isn't in the dict, add it and return true dict.add(word);
		 * 
		 * return true;
		 */
	}

	/** Return the number of words in the dictionary */
	public int size() {
		return dict.size();
	}

	/** Is this a word according to this dictionary? */
	public boolean isWord(String s) {
		s = s.toLowerCase();

		// If the dict contains the word, return true, otherwise return false
		if (dict.contains(s)) {
			return true;
		}

		return false;

		// This implementation also works
		// If any word in the dict matches our word, return true -> it is a word
		/*
		 * for (String word : dict) { if (word.equals(s)) { return true; } }
		 * 
		 * // If it's not a word, return false return false;
		 */
	}

}
