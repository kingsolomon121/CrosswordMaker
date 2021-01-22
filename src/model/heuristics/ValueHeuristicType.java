package model.heuristics;

/**
 * Enum for the types of Value Heuristics supported.
 */
public enum ValueHeuristicType {
	LCVHEURISTIC;

	@Override
	public String toString() {
		return name().toLowerCase().replaceAll("heuristic", "");
	}
}
