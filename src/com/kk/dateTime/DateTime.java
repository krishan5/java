package com.kk.dateTime;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Humans deal with Year, month, day, hours etc to understand time. For computers this kind of interpretation is not easy to represent.
 * For computer, it deals with Epoch. Epoch is single large number representing a point on continuous timeline. 
 * Epoch generally means 'start of something'. [Example: 12:30AM, July 2nd, 2017] Found on January 1, 1970 UTC (Midnight).
 * 
 * UTC - Coordinated Universal Time : used by servers, air traffic control, international space craft.
 * Typical UTC day ~ 864000 seconds
 * Occasionally ~ 86401 or 86399 seconds [1 seconds (leap second) variation due to earth wobbling]
 * UTC time is measured via atomic clock.
 * JVM doesn't not handle Leap seconds as it is not a serious issue to deal with.
 * 
 * For information : UT is another standard based on astronomical observations, not on atomic clocks.
 * It is there since 1884. One of its version UT1 counts for leap seconds. 
 * At that time, GMT (Greenwich Mean Time) chosen as World's standard time.
 * What's means that there is this notion(idea/belief) of timezones across the world and 
 * these timezone various from few our in GMT which is by itself a timezone.
 * Greenwich is actually a place in London and also has an Observatory called Royal Observatory which is where GMT is measure.
 * Many times GMT is also alternatively referred to as UTC which we just discussed.
 * 
 * There are classes which handle different timezones.
 * In some countries, they have Daylight Saving Time.
 * It is a practice for shifting the clock ahead when summer approaches and back when winter come back
 * so that people can have 1 hour more of daylight in the afternoon and evening during the summers.
 * 
 * Calendar systems : (when we deal with days, months, year)
 * 3 types of Calendar system :
 * <ul>
 * <li>Solar : Gregorian (Leap year divisible by 4 and but not 100th year), Julian (Older than Gregorian, Leap year divisible by 4 for all)</li>
 * <li>Lunar : Islamic</li>
 * <li>Lunisolar : Buddhist, Chinese, Hindu Lunisolar, Hebrew</li>
 * </ul>
 * 
 * Gregorian calendar (15 Oct, 1582) is used worldwide. How time mesures before that ? By using Proleptic Gregorian calendar applying same rules.
 * 
 * Java 8 Date & Time API uses the international standard called ISO 8601 which is based on Proleptic Gregorian calendar
 * which means that Gregorian rules of leap years is apply for all times.
 */
public class DateTime {
	
	public static void main(String[] args) {
		legacyDateTime();
	}

	private static void legacyDateTime() {
		
		java.util.Date currentDate = new java.util.Date();
		System.out.println("Current Date :: " + currentDate);
		System.out.println("Current Date in milliseconds :: " + currentDate.getTime());
		
		/**
		 * Passing 121 to add on 1900 is a way to tell Date object I am looking for 2021 as year.
		 * Passing 3 for April because month is 0 based (like array). 0=Jan, 1=Feb, 2=Mar, 3=Apr etc
		 * Same goes for hours, minutes, seconds i.e., 0 based value.
		 * For date we can between 1 to 31 as value.
		 */
		java.util.Date legacyDate = new java.util.Date(121, 3, 18);
		System.out.println(legacyDate); //Sun Apr 18 00:00:00 IST 2021
		//IST is timezone (Indian Standard Time)
		
		/**
		 * Java introduce Calendar class, Gregorian Calendar (extends Calendar) and TimeZone in Java 1.1 to solve above problems.
		 * Still here, months starts from 0. Calendar and Date are not threadsafe. Calendar & Date confusing as they are doind similar kind of things.
		 */
		Calendar calendarDate = new GregorianCalendar(2021, 3, 18);
		System.out.println(calendarDate.getTime()); //Sun Apr 18 00:00:00 IST 2021
		calendarDate.add(Calendar.MONTH, 11); //It will impact on whole time i.e. here year shifted from 2021 to 2022 by adding 11months.
		System.out.println("Calendar.add() : " + calendarDate.getTime()); //Calendar.add() : Fri Mar 18 00:00:00 IST 2022
		
		Calendar calendarDate2 = new GregorianCalendar(2021, 3, 18);
		calendarDate2.roll(Calendar.MONTH, 11); //It will not impact on whole time. It only deals with what we tell it to target for. Eg: year is not affected here. Only month is increased.
		System.out.println("Calendar.roll() : " + calendarDate2.getTime()); //Calendar.roll() : Thu Mar 18 00:00:00 IST 2021
		
		System.out.println(Calendar.getInstance()); //java.util.GregorianCalendar[time=1618736516497,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Calcutta", ... ], ... ] // ... added by me to make it short.
		
		System.out.println(TimeZone.getDefault()); //sun.util.calendar.ZoneInfo[id="Asia/Calcutta",offset=19800000,dstSavings=0,useDaylight=false,transitions=7,lastRule=null]
		
		System.out.println();
		System.out.println("All available TimeZone IDs : ");
		for(String timeZoneId : TimeZone.getAvailableIDs())
			System.out.println(timeZoneId);
		System.out.println();
		
		Calendar newYorkCalendar = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"));
		newYorkCalendar.set(2021, Calendar.APRIL, 18, 14, 45);
		System.out.println("It supposed to print time according to NewYork timezone. Lets see : \n" + newYorkCalendar.getTime()); //Mon Apr 19 00:15:49 IST 2021 //Here it is returning time in Indian timezone instead of Newyork.
		System.out.println("Printing exact time aligned with NewYork timezone : \n" 
			+ newYorkCalendar.get(Calendar.YEAR) + "/" + newYorkCalendar.get(Calendar.MONTH) + "/" + newYorkCalendar.get(Calendar.DATE) + "  "
			+ newYorkCalendar.get(Calendar.HOUR) + ":" + newYorkCalendar.get(Calendar.MINUTE) + " " 
			+ (newYorkCalendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM") + " " + newYorkCalendar.getTimeZone().getID() ); //2021/3/18  2:45 PM America/New_York //Here we get the expected value in Newyork timezone.
		
		/**
		 * Some helpful calendar operations:
		 */
		System.out.println("newYorkCalendar.after(calendarDate) : " + newYorkCalendar.after(calendarDate)); //false
		System.out.println("newYorkCalendar.before(calendarDate) : " + newYorkCalendar.before(calendarDate)); //true
		
	}

}
