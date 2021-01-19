package model.heuristics;

public enum ValueHeuristicType {
	LCVHEURISTIC;

	@Override
	public String toString() {
		return name().toLowerCase().replaceAll("heuristic", "");
	}
}
