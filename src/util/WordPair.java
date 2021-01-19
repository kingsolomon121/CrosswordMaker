package util;

public class WordPair {

	private final String word;
	private final String definition;

	public WordPair(String word, String definition) {
		this.word = word;
		this.definition = definition;
	}

	public String getWord() {
		return this.word;
	}

	public String getDefinition() {
		return this.definition;
	}

	public String toString() {
		return this.word;
	}
}
