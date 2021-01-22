package model;

import util.WordPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Made up of several uninterrupted spaces in a row or column.
 */
public class WordSlot {
	private final List<Space> spaces;
	private final int num;
	private final Direction dir;
	private final List<WordPair> domain;

	public WordSlot(List<Space> spaces, int num, Direction dir, List<WordPair> domain) {
		this.spaces = new ArrayList<>(spaces);
		this.num = num;
		this.dir = dir;
		this.domain = new ArrayList<>(domain);

		this.domain.removeIf(word -> word.getWord().length() != this.spaces.size());
	}

	/**
	 * Is the given word a valid assignment for this wordSlot with the given assignment so far.
	 *
	 * @param word		the word
	 * @param assignment	the assignment so far
	 * @return	a boolean
	 */
	public boolean isValid(WordPair word, Map<WordSlot, WordPair> assignment) {
		for (WordSlot conflict : assignment.keySet()) {
			for (Space space : this.spaces) {
				if (conflict.getSpaces().contains(space)) {
					if (assignment.get(conflict).getWord().charAt(conflict.getSpaceIndex(space)) !=
									word.getWord().charAt(this.getSpaceIndex(space))) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Gets the list of spaces contained within this wordSlot.
	 *
	 * @return	the list of spaces
	 */
	public List<Space> getSpaces() {
		return new ArrayList<>(this.spaces);
	}

	/**
	 * Gets the index of the given space in this wordSlot.
	 *
	 * @param space	the space
	 * @return	the index
	 */
	public int getSpaceIndex(Space space) {
		return this.spaces.indexOf(space);
	}

	/**
	 * Adds a space to this wordSlot.
	 *
	 * @param space the space
	 */
	public void addSpace(Space space) {
		this.spaces.add(space);
	}

	/**
	 * Generates a String representation of this wordSlot.
	 *
	 * @return	the String
	 */
	public String toString() {
		return this.num + this.dir.toString();
	}

	/**
	 * Gets the number assigned to this wordSlot.
	 *
	 * @return	the number
	 */
	public int getNum() {
		return this.num;
	}

	/**
	 * Gets the domain of this wordSlot.
	 *
	 * @return	the domain
	 */
	public List<WordPair> getDomain() {
		return new ArrayList<>(this.domain);
	}

	/**
	 * Removes a word from the domain of this wordSlot.
	 *
	 * @param w	the word.
	 */
	public void removeFromDomain(WordPair w) {
		this.domain.remove(w);
	}

	/**
	 * adds a list of words to the domain of this wordSlot.
	 *
	 * @param words	the words.
	 */
	public void addToDomain(List<WordPair> words) {
		this.domain.addAll(words);
	}
}
