package com.kk.string;

public class StringInterning {

	public static void main(String[] args) {
		
		String s0 = new String("krishan"); //s0 is referring new object which is out of string pool and that object is referring to "krishan" literal stored in string pool.
		String s1 = "krishan"; //s1 is referring to object created in string pool which is shared by all pointing same value.
		System.out.println("s0 == s1 >> " + (s0==s1)); //false
		
		/*Here s0 explicitly intern which means now it is directly referring to "krishan" literal stored in string pool
		and its head object become abandoned which will be garbage collected.*/
		System.out.println("s0.intern() == s1 >> " + (s0.intern()==s1)); //true
		
		String s2 = "krish" + "an"; //Here values are known at compile time. //Not recommended concatenation, use StringBuilder instead.
		System.out.println("s1 == s2 >> " + (s1==s2)); //true
		
		String s3 = "krish";
		String s4 = "an";
		String s5 = s3 + s4; //Here values are not known at compile time. It will be evaluated at runtime.
		System.out.println("s5 == \"krishan\" >> " + (s5=="krishan")); //false
		
		String s6 = "krish" + s4; //Same as above.
		System.out.println("s6 == \"krishan\" >> " + (s6=="krishan")); //false
		
		String s7 = s3 + s4;
		s7 = s7.intern(); //Here values explicitly interned in string pool. Not recommended explicitly invoking intern().
		System.out.println("s7 == \"krishan\" >> " + (s7=="krishan")); //true
		
		final String s8 = s3 + s4; //Here again values of s3 and s4 are not known at compile time.
		System.out.println("s8 == \"krishan\" >> " + (s8=="krishan")); //false
		
		final String s9 = "krish";
		final String s10 = "an";
		String s11 = s9 + s10; //Here s9 and s10 are final variables which means there values directly replaced here, which become known at compile time.
		System.out.println("s11 == \"krishan\" >> " + (s11=="krishan")); //true
	}

}
