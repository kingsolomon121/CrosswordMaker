package model;

/**
 * A Space which contains a single character. Is part of a wordSlot.
 */
public class Space {
	private final int x;
	private final int y;
	private final boolean isOpen;

	public Space(int x, int y, boolean isOpen) {
		this.x = x;
		this.y = y;
		this.isOpen = isOpen;
	}

	public boolean isOpen() {
		return this.isOpen;
	}
}
