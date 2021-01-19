package view;

import model.Space;
import model.WordSlot;
import util.WordPair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StandardCrosswordView {

	private final String cluesFile;
	private final String solutionFile;
	private final String puzzleFile;

	public StandardCrosswordView(String cluesFile, String solutionFile, String puzzleFile) {
		this.cluesFile = cluesFile;
		this.solutionFile = solutionFile;
		this.puzzleFile = puzzleFile;
	}

	public void generateClues(List<WordSlot> slots, Map<WordSlot, WordPair> assignment) throws IOException {
		FileWriter writer = new FileWriter(this.cluesFile);

		for (WordSlot slot : slots) {
			writer.append(String.format("%s: %s%n", slot, assignment.get(slot).getDefinition()));
		}

		writer.close();
	}

	public void generatePuzzle(List<List<Space>> spaces, List<WordSlot> slots) throws IOException {
		int width = spaces.get(0).size() * 50;
		int height = spaces.size() * 50;

		BufferedImage buffImag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = buffImag.createGraphics();
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		g.setStroke(new BasicStroke(2));
		g.drawRect(0,0,width,height);

		Map<Space, Integer> numberedSpaces = new HashMap<>();

		for (WordSlot slot : slots) {
			Space toNumber = slot.getSpaces().get(0);
			numberedSpaces.put(toNumber, slot.getNum());
		}

		for (int row = 0; row < spaces.size(); row++) {
			for (int col = 0; col < spaces.get(0).size(); col++) {
				Space current = spaces.get(row).get(col);

				if (current.isOpen()) {
					g.setColor(Color.white);
				} else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(col * 50, row * 50, 50, 50);

				if (numberedSpaces.containsKey(current)) {
					g.setColor(Color.black);

					String number = numberedSpaces.get(current).toString();

					Rectangle2D rect = g.getFontMetrics().getStringBounds(number, g);

					float w = (float) rect.getWidth() / 2;
					float h = (float) rect.getHeight() / 2;

					g.drawString(number, col * 50 + 10 - w, row * 50 + 10 + h);
				}
			}
		}

		g.dispose();

		File file = new File(this.puzzleFile);
		ImageIO.write(buffImag, "png", file);
	}

	public void generateSolution(List<List<Space>> spaces, List<WordSlot> slots, Map<WordSlot, WordPair> assignment) throws IOException {
		int width = spaces.get(0).size() * 50;
		int height = spaces.size() * 50;

		BufferedImage buffImag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = buffImag.createGraphics();
		g.setStroke(new BasicStroke(10));
		g.drawRect(0,height,width,height);

		Map<Space, Character> letteredSpaces = new HashMap<>();
		Map<Space, Integer> numberedSpaces = new HashMap<>();

		for (WordSlot slot : slots) {
			Space toNumber = slot.getSpaces().get(0);
			numberedSpaces.put(toNumber, slot.getNum());
		}

		for (WordSlot slot : slots) {
			List<Space> slotSpaces = slot.getSpaces();
			String word = assignment.get(slot).getWord();

			for (int i = 0; i < slotSpaces.size(); i++) {
				letteredSpaces.put(slotSpaces.get(i), word.charAt(i));
			}
		}

		for (int row = 0; row < spaces.size(); row++) {
			for (int col = 0; col < spaces.get(0).size(); col++) {
				Space current = spaces.get(row).get(col);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));

				if (current.isOpen()) {
					g.setColor(Color.white);
					g.fillRect(col * 50, row * 50, 50, 50);

					g.setColor(Color.black);
					String letter = letteredSpaces.get(current).toString();

					Rectangle2D rect = g.getFontMetrics().getStringBounds(letter, g);

					float w = (float) (rect.getWidth() / 2);
					float h = (float) (rect.getHeight() / 2);

					g.drawString(letter, col * 50 + 25- w, row * 50 + 25 + h);

					if (numberedSpaces.containsKey(current)) {
						g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
						g.setColor(Color.black);

						String number = numberedSpaces.get(current).toString();

						Rectangle2D rect2 = g.getFontMetrics().getStringBounds(number, g);

						float w2 = (float) rect2.getWidth() / 2;
						float h2 = (float) rect2.getHeight() / 2;

						g.drawString(number, col * 50 + 10 - w2, row * 50 + 10 + h2);
					}
				} else {
					g.setColor(Color.black);
					g.fillRect(col * 50, row * 50, 50, 50);
				}
			}
		}

		g.dispose();

		File file = new File(this.solutionFile);
		ImageIO.write(buffImag, "png", file);
	}
}
