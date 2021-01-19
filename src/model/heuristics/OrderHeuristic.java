package model.heuristics;

import model.CrosswordModel;
import model.Space;
import model.WordSlot;
import util.WordPair;

import java.util.*;

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
