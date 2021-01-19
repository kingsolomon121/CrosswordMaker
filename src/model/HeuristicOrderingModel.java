package model;

import model.heuristics.IValueHeuristic;
import model.heuristics.IVariableHeuristic;
import util.WordPair;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HeuristicOrderingModel extends CrosswordModel {

	private final IVariableHeuristic variableHeuristic;
	private final IValueHeuristic valueHeuristic;

	public HeuristicOrderingModel(List<List<Space>> spaces, List<WordSlot> slots, List<WordPair> values,
																IVariableHeuristic variableHeuristic, IValueHeuristic valueHeuristic) {
		super(spaces, slots, values);
		this.variableHeuristic = variableHeuristic;
		this.valueHeuristic = valueHeuristic;
	}
	@Override
	public WordSlot selectUnassigned(Map<WordSlot, WordPair> assignment) throws IllegalArgumentException {
		if (this.variableHeuristic == null) {
			return super.selectUnassigned(assignment);
		}

		return this.variableHeuristic.selectUnassigned(new HashMap<>(assignment), new ArrayList<>(this.slots),
						new CrosswordModel(this.spaces, this.slots, this.values));
	}


	@Override
	public List<WordPair> domainValues(WordSlot wordSlot, Map<WordSlot, WordPair> assignment) {
		if (this.valueHeuristic == null) {
			return super.domainValues(wordSlot, assignment);
		}

		return this.valueHeuristic.domainValues(wordSlot, new HashMap<>(assignment), new ArrayList<>(this.slots),
						new CrosswordModel(this.spaces, this.slots, this.values));
	}
}

	/*List<WordPair> spaceDomain = wordSlot.getDomain();
	List<Map.Entry<WordPair, Integer>> list = new LinkedList<>();
	Map<WordSlot, WordPair> assignmentCopy = new HashMap<>(assignment);

		spaceDomain.removeIf(val -> !wordSlot.isValid(val, assignment));

						int total;

						for (WordPair value : spaceDomain) {
						assignmentCopy.put(wordSlot, value);

						total = 0;

						for (WordSlot slot : this.slots) {
						total += super.domainValues(slot, assignmentCopy).size();
						}
						list.add(new AbstractMap.SimpleEntry<>(value, total));
				//assignmentSize.put(value, this.slots.stream().mapToInt((ws) -> super.domainValues(ws, assignmentCopy).size()).sum());
				assignmentCopy.remove(wordSlot);
				}

				list.sort(Map.Entry.comparingByValue());
				List<WordPair> output = new ArrayList<>();
				for (Map.Entry<WordPair, Integer> me : list) {
				output.add(me.getKey());
				}

				return output;*/