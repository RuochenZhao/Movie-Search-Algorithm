package project6;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This contains the main class, parses out the csv data provided in the command
 * line, and processes it.
 * 
 * @author zhaoruochen
 *
 */

public class SFMovieData {

	public static void main(String[] args) {
		if (args.length == 0) {
			IllegalArgumentException e = new IllegalArgumentException();
			System.err.println("Usage Error: the program expects file name as an argument.");
			throw e;
		}
		Scanner s = null;
		try {
			s = new Scanner(new File(args[0]));
		} catch (Exception e) {
			System.err.println("Error: the file " + args[0] + "cannot be opened.");
		}
		MovieList movies = new MovieList();
		// read the file and put it into the movies arrayList
		//skip the first line
		s.nextLine();
		while (s.hasNextLine()) {
			boolean n2=false;
			String line = s.nextLine();
			ArrayList<String> parsed = splitCSVLine(line);
			// if this is a valid line
			// i.e. Has more than 10 entries, title is nonempty, year is valid, location is
			// nonempty, has an actor1
			if (parsed.size() >= 9 && parsed.get(0) != null && parsed.get(0) != ""
					&& Integer.parseInt(parsed.get(1)) <= 2020 && Integer.parseInt(parsed.get(1)) >= 1900
					&& parsed.get(2) != null && parsed.get(2) != "") {
				// check if this is the same title as anything in the movies arraylist
				// if not, we add the new movie
				// if this has more than one actors, we create the actors
				Actor a1=null, a2 = null, a3 = null;
				if (parsed.size()==9) {
					a1 = new Actor(parsed.get(parsed.size()-1));
					a2 = null;
					a3 = null;
				}
				if (parsed.size()==10) {
					if(parsed.get(parsed.size()-2)!= null && !parsed.get(parsed.size()-2).equals("") && !parsed.get(parsed.size()-2).isEmpty())
						a1 = new Actor(parsed.get(parsed.size()-2));
					else
						break;
					if(parsed.get(parsed.size()-1)!= null && !parsed.get(parsed.size()-1).equals("") && !parsed.get(parsed.size()-1).isEmpty())
						a2 = new Actor(parsed.get(parsed.size()-1));
					else
						a2=null;
					a3 = null;
				}
				if (parsed.size()==11) {
					a1 = new Actor(parsed.get(parsed.size()-3));
					a2 = new Actor(parsed.get(parsed.size()-2));
					a3 = new Actor(parsed.get(parsed.size()-1));
				}
				Movie m = new Movie(parsed.get(0), Integer.parseInt(parsed.get(1)), parsed.get(6), parsed.get(7),
						a1, a2, a3);
				if (!parsed.get(2).isEmpty()) {
					Location l = new Location(parsed.get(2), parsed.get(3));
					//System.out.println(l.getLocation());
					if (l != null) {
						m.addLocation(l);
					}
				}
				if (movies.contains(m)) {
					// if it's the same, we add the location
					if (!parsed.get(2).isEmpty()) {
						Location l = new Location(parsed.get(2), parsed.get(3));
						movies.get(m).addLocation(l);
					}
				} else {
					movies.add(m);
				}
			}
		}
		// prompt user for query
		Scanner input = new Scanner(System.in);
		System.out.println("Search the database by matching keywords to titles or actor names.\n"
				+ "To search for matching titles, enter\n" + "   title KEYWORD\n"
				+ "To search for matching actor names, enter\n" + "   actor KEYWORD\n"
				+ "To finish the program, enter\n" + "   quit\n");
		String kind = "";
		boolean stop = false;
		while (!stop) {
			System.out.println("Enter your search query:");
			String inputLine = input.nextLine();
			if (inputLine.toLowerCase().equals("quit")){
				stop = true;
				continue;
			}
			MovieList matched = new MovieList();
			if (inputLine.substring(0, 5).toLowerCase().equals("actor")) {
				matched = movies.getMatchingActor(inputLine.substring(6,inputLine.length()));
				if (matched == null) {
					System.out.println("No matches found. Try again.");
				} else {
					Iterator it = matched.iterator();
					while (it.hasNext()) {
						System.out.println(it.next().toString() + "\n\n");
					}
				}
			}
			else if (inputLine.substring(0, 5).toLowerCase().equals("title")) {
				matched = movies.getMatchingTitles(inputLine.substring(6,inputLine.length()));
				if (matched == null) {
					System.out.println("No matches found. Try again.");
					continue;
				} else {
					Iterator it = matched.iterator();
					while (it.hasNext()) {
						System.out.println(it.next().toString() + "\n\n");
					}
				}
			} else {
				System.out.println("This is not a valid query. Try again.");
			}
		}
		System.exit(0);
		}

	// the parse method
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may
	 * contain commas)
	 * 
	 * @author Joanna Klukowska
	 * @param textLine
	 *            a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that
	 *         line
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		if (textLine == null)
			return null;

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry = false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} else {
					insideQuotes = true;
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if (insideQuotes || insideEntry) {
					// add it to the current entry
					nextWord.append(nextChar);
				} else { // skip all spaces between entries
					continue;
				}
			} else if (nextChar == ',') {
				if (insideQuotes) { // comma inside an entry
					nextWord.append(nextChar);
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
}
