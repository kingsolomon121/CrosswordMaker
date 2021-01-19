package controller;

import model.CSP;
import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.*;

public class Algorithm {
	public static Map<WordSlot, WordPair> backtrackingSearch(CrosswordModel csp) {
		Map<WordSlot, WordPair> solution;

		try {
			solution = recursiveBacktracking(new HashMap<WordSlot, WordPair>(), csp);
		} catch (IllegalStateException ex) {
			throw new IllegalStateException("There are no valid fillings for this template.");
		}


		if (solution == null) {
			throw new IllegalStateException("There are no valid fillings for this template.");
		}

		return solution;
	}

	private static Map<WordSlot, WordPair> recursiveBacktracking(Map<WordSlot, WordPair> assignment, CrosswordModel csp)
					throws IllegalStateException {
		Map<WordSlot, WordPair> result;

		if (csp.isComplete(assignment)) {
			return assignment;
		}

		System.out.printf("%d out of %d assignments completed.%n", assignment.size(), csp.getSlots().size());

		WordSlot currentSlot = csp.selectUnassigned(assignment);
		List<WordPair> inferences = new ArrayList<>();

		List<WordPair> domainValues = csp.domainValues(currentSlot, assignment);
		for (WordPair value : domainValues) {
			//System.out.printf("Testing assignment: %s on WordSlot: %s%n", value, currentSlot);
			assignment.put(currentSlot, value);

			if (csp.getSlots().stream().anyMatch((ws) -> csp.domainValues(ws, assignment).isEmpty())) {
				System.out.printf("Filtered out assignment %s from %s%n", value, currentSlot);
				assignment.remove(currentSlot);
				currentSlot.removeFromDomain(value);
				inferences.add(value);
				continue;
			}

			System.out.printf("Assigned %s to %s%n", currentSlot, value);
			result = recursiveBacktracking(assignment, csp);
			if (result != null) {
				return result;
			} else {
				assignment.remove(currentSlot);
				currentSlot.removeFromDomain(value);
				inferences.add(value);
				System.out.printf("Failed to assign %s to %s%n", currentSlot, value);
			}
		}

		currentSlot.addToDomain(inferences);
		return null;
	}

	private static Map<WordSlot, WordPair> iterativeBacktracking(Map<WordSlot, WordPair> assignment, CrosswordModel csp)
					throws IllegalStateException {
		Stack<Map<WordSlot, WordPair>> stack = new Stack<>();
		stack.push(assignment);

		while (!stack.isEmpty()) {
			Map<WordSlot, WordPair> currentAssignment = stack.peek();

			if (csp.isComplete(currentAssignment)) {
				return stack.pop();
			}

			WordSlot current = csp.selectUnassigned(currentAssignment);

			for (WordPair wp : csp.domainValues(current, currentAssignment)) {
				if (csp.isConsistent(current, wp, currentAssignment)) {
					currentAssignment.put(current, wp);
					stack.push(currentAssignment);
				}

				if (csp.getSlots().stream().anyMatch((ws) -> csp.domainValues(ws, currentAssignment).isEmpty())) {
					current.removeFromDomain(wp);
					stack.pop();
				}
			}


		}
		return null;
	}
}