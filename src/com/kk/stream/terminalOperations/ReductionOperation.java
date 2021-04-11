package com.kk.stream.terminalOperations;

import java.util.Arrays;
import java.util.List;

import com.kk.stream.Movie;
import com.kk.stream.MovieApi;

/**
 * Reduction is a terminal operation of Stream.
 * Following are reduction operations:
 * <ul>
 * <li>reduce()</li>
 * <li>collect()</li>
 * </ul>
 */
public class ReductionOperation {

	public static void main(String[] args) {
		reduceOperations();
		collectOperations();
	}

	/**
	 * reduce() method is used to reduce the stream into single value. Example: sum, max, min, count etc
	 * Reduction operation applies repeatedly on the stream of elements.
	 */
	private static void reduceOperations() {
		
		/**
		 * Optional<T> reduce(BinaryOperator<T> accumulator)
		 * where accumulator is a function for combining two values.
		 */
		List<Movie> movies = MovieApi.getMovies();
		movies.stream()
			.reduce((m1, m2) -> m1.getReleaseDate() < m2.getReleaseDate() ? m1 : m2) //It will act as min
			.ifPresent(m -> System.out.println("Oldest movie : " + m));
		
		/**
		 * OptionalInt reduce(IntBinaryOperator op)
		 * where op is a function for combining two values.
		 */
		Arrays.stream(new int[] {4,6,2,9})
			.reduce(Integer::sum)
			.ifPresent(sum -> System.out.println("Sum of int : " + sum)); //Sum of int : 21
		
		/**
		 * T reduce(T identity, BinaryOperator<T> accumulator)
		 * where identity is an initial value >> "" as identity -> ""+"kk" or "kk"+"" = "kk"
		 * accumulator is a function for combining two values.
		 */
		String concatenation = Arrays.stream(new String[]{"A","B","C","D","E"})
			.reduce("", (s1, s2) -> s1 + s2);
		System.out.println("Concatenation by + : " + concatenation); //Concatenation by + : ABCDE
		
		/**
		 * <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
		 * where identity is an initial value and 
		 * accumulator is a function for combining two values and
		 * combiner is a function for combining results of accumulator.
		 * 
		 * Here T will be String and U will be StringBuilder to understand operations:
		 * sb.append(s) >> sb = StringBuilder & s = String
		 * sb1.append(sb2) >> sb1 & sb2 both are StringBuilder
		 * 
		 * The result of accumulator function will be of StringBuilder type 
		 * that's why here combiner is of StringBuilder type
		 * to combine results of accumulators.
		 * 
		 * Due to variations in types, use this type of reduce() operation.
		 * Here source is of String array but using StringBuilder to perform concatenation.
		 */
		StringBuilder concatenation2 = Arrays.stream(new String[]{"A","B","C","D","E"})
			.reduce(new StringBuilder(), (sb,s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2));
		System.out.println("Concatenation by StringBuilder : " + concatenation2); //Concatenation by StringBuilder : ABCDE
	}

	private static void collectOperations() {
		
	}

}
