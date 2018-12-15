package project6;

import java.util.ArrayList;

/**
 * This class creates a movie that has a title, release year. And sometimes has
 * director, writer, locations, and actors.
 * 
 * @author zhaoruochen
 *
 */

public class Movie implements Comparable<Movie> {

	private String title;
	private int year;
	private ArrayList<Location> locations = new ArrayList<>();
	private String director = "";
	private String writer = "";
	private Actor[] actors = new Actor[3];

	/**
	 * A constructor that creates a copy of the movie class
	 * 
	 * @param t, the title of the movie
	 * @param y, the release year of the movie
	 * @throws IllegalArgumentException  if the title is null or the release year is not between 1900 and 2020
	 */
	public Movie(String t, int y) throws IllegalArgumentException {
		if (t != null && !t.equals("") && !t.isEmpty() && y <= 2020 && y >= 1900) {
			title = t;
			year = y;
		} else {
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
	}

	/**
	 * A constructor that creates a copy of the movie class
	 * 
	 * @param t, the title of the movie
	 * @param y, the release year of the movie
	 * @param d, the director of the movie
	 * @param w, the writer of the movie
	 * @param a1, the first actor of the movie
	 * @param a2, the second actor of the movie
	 * @param a3, the third actor of the movie
	 * @throws IllegalArgumentException if the title is null or the release year is not between 1900 and 2020
	 */
	public Movie(String t, int y, String d, String w, Actor a1, Actor a2, Actor a3) throws IllegalArgumentException {
		if (t != null && !t.isEmpty() && !t.equals("") && y <= 2020 && y >= 1900 && a1 != null) {
			title = t;
			year = y;
			director = d;
			writer = w;
			actors[0] = a1;
			actors[1] = a2;
			actors[2] = a3;
		} else {
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
	}

	/**
	 * Adds location to the list of locations associated with the movie object
	 * 
	 * @param loc, the Location object to be added
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public void addLocation(Location loc) throws IllegalArgumentException {
		if (loc != null) {
			this.locations.add(loc);
			//System.out.println(this.locations.size());
		} else {
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
	}

	/**
	 * Compare two movies based on the title and the year
	 * 
	 * @param m, a movie object to be compared with this object
	 * @return 0 if equals, 1 if this is bigger, -1 if this is smaller
	 */
	public int compareTo(Movie m) {
		if (this.equals(m)) {
			return 0;
		} else if (this.year > m.year) {
			return 1;
		} else if (this.year < m.year) {
			return -1;
		} else {
			return this.title.compareTo(m.title);
		}
	}

	/**
	 * Check and see if this object is equal with another movie object
	 * 
	 * @param m, the movie object to be compared with
	 * @return true if the two are equal (same case-insensitive title and same year)
	 */
	@Override
	public boolean equals(Object m) {
		if (!(m instanceof Movie)){
			return false;
		}
		Movie movie = (Movie) m;
		if (this.year == movie.year && this.title.toLowerCase().equals(movie.title.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Puts the information associated with the movie object into a string
	 * 
	 * @return A string that has information such as title, year, director, writer, actors, and locations
	 */
	@Override
	public String toString() {
		String a = this.title + "(" + this.year + ")" + "\n--------------------------\n" + "director             :"
				+ this.director + "\nwriter               :" + this.writer + "\nstarring             :";
		if (this.actors[0] != null) {
			a += this.actors[0].getName() + ", ";
		}
		if (this.actors[1] != null) {
			a += this.actors[1].getName() + ", ";
		}
		if (this.actors[2] != null) {
			a += this.actors[2].getName() + ", ";
		}
		a += "\nfilmed on location at:";
		for (int i = 0; i < this.locations.size(); i++) {
			a += "\n    " + this.locations.get(i).getLocation();
			if (this.locations.get(i).getFunFact()!=null&&!this.locations.get(i).getFunFact().isEmpty()&&this.locations.get(i).getFunFact()!="") {
					a += " (" + locations.get(i).getFunFact() + ")";
				}
			}
		return a;
	}

	/**
	 * Gets the title of the movie
	 * 
	 * @return title of the movie as a string
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets title of the movie
	 * 
	 * @param t, new title of the movie
	 */
	public void setTitle(String t) {
		this.title = t;
	}

	/**
	 * gets release year of the movie.
	 * 
	 * @return release year of the movie as an integer.
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Sets release year of the movie.
	 * 
	 * @param y, the new release year of the movie.
	 */
	public void setYear(int y) {
		this.year = y;
	}

	/**
	 * Get director of the movie.
	 * 
	 * @return director of the movie as a string.
	 */
	public String getDirector() {
		return this.director;
	}

	/**
	 * Sets director of the movie.
	 * 
	 * @param t, the name of the new director of the movie as a string.
	 */
	public void setDirector(String t) {
		this.director = t;
	}

	/**
	 * Gets the writer of the movie.
	 * 
	 * @return writer name of the movie as a string.
	 */
	public String getWriter() {
		return this.writer;
	}

	/**
	 * Sets writer of the movie.
	 * 
	 * @param t, the new writer name of the movie.
	 */
	public void setWriter(String t) {
		this.writer = t;
	}

	/**
	 * Get the actor of the movie.
	 * 
	 * @return the list of actor objects associated with the movie.
	 */
	public Actor[] getActor() {
		return this.actors;
	}

	/**
	 * Sets actor list associated with the movie object.
	 * 
	 * @param a, the list of actors associated with the movie.
	 */
	public void setActor(Actor[] a) {
		this.actors = a;
	}

	/**
	 * Gets the ArrayList of locations associated with the movie.
	 * 
	 * @return the ArrayList of locations associated with the movie.
	 */
	public ArrayList<Location> getLocation() {
		return this.locations;
	}

	/**
	 * Sets the ArrayList of locations associated with the movie
	 * 
	 * @param l, the new ArrayList of locations associated with the movie
	 */
	public void setLocation(ArrayList<Location> l) {
		this.locations = l;
	}
}
