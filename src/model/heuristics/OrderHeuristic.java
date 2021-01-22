package model.heuristics;

import model.CrosswordModel;
import model.Space;
import model.WordSlot;
import util.WordPair;

import java.util.*;

/**
 * Tries to fill in clusters of clues at the same time. Promotes solving a 4x4 square for example before starting to
 * fill in other parts of the puzzle.
 */
public class OrderHeuristic implements IVariableHeuristic {
	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) throws IllegalArgumentException {
		slots.removeIf(assignment::containsKey);

		Set<Space> visited = new HashSet<>();

		for (WordSlot slot : assignment.keySet()) {
			visited.addAll(slot.getSpaces());
		}

		for (WordSlot slot : slots) {
			if (visited.stream().anyMatch(slot.getSpaces()::contains)) {
				return slot;
			}
		}

		return slots.get(0);
	}
}
