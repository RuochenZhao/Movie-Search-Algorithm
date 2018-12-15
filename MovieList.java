package project6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class holds a list of the movie objects in the form of a binary search tree.
 * It extends the BST class
 * @author zhaoruochen
 *
 */

public class MovieList extends BST<Movie> {
	BST<Movie> MovieList;

	/**
	 * A constructor that creates an instance of the MovieList class
	 */
	public MovieList() {
		BST<Movie> MovieList = new BST<Movie>();
	}

	/**
	 * Get a MovieList object that has the list of movies which contains the keyword
	 * parameter in their titles.
	 * 
	 * @param keyword, the word to be found in the titles
	 * @return a MovieList object
	 */
	public MovieList getMatchingTitles(String keyword) {
		if (keyword == null || keyword.equals("")) {
			return null;
		}
		MovieList titles = new MovieList();
		Iterator<Movie> it = this.iterator();
		while (it.hasNext()) {
			Movie m = it.next();
			if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
				titles.add(m);
			}
		}
		if (titles.size() == 0) {
			return null;
		}
		return titles;
	}

	/**
	 * Get a MovieList object that has the list of movies which contains the keyword
	 * parameter in their actor names.
	 * 
	 * @param keyword, the word to be found in the titles
	 * @return a MovieList object
	 */
	public MovieList getMatchingActor(String keyword) {
		if (keyword == null || keyword.equals("")) {
			return null;
		}
		MovieList actorsList = new MovieList();
		Iterator<Movie> it = this.iterator();
		while(it.hasNext()) {
			Movie m = it.next();
			for (int i=0;i<3;i++) {
				if(m.getActor()[i]!= null&& m.getActor()[i].getName().toLowerCase().contains(keyword.toLowerCase()))
					actorsList.add(m);
			}
		}
		if (actorsList.size() == 0) {
			return null;
		}
		return actorsList;
	}

	/**
	 * Puts the information associated with the MovieList object into a string
	 * 
	 * @return a string that contains information on every movie in the MovieList
	 *         object provided by their "toString" methods
	 */
	@Override
	public String toString() {
		if(this.size()==0) {
			return "";
		}
		Iterator it = this.iterator();
		String a=""+((Movie)it.next()).getTitle();
		while (it.hasNext()) {
			a += "; " + ((Movie)it.next()).getTitle();
		}
		return a;
	}
}
