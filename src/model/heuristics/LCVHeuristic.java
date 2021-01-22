package model.heuristics;

import com.sun.source.tree.Tree;
import model.CrosswordModel;
import model.Space;
import model.WordSlot;
import util.WordPair;

import java.util.*;

/**
 * Chooses the value which least constrains the remaining variables. This promotes finding optimal solutions on early
 * tries with less failure. Hinges on the fact that there exist more than one possible solution, so searching for a
 * solution instead of failing may be more efficent.
 */
public class LCVHeuristic implements IValueHeuristic {

	@Override
	public List<WordPair> domainValues(WordSlot wordSlot, Map<WordSlot, WordPair> assignment, List<WordSlot> slots, CrosswordModel model) {
		slots.removeIf(assignment::containsKey);

		List<WordSlot> newSlots = new ArrayList<>();

		for (WordSlot slot : slots) {
			List<Space> wordSpaces = wordSlot.getSpaces();
			wordSpaces.retainAll(slot.getSpaces());

			if (wordSpaces.size() != 0) {
				newSlots.add(slot);
			}
		}

		TreeMap<Integer, WordPair> map = new TreeMap<>();
		int sumOfNeighbors;

		for (WordPair word : model.domainValues(wordSlot, assignment)) {
			sumOfNeighbors = 0;

			assignment.put(wordSlot, word);

			for (WordSlot slot : newSlots) {
				sumOfNeighbors += model.domainValues(slot, assignment).size();
			}
			map.put(sumOfNeighbors, word);
			assignment.remove(wordSlot);
		}

		List<WordPair> output = new ArrayList<>();

		for (Map.Entry<Integer, WordPair> entry : map.entrySet()) {
			output.add(entry.getValue());
		}

		Collections.reverse(output);

		return output;
	}
}
