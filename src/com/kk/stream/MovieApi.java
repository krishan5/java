package com.kk.stream;

import java.util.LinkedList;
import java.util.List;

public class MovieApi {
	
	private static List<Movie> movies;
	
	public static void loadMovies() {
		movies = new LinkedList<>();
		movies.add(new Movie("Dangal", 2016, Language.HINDI));
		movies.add(new Movie("Baahubali 2: The Conclusion", 2017, Language.TELUGU));
		movies.add(new Movie("Bajrangi Bhaijaan", 2015, Language.HINDI));
		movies.add(new Movie("Baahubali: The Beginning", 2015, Language.TELUGU));
		movies.add(new Movie("Carry on Jatta 2", 2018, Language.PUNJABI));
		movies.add(new Movie("Shadaa", 2019, Language.PUNJABI));
		movies.add(new Movie("Chaar Sahibzaade", 2014, Language.PUNJABI));
		movies.add(new Movie("Avatar", 2009, Language.ENGLISH));
		movies.add(new Movie("Avengers: Endgame", 2019, Language.ENGLISH));
		movies.add(new Movie("Titanic", 1997, Language.ENGLISH));
	}
	
	public static List<Movie> getMovies() {
		if(movies!=null && !movies.isEmpty())
			return movies;
		else {
			loadMovies();
			return movies;
		}
	}
	
	public static void clear() {
		if(movies!=null && !movies.isEmpty())
			movies.clear();
	}

}
