package controller;

import model.CSP;
import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.*;

/**
 * Contains the code for the recursive backtracking algorithm.
 */
public class Algorithm {

	/**
	 * Begins the recursive algorithm.
	 *
	 * @param csp The Constraint Satisfaction Problem to be solved
	 * @return A set of slot, solution pairs that defines a valid assignment of the CSP
	 */
	public static Map<WordSlot, WordPair> backtrackingSearch(CrosswordModel csp) {
		Map<WordSlot, WordPair> solution;

		try {
			solution = recursiveBacktracking(new HashMap<>(), csp);
		} catch (IllegalStateException ex) {
			throw new IllegalStateException("There are no valid fillings for this template.");
		}


		if (solution == null) {
			throw new IllegalStateException("There are no valid fillings for this template.");
		}

		return solution;
	}

	/**
	 * Recursively assigns values to potential wordSlots until a valid assignment is found or no assignment can be found.
	 *
	 * @param assignment The assignment of slot, solution pairs so far (Acuumulator)
	 * @param csp        The CSP to be solved
	 * @throws IllegalStateException if no valid assignments exist
	 * @return A valid assignment for the CSP
	 */
	private static Map<WordSlot, WordPair> recursiveBacktracking(Map<WordSlot, WordPair> assignment, CrosswordModel csp)
					throws IllegalStateException {
		Map<WordSlot, WordPair> result;

		if (csp.isComplete(assignment)) {
			return assignment;
		}

		//System.out.printf("%d out of %d assignments completed.%n", assignment.size(), csp.getSlots().size());

		WordSlot currentSlot = csp.selectUnassigned(assignment);
		List<WordPair> inferences = new ArrayList<>();

		List<WordPair> domainValues = csp.domainValues(currentSlot, assignment);

		//for each value in the domain (random order) try the assignment and see if its valid.
		for (WordPair value : domainValues) {
			//System.out.printf("Testing assignment: %s on WordSlot: %s%n", value, currentSlot);
			assignment.put(currentSlot, value);

			//if this assignment makes any other space have a domain of 0, filter it out.
			if (csp.getSlots().stream().anyMatch((ws) -> csp.domainValues(ws, assignment).isEmpty())) {
				System.out.printf("Filtered out assignment %s from %s%n", value, currentSlot);
				assignment.remove(currentSlot);
				currentSlot.removeFromDomain(value);
				inferences.add(value);
				continue;
			}

			System.out.printf("Assigned %s to %s%n", currentSlot, value);

			//Maintain this assignment and move on to the next wordSlot.
			result = recursiveBacktracking(assignment, csp);
			if (result != null) {
				//Solution found, return it.
				return result;
			} else {
				//There were no solutions found down this path, back up and start again.
				assignment.remove(currentSlot);
				currentSlot.removeFromDomain(value);
				inferences.add(value);
				System.out.printf("Failed to assign %s to %s%n", currentSlot, value);
			}
		}

		currentSlot.addToDomain(inferences);
		return null;
	}

	//Failed attempt at implementing an iterative rather than recursive backtracking solutions, in the hopes of avoiding
	// overflowing the stack
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