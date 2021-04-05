package com.kk.staticAndFinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InitializerBlock {
	
	String s;
	HashMap<Integer, String> map;
	
	static String ss;
	static List<String> list;
	
	//Instance initializer block copied in all constructors and executed before constructor code. 
	{
		s = "Value initialized in initializer block for instance variable.";
		System.out.println("Instance initializer block 1");
	}
	
	//In case multiple instance initializer block are used, they will be copied in order they written.
	{
		map = new HashMap<>();
		map.put(1, "One");
		map.put(2, "Two");
		map.put(3, "Three");
		System.out.println("Instance initializer block 2");
	}
	
	//Use static initializer block in case we need to initialize static class fields.
	static {
		ss = "Value initialized in static initializer block for static variable.";
		System.out.println("Static initializer block 1");
	}
	
	//In case multiple static initializer block are used, they will be executed in order they written.
	static {
		list = new ArrayList<>();
		list.add("One");
		list.add("Two");
		list.add("Three");
		System.out.println("Static initializer block 2");
	}
	
	InitializerBlock() {
		System.out.println("InitializerBlock() constructor invoked.");
	}
	
	InitializerBlock(int i) {
		System.out.println("InitializerBlock(int) constructor invoked.");
	}

	public static void main(String[] args) {
		InitializerBlock ib1 = new InitializerBlock();
		InitializerBlock ib2 = new InitializerBlock(100);
		
		/**
		 * OUTPUT would be :
		 * 
		 * Static initializer block 1
		 * Static initializer block 2
		 * Instance initializer block 1
		 * Instance initializer block 2
		 * InitializerBlock() constructor invoked.
		 * Instance initializer block 1
		 * Instance initializer block 2
		 * InitializerBlock(int) constructor invoked.
		 */
	}
	
}
