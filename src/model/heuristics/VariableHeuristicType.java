package model.heuristics;

public enum VariableHeuristicType {
	MRVHEURISTIC, DEGREEHEURISTIC, ORDERHEURISTIC;

	@Override
	public String toString() {
		return name().toLowerCase().replaceAll("heuristic", "");
	}
}
