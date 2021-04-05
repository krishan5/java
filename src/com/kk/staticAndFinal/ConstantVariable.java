package com.kk.staticAndFinal;

/**
 * Rules to being a Constant variable :
 * 1. Variable should be final.
 * 2. Initialization should be done in declaration statement. Neither in constructor nor in initializer block.
 * 3. Initialized with compile-time constant expression. Constant expression is something whose value is known at compile time.
 */
public class ConstantVariable {
	
	/*
	 * Constant variable as it is final and initialized with compile-time constant expression.
	 * Math class won't be load on runtime as Math.PI is itself a constant variable. So its value directly copied here.
	 */
	final double PI = Math.PI; 
	
	public static void main(String[] args) {
		ConstantVariableInSwitchBlock.switchExample();
		
		/*Here both x and z are constant variable.*/ 
		final int x = 5;
		final int z = 55 + x; //55 + x >> is compile-time constant expression as x is final and initialized in declaration statement.
		
		/*Here both y and w are not constant variable.*/
		int y = 5;
		final int w = 90 + y; //90 + w >> is not compile-time constant expression as y is not final so its value is not known at compile time here.
	}

	static class ConstantVariableInSwitchBlock {
		final String s1; //Not a constant variable as it is final but initialized in initializer block.
		final String s2; //Not a constant variable as it is final but initialized in constructor.
		static final int DAY2 = 2; //Constant variable as it is final and initialized here i.e. in declaration statement.
		
		{
			s1 = "final variable initalized in instance initializer block.";
		}
		
		ConstantVariableInSwitchBlock() {
			s2 = "final variable initalized in constructor.";
		}
		
		/*
		 * Here how constant variable can be used in switch statement.
		 */
		private static void switchExample() {
			final int day3 = 3;
			int day = 3;
			String weekString = "";
			switch(day) {
			case 1 :
				weekString = "Monday";
				break;
			case DAY2 : //We can use constant variables like this in switch case.
				weekString = "Tuesday";
				break;
			case day3 : //We can use constant variables like this in switch case.
				weekString = "Wednesday";
				break;
			case 4 :
				weekString = "Thursday";
				break;
			case 5 :
				weekString = "Friday";
				break;
			case 6 :
				weekString = "Saturday";
				break;
			case 7 :
				weekString = "Sunday";
				break;
			default :
				weekString = "default";
			}
			System.out.println(weekString);
		}
	}

}
