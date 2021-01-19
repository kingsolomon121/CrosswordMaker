package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DegreeHeuristic implements IVariableHeuristic {

	public DegreeHeuristic() {

	}

	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) throws IllegalArgumentException {
		slots.removeIf(assignment::containsKey);

		int totalConstraints = 0;
		int highestConstraints = -1;
		WordSlot mostConstraints = null;

		for (WordSlot slot : slots) {
			totalConstraints = 0;
			for (WordSlot innerSlot : slots) {
				if (slot.getSpaces().stream().anyMatch(innerSlot.getSpaces()::contains)) {
					totalConstraints++;
				}
			}

			if (totalConstraints > highestConstraints) {
				highestConstraints = totalConstraints;
				mostConstraints = slot;
			}
		}

		if (mostConstraints == null) {
			throw new IllegalArgumentException("All variables have been assigned.");
		}

		return mostConstraints;
	}
}
