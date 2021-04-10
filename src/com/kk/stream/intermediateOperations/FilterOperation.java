package com.kk.stream.intermediateOperations;

import java.util.List;
import java.util.stream.Stream;

import com.kk.stream.Movie;
import com.kk.stream.MovieApi;

/**
 * Filter is an intermediate operation of Stream to filter out which fails the condition.
 * Those elements which filtered out will not be proceed in further stream's operations.
 * Intermediate operations returns Stream objects.
 * Terminal operations returns result in way you want.
 */
public class FilterOperation {

	public static void main(String[] args) {
		
		filter();
		distinct();
	}

	private static void filter() {
		System.out.println("**************************FILTER OPERATION***************************");
		
		List<String> bollywoodStarsList = List.of("Amitabh Bachchan","Abhishek Bachchan","Aishwarya Rai Bachchan",
				"Salman Khan","Shahrukh Khan","Aamir Khan","Saif Ali Khan");
		
		Stream<String> bollywoodStarsStream = bollywoodStarsList.stream();
		
		Stream<String> bachchanStarsStream 
		 	= bollywoodStarsStream
				 .filter(bollywoodStar -> bollywoodStar.contains("Bachchan")); //filter is an intermediate operation.
		
		System.out.println("Bachchan stars :");
		bachchanStarsStream.forEach(name -> System.out.println(name)); //foreach is a terminal operation, stream gets closed after its execution.
		/*
		Stream<String> khanStarsStream 
	 	= bollywoodStarsStream
			 .filter(bollywoodStar -> bollywoodStar.contains("Khan")); //It will throw IllegalStateException as stream is already closed above.
		
		System.out.println("Khan stars :");
		khanStarsStream.forEach(name -> System.out.println(name));
		*/
	}
	
	private static void distinct() {
		System.out.println("**************************DISTINCT OPERATION***************************");
		
		List<Movie> movies = MovieApi.getMovies();
		movies.stream()
			.distinct() //Distinct by movie's language. Just provide the implementation to equals() method of Movie class. Distinct uses it for comparison.
			.forEach(movie -> System.out.println(movie));
	}

}
