package model.heuristics;

/**
 * Enum for the types of Variable Heuristics supported.
 */
public enum VariableHeuristicType {
	MRVHEURISTIC, DEGREEHEURISTIC, ORDERHEURISTIC;

	@Override
	public String toString() {
		return name().toLowerCase().replaceAll("heuristic", "");
	}
}
