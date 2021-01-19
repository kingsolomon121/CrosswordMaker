package model;

import util.WordPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public List<Space> getSpaces() {
		return new ArrayList<Space>(this.spaces);
	}

	public int getSpaceIndex(Space space) {
		return this.spaces.indexOf(space);
	}

	public void addSpace(Space space) {
		this.spaces.add(space);
	}

	public String toString() {
		return this.num + this.dir.toString();
	}

	public int getNum() {
		return this.num;
	}

	public List<WordPair> getDomain() {
		return new ArrayList<WordPair>(this.domain);
	}

	public void removeFromDomain(WordPair w) {
		this.domain.remove(w);
	}

	public void addToDomain(List<WordPair> words) {
		this.domain.addAll(words);
	}
}
