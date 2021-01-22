package model;

import util.WordPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Stores the data to represent the Constraint Satisfaction Problem.
 */
public class CrosswordModel implements CSP<WordSlot, WordPair> {

	protected final List<List<Space>> spaces;
	protected final List<WordSlot> slots;
	protected final List<WordPair> values;

	//Constructor
	public CrosswordModel(List<List<Space>> spaces, List<WordSlot> slots, List<WordPair> values) {
		this.spaces = new ArrayList<>(spaces);
		this.slots = new ArrayList<>(slots);
		this.values = new ArrayList<>(values);
	}

	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment) throws IllegalArgumentException {
		List<WordSlot> slotsCopy = new ArrayList<>(this.slots);
		slotsCopy.removeIf(assignment::containsKey);
		Collections.shuffle(slotsCopy);

		if (!slotsCopy.isEmpty()) {
			return slotsCopy.get(0);
		}

		throw new IllegalArgumentException("All variables are assigned.");
	}

	@Override
	public List<WordPair> domainValues(WordSlot wordSlot, Map<WordSlot, WordPair> assignment) {
		List<WordPair> spaceDomain = wordSlot.getDomain();

		spaceDomain.removeIf(val -> !wordSlot.isValid(val, assignment));

		Collections.shuffle(spaceDomain);

		return spaceDomain;
	}

	@Override
	public boolean isConsistent(WordSlot wordSlot, WordPair wordPair, Map<WordSlot, WordPair> assignment) {
		return wordSlot.isValid(wordPair, assignment);
	}

	@Override
	public boolean isComplete(Map<WordSlot, WordPair> assignment) {
		for (WordSlot slot : this.slots) {
			if (!assignment.containsKey(slot)) {
				return false;
			}
		}

		return true;
	}

	public List<WordSlot> getSlots() {
		return this.slots;
	}
}
