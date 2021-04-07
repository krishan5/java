package com.kk.primitive;

import java.time.Duration;
import java.time.Instant;

public class AutoBoxing {
	
	static Integer boxedIntegerNotInitialized;

	public static void main(String[] args) {
		
		long sum = 0;
		byPrimitive(sum);
		byBoxedPrimitive(sum);
		
		weirdBehavior(); //It will throw NullPointerException at runtime because of boxedIntegerNotInitialized (name is self explanatory :P)
	}

	private static void byPrimitive(long sum) {
		Instant start = Instant.now();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum = sum + i;
		}
		Instant end = Instant.now();
		System.out.println("Time take by primitive = " + Duration.between(start, end).getSeconds()); //1
	}
	
	private static void byBoxedPrimitive(Long sum) {
		Instant start = Instant.now();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			/**
			 * Here three steps are going to be done due to Boxed primitive :
			 * 1. variable sum going to be auto-unboxed.
			 * 2. addition will be performed.
			 * 3. variable sum will auto-boxed again because its value will be assigned to boxed primitive variable sum. 
			 */
			sum = sum + i;
		}
		Instant end = Instant.now();
		System.out.println("Time take by boxed primitive = " + Duration.between(start, end).getSeconds()); //8
	}
	
	private static void weirdBehavior() {
		if(boxedIntegerNotInitialized == 0)
			System.out.println("reached...");
	}

}
