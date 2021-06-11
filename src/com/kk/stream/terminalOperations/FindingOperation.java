package com.kk.stream.terminalOperations;

import java.util.Optional;

import com.kk.stream.Movie;
import com.kk.stream.MovieApi;

/**
 * Finding is a terminal operation of Stream to return single (i.e. any or first) object. Typically it is used with filter() operation and can be used without it.
 * Its all terminal operations returns {@code Optional} object and are Short-circuit operations.<br>
 * Following are Finding operations:
 * <ul>
 * <li>findAny() :: In case of sequential stream, most probably it will return first element. But in case of parallel stream, it could return any.
 * <li>findFirst() :: It will return always first element. Of course, it has to do some extra operations internally in case of parallel stream to return the first element.
 * </ul>
 */
public class FindingOperation {

	public static void main(String[] args) {
		findAny();
		findFirst();
	}
	
	private static void findAny() {
		Optional<Movie> anyBaahubaliMovie = MovieApi.getMovies()
			.stream()
			.filter(movie -> movie.getName().contains("Baahubali"))
			.findAny();
		
		if( anyBaahubaliMovie.isPresent() )
			System.out.println("Any = " + anyBaahubaliMovie.get()); //Any = Baahubali 2
		else
			System.out.println("Nothing found!");
	}
	
	private static void findFirst() {
		Movie latestBaahubaliMovie = MovieApi.getMovies()
				.stream()
				.filter(movie -> movie.getName().contains("Baahubali 5"))
				.findFirst()
				.orElseGet(MovieApi::getAnyMovie); //If element is null then this orElseGet() will be executed. [Its lazy execution]
		
		System.out.println("First = " + latestBaahubaliMovie); //First = Dangal
	}
	
}
