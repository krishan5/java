package com.kk.stream.intermediateOperations;

import com.kk.stream.MovieApi;

/**
 * Slicing is an intermediate operation of Stream to narrow down elements to be processed in further stream's operations.
 * Those elements which filtered out will not be proceed in further stream's operations.
 * Intermediate operations returns Stream objects.<br>
 * Following are Slicing operations:
 * <ul>
 * <li>limit(long)</li>
 * <li>skip(long)</li>
 * </ul>
 */
public class SlicingOperation {
	
	public static void main(String args[]) {
		limit();
		skip();
	}
	
	private static void limit() {
		System.out.println("**************************LIMIT OPERATION***************************");
		MovieApi.getMovies()
			.stream()
			.limit(5) //It will allow first 5 elements to processed further, rest will not be processed. It is also one of the Short-circuit operations.
			.forEach(movie -> System.out.println(movie));
	}
	
	private static void skip() {
		System.out.println("**************************SKIP OPERATION***************************");
		MovieApi.getMovies()
		.stream()
		.skip(5) //It will not allow first 5 elements to processed further, rest will be processed.
		.forEach(movie -> System.out.println(movie));
	}
	
}
