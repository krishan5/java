package com.kk.stream.terminalOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kk.stream.Language;
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
		 * accumulator is a function for combining two values and returns one/any of its type;
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
		 * because reduce() is not designed to perform mutable reduction.
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
		 * Here is the solution of above problem in parallel stream :
		 * In this way, you can use any stream (SEQUENTIAL or PARALLEL), result will always be correct.
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
		 * Making reduce() working correct with one modification :
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
	 * {@code collect()} method is used to reduces stream into a single container or single value.
	 * Example: List/Set/Map, StringBuilder, Summary Object, count, max, sum, average.
	 * What is Summary Object : If stream is a stream of numbers then the Summary Object can have list of summary values
	 * (like sum, avg or max), all of them encapsulated in container called Summary Object.
	 * <br>
	 * One of its method take {@link Collectors} as a helper class which provides predefined reductions.
	 * {@link java.util.stream.Collectors} returns the {@link java.util.stream.Collector} object to {@code collect()} method.
	 * It internally provides supplier, accumulator and combiner to {@code collect()} method.
	 * <br><br>{@code Collector<T,A,R>} :
	 * where
	 * <ul> 
	 * <li>R is a container which is being returned</li>
	 * <li>T is type of input stream element</li>
	 * <li>A is accumulator type</li>
	 * </ul>
	 * Following are {@link java.util.stream.Collector} methods :
	 * <ul>
	 * <li>supplier() Supplier<A> : creates new container</li>
	 * <li>accumulator() BiConsumer<A,T>: accumulates into container</li>
	 * <li>combiner() BinaryOperator<A> : combines two result containers</li>
	 * <li>finisher() Function<A,R> : optional : transform container object to another type</li>
	 * </ul>
	 * Following are predefined Collectors :
	 * <ul>
	 * <li>Collection : toList(), toSet(), toCollection(Supplier)</li>
	 * <li>Grouping and Multi-level grouping : toMap(), groupingBy(), groupingBy(Function classifier, Collectors.mapping()), partitioningBy()</li>
	 * <li>Reducing & Summarizing : minBy(), maxBy(), summingInt(), averagingInt(), summarizingInt(), joining()</li>
	 * </ul>
	 */
	private static void collectOperations() {
		System.out.println();System.out.println();
		System.out.println("**************************collect(Collector)***************************");
		System.out.println();
		
		collectorsForCollection();
		collectorsForGroupingAndMultiLevelGrouping();
		collectorsForReducingAndSummarizing();
	}

	private static void collectorsForCollection() {
		List<Movie> movies = MovieApi.getMovies();
		
		System.out.println();
		System.out.println("**************************collect(Collectors.toList())***************************");
		List<String> movieNameList = movies.stream()
			.map(movie -> movie.getName())
			.collect(Collectors.toList());
		movieNameList.forEach(movieName -> System.out.println(movieName));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.toSet())***************************");
		Set<Language> movieLanguageSet = movies.stream()
			.map(movie -> movie.getLanguage())
			.collect(Collectors.toSet()); //It is creating object of HashSet. //It internally uses hashCode() and equals() method
		movieLanguageSet.forEach(movieLanguage -> System.out.println(movieLanguage));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.toCollection(Supplier))***************************");
		TreeSet<Movie> movieTreeSet = movies.stream()
			.collect(Collectors.toCollection(() -> new TreeSet<>())); //If want to create object initialization of your choice then use toCollection(). //TreeSet internally uses compareTo() method
		movieTreeSet.forEach(movieName -> System.out.println(movieName));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.toMap(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction))***************************");
		/**
		 * Use toMap() you want value as single value instead of list.
		 */
		Map<Integer, Movie> keyReleaseDate_valueMovie_Map = movies.stream()
			.collect(Collectors.toMap(Movie::getReleaseDate, //key
					Function.identity(), //value
					(m1,m2) -> m1.getReleaseDate() <= m2.getReleaseDate() ? m1 : m2)); //merger function. In case it face collision in keys, then this merger function helps in deciding which one value to take up.
		keyReleaseDate_valueMovie_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.toMap(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction))***************************");
		/**
		 * Here we are forcing toMap() to create value as list. Its alternative method is groupingBy() explained just next of it. 
		 */
		Map<Integer, List<Movie>> keyReleaseDate_valueMovieList_Map = movies.stream()
			.collect(Collectors.toMap(Movie::getReleaseDate, //key
					movie -> Collections.singletonList(movie), //value as List
					(m1,m2) -> {
						List<Movie> l = new ArrayList<>(m1);
						l.addAll(m2);
						return l;
					})); //merger function. In case it face collision in keys, then this merger function helps in put another value into list. No value will be skipped as happened above.
		keyReleaseDate_valueMovieList_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
	}
	
	

	private static void collectorsForGroupingAndMultiLevelGrouping() {
		List<Movie> movies = MovieApi.getMovies();
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier))***************************");
		/**
		 * groupingBy() by design create key and value itself where map's value will be of List.
		 */
		Map<Integer, List<Movie>> keyReleaseDate_valueMovieList_Map = movies.stream()
			.collect(Collectors.groupingBy(Movie::getReleaseDate)); //It act same as above one in more clear way. By default it make List of map's value. But for developers, its hard to debug its internal working.
		keyReleaseDate_valueMovieList_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Collector downstream))***************************");
		/**
		 * groupingBy() by design create key and value itself where map's value will be of Set.
		 */
		Map<Integer, Set<Movie>> keyReleaseDate_valueMovieSet_Map = movies.stream()
			.collect(Collectors.groupingBy(Movie::getReleaseDate, Collectors.toSet())); //In 2nd parameter of Collectors, Here we are saying it to make Set of map's value. Above it was List by default.
		keyReleaseDate_valueMovieSet_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Supplier mapFactory, Collector downstream))***************************");
		Map<Integer, Set<Movie>> keyReleaseDate_valueMovieSet_TreeMap = movies.stream()
			.collect(Collectors.groupingBy(Movie::getReleaseDate, TreeMap::new, Collectors.toSet())); //Now, in 2nd parameter of Collectors, we are saying it to initialize Map of TreeMap. Above it was initializing map of HashMap by default. Rest is same as above.
		keyReleaseDate_valueMovieSet_TreeMap.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Collectors.mapping()))***************************");
		Map<Integer, List<String>> keyReleaseDate_valueMovieNameList_Map = movies.stream()
			.collect(Collectors.groupingBy(Movie::getReleaseDate, Collectors.mapping(Movie::getName, Collectors.toList())));
		keyReleaseDate_valueMovieNameList_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Collectors.groupingBy()))***************************");
		Map<Integer, Map<Language, List<Movie>>> multiLevelMap = movies.stream()
				.collect(Collectors.groupingBy(Movie::getReleaseDate, Collectors.groupingBy(Movie::getLanguage, Collectors.toList())));
		multiLevelMap.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.partitioningBy(Predicate))***************************");
		/**
		 * partitioningBy(Predicate) divide the whole list into 2 list on the basis of given condition (predicate) and key will be of boolean type.
		 * true : Hindi, Punjabi movies list
		 * false : Other all language type of movie list
		 */
		Map<Boolean, List<Movie>> partitioningMap = movies.stream()
			.collect(Collectors.partitioningBy(m -> m.getLanguage() == Language.HINDI || m.getLanguage() == Language.PUNJABI));
		partitioningMap.forEach((k,v) -> System.out.println(k + " -> " + v));
	}

	private static void collectorsForReducingAndSummarizing() {
		List<Movie> movies = MovieApi.getMovies();
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Collectors.counting()))***************************");
		Map<Language, Long> keyMovieLanguage_valueCount_Map = movies.stream()
			.collect(Collectors.groupingBy(Movie::getLanguage, Collectors.counting()));
		keyMovieLanguage_valueCount_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.counting())***************************");
		long count = movies.stream().collect(Collectors.counting()); //Self-explanatory :P
		System.out.println("Total movies = " + count); //Total movies = 10
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.groupingBy(Function classifier, Collectors.minBy()))***************************");
		Map<Language, Optional<Movie>> keyLanguage_valueMovieHasMininumMinutes_Map = movies.stream()
			.collect(Collectors.groupingBy(Movie::getLanguage, Collectors.minBy(Comparator.comparingInt(Movie::getMinutes))));
		keyLanguage_valueMovieHasMininumMinutes_Map.forEach((k,v) -> System.out.println(k + " -> " + v));
		
		
		
		System.out.println();
		System.out.println("**************************collect(Collectors.summarizingInt())***************************");
		/**
		 * Summarizing objects provide sum, min, max, count, average of given target. Example here is movie minutes.
		 */
		IntSummaryStatistics intSummaryStatistics = movies.stream()
			.collect(Collectors.summarizingInt(Movie::getMinutes));
		System.out.println("Movie minutes summary = " + intSummaryStatistics);
	}

}
