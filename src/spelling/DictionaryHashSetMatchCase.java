/**
 * 
 */
package spelling;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSetMatchCase implements Dictionary {

	private HashSet<String> words;

	public DictionaryHashSetMatchCase() {
		words = new HashSet<String>();
	}

	/**
	 * Add this word to the dictionary in three formats: all letters lowercase (ex.
	 * "hello"), all letter uppercase (ex. "HELLO") and only the first letter
	 * capitalized (ex. "Hello")
	 * 
	 * @param word The word to add
	 * @return true if the word was added to the dictionary (it wasn't already
	 *         there).
	 */
	@Override
	public boolean addWord(String word) {
		return words.add(word); // Add the word as it is
		
		// return words.add(word.toLowerCase()) && words.add(word.toUpperCase()) &&
		// words.add(word.substring(0,1).toUpperCase() +
		// word.substring(1).toLowerCase());
	}

	/** Return the number of words in the dictionary */
	@Override
	public int size() {
		return words.size();
	}

	/** Is this a word according to this dictionary? */
	@Override
	public boolean isWord(String s) {
		return words.contains(s);
		/*
		 * String s1 = ""; String s2 = ""; String s3 = "";
		 * 
		 * if (s == "") { return false; }
		 * 
		 * if (words.contains(s)) { return true; }
		 * 
		 * if (Character.isUpperCase(s.charAt(0))) // If the first letter is upper case
		 * { s1 = Character.toLowerCase(s.charAt(0)) + s.substring(1); // With first
		 * letter lower
		 * 
		 * if (words.contains(s1)) { return true; } } else if
		 * (s.equals(s.toUpperCase())) { s2 = s1.toLowerCase(); // All lower case
		 * 
		 * if (words.contains(s2)) { return true; }
		 * 
		 * s3 = Character.toUpperCase(s2.charAt(0)) + s2.substring(1); // With first
		 * letter upper
		 * 
		 * if (words.contains(s3)) { return true; } }
		 * 
		 * return false;
		 */
	}

}
