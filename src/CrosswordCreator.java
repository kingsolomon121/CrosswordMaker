import controller.Controller;
import model.heuristics.*;

import java.io.IOException;
import java.util.Objects;

public class CrosswordCreator {
	public static void main(String[] args) {
		ControllerBuilder builder = new ControllerBuilder(args);
		Controller controller = builder.build();

		if (controller == null) {
			return;
		}

		try {
			controller.go();
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	private static class ControllerBuilder {
		private final String[] args;

		public ControllerBuilder(String[] args) {
			this.args = args;
		}

		public Controller build() {
			String template = null;
			String dictionary = null;
			String cluesOutput = null;
			String puzzleOutput = null;
			String solutionOutput = null;
			IValueHeuristic valueHeuristic = null;
			IVariableHeuristic variableHeuristic = null;

			for (int i = 0; i < args.length; i += 2) {
				if (args[i].equalsIgnoreCase("-template")) {
					template = args[i + 1];
				}

				if (args[i].equalsIgnoreCase("-dictionary")) {
					dictionary = args[i + 1];
				}

				if (args[i].equalsIgnoreCase("-clues")) {
					cluesOutput = args[i + 1];
				}

				if (args[i].equalsIgnoreCase("-puzzle")) {
					puzzleOutput = args[i + 1];
				}

				if (args[i].equalsIgnoreCase("-solution")) {
					solutionOutput = args[i + 1];
				}

				if (args[i].equalsIgnoreCase("-valueH")) {
					String type = args[i + 1];

					if (type.equalsIgnoreCase(ValueHeuristicType.LCVHEURISTIC.toString())) {
						valueHeuristic = new LCVHeuristic();
					}
				}

				if (args[i].equalsIgnoreCase("-varH")) {
					String type = args[i + 1];

					if (type.equals(VariableHeuristicType.DEGREEHEURISTIC.toString())) {
						variableHeuristic = new DegreeHeuristic();
					} else if (type.equals(VariableHeuristicType.MRVHEURISTIC.toString())) {
						variableHeuristic = new MRVHeuristic();
					} else if  (type.equals(VariableHeuristicType.ORDERHEURISTIC.toString())) {
						variableHeuristic = new OrderHeuristic();
					}
				}

				if (args[i].equalsIgnoreCase("--help")) {
					System.out.println(helpOutput());
					return null;
				}
			}

			Objects.requireNonNull(template);
			Objects.requireNonNull(dictionary);

			if (cluesOutput == null) {
				cluesOutput = "output\\clues\\clues.txt";
			}

			if (puzzleOutput == null) {
				puzzleOutput = "output\\puzzles\\puzzle.png";
			}

			if (solutionOutput == null) {
				solutionOutput = "output\\solutions\\solution.png";
			}

			Controller controller = new Controller(template, dictionary);

			controller.setCluesOutput(cluesOutput);
			controller.setPuzzleOutput(puzzleOutput);
			controller.setSolutionOutput(solutionOutput);

			controller.setValueHeuristic(valueHeuristic);
			controller.setVariableHeuristic(variableHeuristic);

			return controller;
		}
	}

	public static String helpOutput() {
		return String.format("-template : specify a text file as a template%n" +
						"-dictionary : specify the csv file to draw words from%n" +
						"-clues : the output .txt file for the clues (OPTIONAL)%n" +
						"-puzzle : the output .png file for the puzzle (OPTIONAL)%n" +
						"-solution : the solution .png for the solution (OPTIONAL)%n" +
						"-valueH : NOT CURRENTLY SUPPORTED%n" +
						"-varH : specify the variable heuristic to be used (mvr or degree)%n");
	}
}
