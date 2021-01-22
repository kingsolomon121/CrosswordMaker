package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.List;
import java.util.Map;

public interface IValueHeuristic {
	/**
	 * Supplies the list of possible values that can be tested for the given word, with the ordering determined by
	 * implementation.
	 *
	 * @param wordSlot   The wordSlot to be filled in
	 * @param assignment The assignment so far
	 * @param slots      The list of all wordSlots in the CSP.
	 * @param model      The CSP to be solved.
	 * @return A list of possible values for the given wordSlot
	 */
	List<WordPair> domainValues(WordSlot wordSlot, Map<WordSlot, WordPair> assignment, List<WordSlot> slots,
															CrosswordModel model);
}
