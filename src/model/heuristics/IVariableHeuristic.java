package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.List;
import java.util.Map;

public interface IVariableHeuristic {

	/**
	 * Selects an unassigned variable to be the next assigned variable, order determined by implementation.
	 *
	 * @param assignment	The assignment so far
	 * @param slots	All of the wordSlots in the CSP
	 * @param model	The CSP to be solved
	 * @return	A wordSlot to be assigned
	 * @throws IllegalArgumentException	There are no more unassigned wordSlots
	 */
	WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) throws IllegalArgumentException;
}
