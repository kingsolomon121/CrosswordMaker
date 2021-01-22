package model;

import model.heuristics.IValueHeuristic;
import model.heuristics.IVariableHeuristic;
import util.WordPair;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Extends the CrosswordModel and adds support for Heuristic ordering of both variables and values.
 */
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
