package com.kk.string;

import java.time.Duration;
import java.time.Instant;

public class StringConcatenation {
	public static void main(String args[]) {
		//int timeout = 10000; //Doesn't make major performance impact.
		int timeout = 100000; //Does make major performance impact.
		byStringBuilder(timeout);
		System.out.println();
		byConcatenation(timeout);
	}

	/**
	 * With each concatenation, following steps being performed :
	 * 1. Contents of both strings are first copied.
	 * 2. Then New StringBuilder object is created and appended with both strings.
	 * 3. Return string via toString(). 
	 */
	private static void byConcatenation(int timeout) {
		System.out.println("byConcatenation() is invoked");
		String s = "";
		Instant start = Instant.now();
		System.out.println("for loop initiated.....");
		for(int i = 0; i < timeout; i++) {
			s += "a";
			s += "b";
			s += "c";
			s += " ";
		}
		System.out.println("for loop finished.");
		Instant end = Instant.now();
		Duration total = Duration.between(start, end);
		//System.out.println(s);
		System.out.println("Time taken by concatenation : " + total.getSeconds());
	}
	
	private static void byStringBuilder(int timeout) {
		System.out.println("byStringBuilder() is invoked");
		StringBuilder s = new StringBuilder(" ");
		Instant start = Instant.now();
		System.out.println("for loop initiated.....");
		for(int i = 0; i < timeout; i++) {
			s.append("a");
			s.append("b");
			s.append("c");
			s.append(" ");
		}
		System.out.println("for loop finished.");
		Instant end = Instant.now();
		Duration total = Duration.between(start, end);
		//System.out.println(s.toString());
		System.out.println("Time taken by StringBuilder : " + total.getSeconds());
	}
}
