package project6;

/**
 * This class represents an actor and contains his/her name.
 * 
 * @author Ruochen Zhao
 *
 */

public class Actor {

	private String name;

	/**
	 * A constructor that creates an actor object.
	 * 
	 * @param s, the name of the actor
	 * @throws IllegalArgumentException if the name is null.
	 */
	public Actor(String s) throws IllegalArgumentException {
		if (s != null && !s.equals("") && !s.isEmpty()) {
			name = s;
		} else {
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
	}

	/**
	 * Get the name of the actor object
	 * 
	 * @return name as a string
	 */
	public String getName() {
		return this.name;
	}

}
