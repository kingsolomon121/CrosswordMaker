package util;

/**
 * Describes a solution word and its definition or clue.
 */
public class WordPair {

	private final String word;
	private final String definition;

	public WordPair(String word, String definition) {
		this.word = word;
		this.definition = definition;
	}

	/**
	 * Returns the solution word associated with this WordPair.
	 *
	 * @return the word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * Returns the clue phrase associated with this WordPair.
	 *
	 * @return the clue
	 */
	public String getDefinition() {
		return this.definition;
	}

	/**
	 * Returns a String representation of this WordPair.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.word;
	}
}
