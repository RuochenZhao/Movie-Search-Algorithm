package project6;

/**
 * This class creates a location and holds its name and fun facts.
 * 
 * @author Ruochen Zhao
 *
 */

public class Location {

	private String location;
	private String FunFact = null;

	/**
	 * A constructor that creates a copy of the Location class
	 * 
	 * @param l,  the name of the location
	 * @param f, the fun fact associated with the location
	 * @throws IllegalArgumentException if the name is null
	 */
	public Location(String l, String f) throws IllegalArgumentException {
		if (l != null && !l.equals("") && !l.isEmpty()) {
			location = l;
			FunFact = f;
		} else {
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
	}

	/**
	 * Gets the location name
	 * 
	 * @return location name as a string
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the fun fact associated with the location
	 * 
	 * @return fun fact as a string
	 */
	public String getFunFact() {
		return FunFact;
	}
}
