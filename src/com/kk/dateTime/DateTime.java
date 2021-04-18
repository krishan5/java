package com.kk.dateTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
		java8DateTimeApi();
		
	}

	/**
	 * Legacy classes from {@code java.util} package :
	 * <ul>
	 * <li>Date</li>
	 * <li>Calendar</li>
	 * <li>TimeZone</li>
	 * </ul>
	 */
	private static void legacyDateTime() {
		System.out.println("/******************************legacyDateTime()*****************************/");
		System.out.println();
		
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
		 * Java introduce Calendar abstract class, Gregorian Calendar (extends Calendar) and TimeZone in Java 1.1 to solve above problems.
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
		
		/**
		 * You can uncomment and see long list of zone ids.
		 * 
		System.out.println();
		System.out.println("All available TimeZone IDs : ");
		for(String timeZoneId : TimeZone.getAvailableIDs())
			System.out.println(timeZoneId);
		System.out.println();
		*/
		
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
	
	/**
	 * Commonly used java 8 DateTimeApi classes from {@code java.time} package :
	 * <ul>
	 * <li>LocalDate : It only supports Date. No support of Time and Timezone.</li>
	 * <li>LocalTime : It only supports Time. No support of Date and Timezone.</li>
	 * <li>LocalDateTime : It only supports Date and Time. No support of Timezone.</li>
	 * <li>ZonedDateTime : It supports all i.e. Date, Time, Timezone. It is similar to GregorianCalendar class.</li>
	 * <li>Instant : It is a single instantaneous point on the timeline.</li>
	 * </ul>
	 * 
	 * All these classes implement an interface called "Temporal". Temporal means there is an involvement of concept of time.
	 * Temporal also extends another interface called "TemporalAccessor" which contains only getters (as its name suggests 'accessor').
	 * <br>
	 * 
	 * Following are more classes in {@code java.time} package which represents an amount of time :
	 * <ul>
	 * <li>Duration : It represents duration b/w two points of time. i.e, Duration b/w two instances of {@code Instant} class. Duration is associated with Time.</li>
	 * <li>Period : It represents interval b/w two dates. Eg: Age is calculated using two dates. Period is associated with Date.</li>
	 * </ul>
	 * 
	 * Duration and Period implements "TemporalAmount" interface.
	 * <br>
	 * 
	 * ISO 8601 Format :
	 * [date]T[time][zone offset]
	 * Example: 2021-04-18T01:00-7:00[America/Los_Angeles]
	 * where :
	 * <ul>
	 * <li> 2021-04-18 = [date] whose each component is separated by hyphen (-) </li>
	 * <li> 01:00 = [time] which is 1 AM. Time component elements are separated by colon (:) </li>
	 * <li> -7:00 = offset of UTC/GMT is minus 7 hours i.e. -7hrs </li>
	 * <li> [America/Los_Angeles] = timezone </li>
	 * </ul>
	 * 
	 * Lets see output of java 8 classes as per ISO 8601 Format :
	 * <ul>
	 * <li> LocalDate : 2021-04-18 </li>
	 * <li> LocalTime : 01:00 </li>
	 * <li> LocalDateTime : 2021-04-18T01:00 </li>
	 * <li> ZonedDateTime : 2021-04-18T01:00-7:00[America/Los_Angeles] </li>
	 * <li> In case we deal with UTC/GMT instead of offset : 2021-04-18T01:00Z[UTC] : Z is printed in replacement of offset </li>
	 * </ul>
	 */
	private static void java8DateTimeApi() {
		System.out.println();
		System.out.println("/******************************java8DateTimeApi()*****************************/");
		System.out.println();
		
		LocalDate localDate = LocalDate.of(2021, Month.APRIL, 18);
		System.out.println("LocalDate = " + localDate); //LocalDate = 2021-04-18
		
		LocalDate newLocalDate = localDate.plusMonths(7); //New DateTime API is threadsafe and immutable, hence it is returning new object here with modifications.
		System.out.println("New LocalDate = " + newLocalDate);
		
		LocalTime localTime = LocalTime.of(2, 35, 56);
		System.out.println("LocalTime = " + localTime); //LocalTime = 02:35:56
		
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		System.out.println("LocalDateTime = " + localDateTime); //LocalDateTime = 2021-04-18T02:35:56
		
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York"));
		System.out.println("ZonedDateTime = " + zonedDateTime); //ZonedDateTime = 2021-04-18T02:35:56-04:00[America/New_York]
		
		Instant startInstant = Instant.now();
		performance();
		Instant endInstant = Instant.now();
		Duration duration = Duration.between(startInstant, endInstant);
		System.out.println("Time taken by performance() via Instant.now() and Duration.between() : " + duration.getNano()); //0
		
		/**
		 * Whenever you need to test performance, always use System.nanoTime() approach as it is meant for this case specially.
		 */
		long startNanoTime = System.nanoTime();
		performance();
		long endNanoTime = System.nanoTime();
		System.out.println("Time taken by performance() via System.nanoTime() : " + (endNanoTime - startNanoTime)); //4700
		
	}
	
	private static void performance() {
		String s = "";
		for(int i=0; i < Integer.MAX_VALUE; i++) {
			s+=i;
		}
	}

}
