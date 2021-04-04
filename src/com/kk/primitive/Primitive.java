package com.kk.primitive;

public class Primitive {

	public static void main(String[] args) {
		
		System.out.println("*******************INTEGER*******************");
		
		int decimal = 10;
		int hexaDecimal = 0x10;
		int binary = 0b10;
		int octal = 010;
		
		System.out.println("10 = " + decimal); //10 
		System.out.println("0x10 = " + hexaDecimal); //16
		System.out.println("0b10 = " + binary); //2
		System.out.println("010 = " + octal); //8
		
		int readableInt = 1_23_456;
		System.out.println("1_23_456 = " + readableInt); //123456
		
		int charValueToInt = 'A';
		System.out.println("A = " + charValueToInt); //65
		
		System.out.println("*******************CHARACTER*******************");
		
		char charA = 'A';
		System.out.println("A = " + charA); //A
		
		char intValueToChar = 65; // 65 -> A
		System.out.println("65 = " + intValueToChar); //A
		
		char unicode = '\u0041'; // 4 * 16^1 + 1 * 16^0 -> 65 -> A
		System.out.println("\\u0041 = " + unicode); //A
		
		char unicode2 = 0x41;
		System.out.println("0x41 = " + unicode2); //A
		
		char charBinary = 0b01000001;
		System.out.println("0b01000001 = " + charBinary); //A
		
		char charDefaultValue = '\u0000';
		System.out.println("\\u0000 = " + charDefaultValue); // \u0000 is default value of char which is NULL
	}

}
