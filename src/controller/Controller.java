package controller;

import model.*;
import model.heuristics.DegreeHeuristic;
import model.heuristics.IValueHeuristic;
import model.heuristics.IVariableHeuristic;
import model.heuristics.LCVHeuristic;
import util.WordPair;
import view.StandardCrosswordView;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Runtime controller that translates all the data from the model and passes it to the algorithm and View, and
 * vice versa
 */
public class Controller {
	private final String cspInput;
	private final String dictionaryInput;
	private final List<List<Space>> spaces;
	private final List<WordSlot> slots;
	private final List<WordPair> values;
	private String cluesOutput;
	private String puzzleOutput;
	private String solutionOutput;
	private IValueHeuristic valueHeuristic;
	private IVariableHeuristic variableHeuristic;


	//Constructor
	public Controller(String inputFile, String dictionaryInput) {
		this.cspInput = inputFile;
		this.dictionaryInput = dictionaryInput;
		this.spaces = new ArrayList<>();
		this.slots = new ArrayList<>();
		this.values = new ArrayList<>();
	}

	/**
	 * Starts running the program and starting up the algorithm
	 * @throws IOException when the program fails to read from a valid file
	 */
	public void go() throws IOException {
		long startTime = System.currentTimeMillis();

		CrosswordModel crosswordCSP;

		//Init scanners and readers
		Readable cspReader = new FileReader(this.cspInput);
		Readable dictReader = new FileReader(this.dictionaryInput);

		Scanner cspScanner = new Scanner(cspReader);
		Scanner dictScanner = new Scanner(dictReader);
		dictScanner.useDelimiter(",|\\n");

		//Read and format from the dictionary input
		while (dictScanner.hasNext()) {
			String word = dictScanner.next().toLowerCase();
			if (dictScanner.hasNext()) {
				dictScanner.next();
			} else {
				continue;
			}

			String defn = dictScanner.next();

			//dictScanner.next();
			//dictScanner.next();
			//System.out.printf("Word: %s, Defn: %s%n", word, defn);

			if (word.matches("[a-z]+")) {
				word = word.replaceAll("\\s", "");
				this.values.add(new WordPair(word, defn));
			}
		}

		int lineCounter = 0;

		//read and format from the template input
		while (cspScanner.hasNext()) {

			String line = cspScanner.nextLine();
			String[] chars = line.split(",");

			spaces.add(new ArrayList<>());

			for (int i = 0; i < chars.length; i++) {
				boolean isOpen = Integer.parseInt(chars[i]) == 1;

				spaces.get(lineCounter).add(new Space(i, lineCounter, isOpen));
			}

			lineCounter++;
		}

		int currentNum = 1;

		for (int y = 0; y < spaces.size(); y++) {
			for (int x = 0; x < spaces.get(0).size(); x++) {
				if (!spaces.get(y).get(x).isOpen()) {
					continue;
				}

				//Determine direction, length and numbering of word slots from the template
				boolean openToRight = x != spaces.get(0).size() - 1 && spaces.get(y).get(x + 1).isOpen();
				boolean openToLeft = x != 0 && spaces.get(y).get(x - 1).isOpen();
				boolean makeAcross = openToRight && !openToLeft;

				boolean openBelow = y != spaces.size() - 1 && spaces.get(y + 1).get(x).isOpen();
				boolean openAbove = y != 0 && spaces.get(y - 1).get(x).isOpen();
				boolean makeDown = openBelow && !openAbove;


				if (makeAcross) {
					slots.add(this.createAcrossSlot(x, y, spaces.get(0).size(), currentNum));
				}

				if (makeDown) {
					slots.add(this.createDownSlot(x, y, spaces.size(), currentNum));
				}

				if (makeDown || makeAcross) {
					currentNum++;
				}
			}
		}


		//Run the backtracking algorithm
		crosswordCSP = new HeuristicOrderingModel(spaces, slots, values, this.variableHeuristic, this.valueHeuristic);
		Map<WordSlot, WordPair> assignment = Algorithm.backtrackingSearch(crosswordCSP);

		//Generate View files based on the results of the algorithm
		StandardCrosswordView view = new StandardCrosswordView(this.cluesOutput, this.solutionOutput, this.puzzleOutput);
		view.generateClues(this.slots, assignment);
		view.generatePuzzle(this.spaces, this.slots);
		view.generateSolution(this.spaces, this.slots, assignment);

		//long endTime = System.currentTimeMillis();
		//System.out.printf("Program finished running in %s ms.%n", endTime - startTime);
	}


	private WordSlot createDownSlot(int x, int y, int maxY, int currentNum) {
		List<Space> spacesInSlot = new ArrayList<>();

		while (y != maxY && this.spaces.get(y).get(x).isOpen()) {
			spacesInSlot.add(this.spaces.get(y).get(x));
			y++;
		}


		return new WordSlot(spacesInSlot, currentNum, Direction.DOWN, this.values);
	}

	private WordSlot createAcrossSlot(int x, int y, int maxX, int currentNum) {
		List<Space> spacesInSlot = new ArrayList<>();

		while (x != maxX && this.spaces.get(y).get(x).isOpen()) {
			spacesInSlot.add(this.spaces.get(y).get(x));
			x++;
		}


		return new WordSlot(spacesInSlot, currentNum, Direction.ACROSS, this.values);
	}

	public void setCluesOutput(String cluesOutput) {
		this.cluesOutput = cluesOutput;
	}

	public void setPuzzleOutput(String puzzleOutput) {
		this.puzzleOutput = puzzleOutput;
	}

	public void setSolutionOutput(String solutionOutput) {
		this.solutionOutput = solutionOutput;
	}

	public void setValueHeuristic(IValueHeuristic h) {
		this.valueHeuristic = h;
	}

	public void setVariableHeuristic(IVariableHeuristic h) {
		this.variableHeuristic = h;
	}
}
