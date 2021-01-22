package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.*;

/**
 * Chooses the variable with the smallest remaining domain. Promotes finding failure states as early as possible and
 * backtracking out of solution barren state spaces quickly.
 */
public class MRVHeuristic implements IVariableHeuristic {

	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) throws IllegalArgumentException {
		slots.removeIf(assignment::containsKey);

		Map<WordSlot, Integer> slotCounts = new HashMap<>();

		for (WordSlot slot : slots) {
			slotCounts.put(slot, model.domainValues(slot, assignment).size());
		}

		List<Integer> sorted = new ArrayList<>(slotCounts.values());

		Collections.sort(sorted);

		int minimum = sorted.get(0);

		for (Map.Entry<WordSlot, Integer> entry : slotCounts.entrySet()) {
			if (entry.getValue() == minimum) {
				return entry.getKey();
			}
		}

		throw new IllegalArgumentException("There are no more unassigned variables.");
	}
}
