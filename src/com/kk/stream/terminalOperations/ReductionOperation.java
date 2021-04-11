package com.kk.stream.terminalOperations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kk.stream.Movie;
import com.kk.stream.MovieApi;

/**
 * Reduction is a terminal operation of Stream.
 * Following are reduction operations:
 * <ul>
 * <li>reduce() :: perform immutable reduction</li>
 * <li>collect() :: perform mutable reduction</li>
 * </ul>
 * <b>Note: Developers misused reduce methods by using mutable reduction.</b>
 */
public class ReductionOperation {

	public static void main(String[] args) {
		reduceOperations();
		mutableReduction();
		collectOperations();
	}
	
	/**
	 * reduce() method is used to reduces the stream into a single value. Example: sum, max, min, count etc
	 * Reduction operation applies repeatedly on the stream of elements.
	 */
	private static void reduceOperations() {
		System.out.println("**************************reduce()***************************");
		/**
		 * reduce() method signature:
		 * Optional<T> reduce(BinaryOperator<T> accumulator)
		 * 
		 * where accumulator is a function for combining two values.
		 */
		List<Movie> movies = MovieApi.getMovies();
		movies.stream()
			.reduce((m1, m2) -> m1.getReleaseDate() < m2.getReleaseDate() ? m1 : m2) //It will act as min
			.ifPresent(m -> System.out.println("Oldest movie : " + m));
		
		/**
		 * reduce() method signature:
		 * OptionalInt reduce(IntBinaryOperator op)
		 * 
		 * where op is a function for combining two values.
		 */
		Arrays.stream(new int[] {4,6,2,9})
			.reduce(Integer::sum)
			.ifPresent(sum -> System.out.println("Sum of int : " + sum)); //Sum of int : 21
		
		/**
		 * reduce() method signature:
		 * T reduce(T identity, BinaryOperator<T> accumulator)
		 * 
		 * where identity is an initial value >> "" as identity -> ""+"kk" or "kk"+"" = "kk"
		 * accumulator is a function for combining two values.
		 */
		String concatenation = Arrays.stream(new String[]{"A","B","C","D","E"})
			.reduce("", (s1, s2) -> s1 + s2);
		System.out.println("Concatenation by + : " + concatenation); //Concatenation by + : ABCDE
	}
	
	private static void mutableReduction() {
		System.out.println("**************************mutableReduction()***************************");
		/**
		 * BY SEQUENTIAL STREAM :
		 * 
		 * reduce() method signature:
		 * <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
		 * 
		 * where identity is an initial value;
		 * accumulator is a function for combining two values and one of its type;
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
		 * Anyhow combiner is not going to be used by sequential stream, still you have to provide its implementation.
		 * You can't set it NULL otherwise you will face NullPointerException.
		 * (API is designed in a way that Java designers don't want to differentiate b/w the sequential and parallel stream.
		 * i.e., they haven't build one reduce() for sequential stream and another overloaded reduce() for parallel stream
		 * because if we want to switch b/w sequential to parallel or vice-versa, we will also have to change the reduce()
		 * in our code which is not a good design practice)
		 * 
		 * NOTE : Due to variations in types, use this type of reduce() operation.
		 * Here source is of String array but using StringBuilder to perform concatenation.
		 */
		StringBuilder concatenation = Arrays.stream(new String[]{"A","B","C","D","E"})
			.reduce(new StringBuilder(), 
					(sb,s) -> sb.append(s), 
					(sb1, sb2) -> sb1.append(sb2));
		System.out.println("Concatenation by StringBuilder using reduce() : " + concatenation);
		//OUTPUT >>
		//Concatenation by StringBuilder using reduce() : ABCDE
		
		
		
		/**
		 * BY PARALLEL STREAM :
		 * 
		 * Always do testing before proceeding with parallel stream.
		 * Here identity (i.e., StringBuilder) is not ThreadSafe and combiner function is itself a problem.
		 * Here reduction method is doing mutable reduction:
		 * In parallelism, there will be multiple segments (according to cores of machine) 
		 * and this StringBuilder (as identity) is a shared container between each segments (where elements divided into groups/segment)
		 * so coordination between threads need to be done but fails because StringBuilder is not ThreadSafe.
		 * There will be race condition and lead to incorrect result.
		 * And combiner function has to combine the containers, but in this case we have a single container (shared StringBuilder)
		 * and it will combine the same container with itself which lead to incorrect result.
		 * 
		 * Here, we are doing mutable reduction with reduce() which is not recommended
		 * because reduce() is designed to perform mutable reduction.
		 * Use collect() to perform mutable reduction which is designed for it.
		 */
		StringBuilder concatenation2 = Arrays.stream(new String[]{"A","B","C","D","E"})
			.parallel()
			.reduce(new StringBuilder(), 
					(sb,s) -> sb.append(s), 
					(sb1, sb2) -> sb1.append(sb2));
		System.out.println("Parallel concatenation by StringBuilder using reduce() : " + concatenation2);
		//OUTPUT >>
		//Parallel concatenation by StringBuilder using reduce() : CBEDCBEDCBEDCBEDACBEDCBEDCBEDCBEDACBEDCBEDCBEDCBEDACBEDCBEDCBEDCBEDA
		
		
		
		/**
		 * You can use any stream (SEQUENTIAL or PARALLEL), result will always be correct.
		 * 
		 * collect() method signature:
		 * <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
		 * where supplier is a mutable container;
		 * accumulator is a consumer to combine the values but not returning anything;
		 * combiner is also a consumer here to combine the containers.
		 * Values being mutated in both accumulator and combiner operations.
		 * 
		 * In each segment will have its own container (supplier)
		 * and rest of the thing is just same as explained above example.
		 * 
		 * Here, we are doing mutable reduction with collect() which is recommended
		 * because collect() is designed to perform mutable reduction.
		 */
		StringBuilder concatenation3 = Arrays.stream(new String[]{"A","B","C","D","E"})
			.parallel()
			.collect(() -> new StringBuilder(), 
					(sb,s) -> sb.append(s), 
					(sb1, sb2) -> sb1.append(sb2));
		System.out.println("Parallel concatenation by StringBuilder using collect() : " + concatenation3);
		//OUTPUT >>
		//Parallel concatenation by StringBuilder using collect() : ABCDE
		
		
		
		/**
		 * Here is the easy approach to achieve the similar result through Collectors in collect() method.
		 */
		String concatenation4 = Arrays.stream(new String[]{"A","B","C","D","E"})
			.parallel()
			.collect(Collectors.joining());
		System.out.println("Parallel concatenation by StringBuilder using collect(Collectors) : " + concatenation4);
		//OUTPUT >>
		//Parallel concatenation by StringBuilder using collect(Collectors) : ABCDE
		
		
		
		/**
		 * Making reduce() working correct with one modification:
		 * 
		 * In accumulator, we created a new StringBuilder object and performing two append operation.
		 * By doing this, we are not mutating same StringBuilder object again and again
		 * instead we are now creating new StringBuilder object in each accumulating operation.
		 * 
		 * Although it gives the correct result but it is still inefficient.
		 */
		StringBuilder concatenation5 = Arrays.stream(new String[]{"A","B","C","D","E"})
			.parallel()
			.reduce(new StringBuilder(), 
					(sb,s) -> new StringBuilder().append(sb).append(s), 
					(sb1, sb2) -> sb1.append(sb2));
		System.out.println("Parallel concatenation by StringBuilder using inefficient reduce() : " + concatenation5);
		//OUTPUT >>
		//Parallel concatenation by StringBuilder using inefficient reduce() : ABCDE
	}
	
	/**
	 * collect() method is used to reduces stream into a single container. Example: List/Set/Map, StringBuilder, Summary Object.
	 * What is Summary Object : If stream is a stream of numbers then the Summary Object can have list of summary values
	 * (like sum, avg or max), all of them encapsulated in container called Summary Object.
	 */
	private static void collectOperations() {
		System.out.println("**************************collect()***************************");
	}

}
