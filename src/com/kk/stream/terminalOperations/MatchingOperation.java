package com.kk.stream.terminalOperations;

import com.kk.stream.Language;
import com.kk.stream.MovieApi;

/**
 * Matching is a terminal operation of Stream which returns boolean based on condition.
 * It takes Predicate as like filter operation. But difference is that filter is an intermediate operation and match is a terminal operation.
 * Its all terminal operations returns boolean and are Short-circuit operations.<br>
 * Following are Matching operations:
 * <ul>
 * <li>anyMatch(Predicate) :: return {@code true} if any elements match the condition. If any element found that matches the condition, then it will not processed rest of the elements and return result.</li>
 * <li>allMatch(Predicate) :: return {@code true} if all elements match the condition. If any element found that not matches the condition, then it will not processed rest of the elements and return result.</li>
 * <li>noneMatch(Predicate) :: return {@code true} if none of elements match the condition. If any element found that matches the condition, then it will not processed rest of the elements and return result.</li>
 * </ul>
 */
public class MatchingOperation {

	public static void main(String[] args) {
		anyMatch();
		allMatch();
		noneMatch();
	}

	private static void anyMatch() {
		boolean anyMatch = MovieApi.getMovies()
			.stream()
			.anyMatch(movie -> movie.getReleaseDate() > 2000); 
		System.out.println("Is any movie released after year 2000 ? :: " + anyMatch); //true
	}

	private static void allMatch() {
		boolean allMatch = MovieApi.getMovies()
				.stream()
				.allMatch(movie -> movie.getLanguage() == Language.TELUGU);
			System.out.println("Are all movie of TELUGU language ? :: " + allMatch); //false
	}

	private static void noneMatch() {
		boolean noneMatch = MovieApi.getMovies()
				.stream()
				.noneMatch(movie -> movie.getLanguage() == Language.GERMAN);
			System.out.println("Are all movie not of GERMAN language ? :: " + noneMatch); //true
	}

}
