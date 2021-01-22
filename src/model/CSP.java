package model;

import util.WordPair;

import java.util.List;
import java.util.Map;

/**
 * A Constraint Satisfaction Problem Definition.
 *
 * @param <K> The type of the variables to be filled in.
 * @param <Z> The type of the values to be assigned.
 */
public interface CSP<K, Z> {

	/**
	 * Selects an unassigned variable to be assigned next. Order determined by implementation.
	 *
	 * @param assignment The assignment so far
	 * @return The variable to be assigned
	 */
	K selectUnassigned(Map<K, Z> assignment);

	/**
	 * Lists the domain of values possible for a given variable. Order determined by implementation.
	 *
	 * @param variable   The variable to be assigned
	 * @param assignment The assignment so far
	 * @return the list of values
	 */
	List<Z> domainValues(K variable, Map<K, Z> assignment);

	/**
	 * Checks if assigned the given value to the given variable would be consistent with the assignment so far.
	 *
	 * @param variable   The variable
	 * @param value      The value
	 * @param assignment the assignment so far
	 * @return is this new assignment consistent?
	 */
	boolean isConsistent(K variable, Z value, Map<K, Z> assignment);

	/**
	 * Checks if the given assignment is a complete solution for this CSP.
	 *
	 * @param assignment The assignment so far
	 * @return is it complete?
	 */
	boolean isComplete(Map<K, Z> assignment);
}
