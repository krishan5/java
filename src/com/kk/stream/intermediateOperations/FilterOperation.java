package com.kk.stream.intermediateOperations;

import java.util.List;
import java.util.stream.Stream;

public class FilterOperation {

	public static void main(String[] args) {
		
		List<String> bollywoodStarsList = List.of("Amitabh Bachchan","Abhishek Bachchan","Aishwarya Rai Bachchan",
				"Salman Khan","Shahrukh Khan","Aamir Khan","Saif Ali Khan");
		
		Stream<String> bollywoodStarsStream = bollywoodStarsList.stream();
			
		Stream<String> bachchanStarsStream 
		 	= bollywoodStarsStream
				 .filter(bollywoodStar -> bollywoodStar.contains("Bachchan"));
		
		System.out.println("Bachchan stars :");
		bachchanStarsStream.forEach(name -> System.out.println(name));
		
		Stream<String> khanStarsStream 
	 	= bollywoodStarsStream
			 .filter(bollywoodStar -> bollywoodStar.contains("Khan")); //It will throw IllegalStateException as stream is already closed above.
		
		System.out.println("Khan stars :");
		khanStarsStream.forEach(name -> System.out.println(name));
	}

}
