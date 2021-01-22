package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adjusts the variable ordering such that the variable with the most connections to other variables (highest degree),
 * will be assigned first. This promotes filling in difficult slots early and failing early and often.
 */
public class DegreeHeuristic implements IVariableHeuristic {

	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) throws IllegalArgumentException {
		slots.removeIf(assignment::containsKey);

		int totalConstraints;
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
