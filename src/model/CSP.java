package model;

import util.WordPair;

import java.util.List;
import java.util.Map;

public interface CSP <K, Z> {
	K selectUnassigned(Map<K, Z> assignment);

	List<Z> domainValues(K wordSlot, Map<K, Z> assignment);

	boolean isConsistent(K wordSlot, Z wordPair, Map<K, Z> assignment);

	boolean isComplete(Map<K, Z> assignment);
}
