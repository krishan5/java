package com.kk.stream.intermediateOperations;

import java.util.List;

import com.kk.stream.Language;
import com.kk.stream.Movie;
import com.kk.stream.MovieApi;

/**
 * Filter is an intermediate operation of Stream to filter out elements which fails the condition.
 * Those elements which filtered out will not be proceed in further stream's operations.
 * Intermediate operations returns Stream objects.<br>
 * Following are Filter operations:
 * <ul>
 * <li>filter(Predicate)</li>
 * <li>distinct()</li>
 * </ul>
 */
public class FilterOperation {

	public static void main(String[] args) {
		filter();
		distinct();
	}

	private static void filter() {
		System.out.println("**************************FILTER OPERATION***************************");
		List<Movie> movies = MovieApi.getMovies();
		movies.stream()
			.filter(movie -> movie.getLanguage() == Language.HINDI) //Filter out HINDI movies
			.forEach(hindiMovie -> System.out.println(hindiMovie));
	}
	
	private static void distinct() {
		System.out.println("**************************DISTINCT OPERATION***************************");
		List<Movie> movies = MovieApi.getMovies();
		movies.stream()
			.distinct() //Distinct by movie's language. Just provide the implementation to equals() method of Movie class. Distinct uses it for comparison.
			.forEach(movie -> System.out.println(movie));
	}

}
