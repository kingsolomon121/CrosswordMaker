package model.heuristics;

import model.CrosswordModel;
import model.WordSlot;
import util.WordPair;

import java.util.List;
import java.util.Map;

public interface IValueHeuristic {
	List<WordPair> domainValues(WordSlot wordSlot, Map<WordSlot, WordPair> assignment, List<WordSlot> slots,
															CrosswordModel model);
}
