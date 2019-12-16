package com.scrotifybanking.scrotifybanking.util;

import java.util.Calendar;

/**
 * The type Statement date.
 */
public class StatementDate {
	
	private StatementDate() {
		
	}
	/**
	 * The Month int.
	 */
	static Integer monthInt;

	/**
	 * Month converter integer.
	 *
	 * @param month the month
	 * @return the integer
	 */
	public static Integer monthConverter(String month) {
		if (month.equalsIgnoreCase("January")) {
			monthInt = Calendar.JANUARY;
		}
		if (month.equalsIgnoreCase("february")) {
			monthInt = Calendar.FEBRUARY;
		}
		if (month.equalsIgnoreCase("march")) {
			monthInt = Calendar.MARCH;
		}
		if (month.equalsIgnoreCase("april")) {
			monthInt = Calendar.APRIL;
		}
		if (month.equalsIgnoreCase("may")) {
			monthInt = Calendar.MAY;
		}
		if (month.equalsIgnoreCase("june")) {
			monthInt = Calendar.JUNE;
		}
		if (month.equalsIgnoreCase("july")) {
			monthInt = Calendar.JULY;
		}
		if (month.equalsIgnoreCase("august")) {
			monthInt = Calendar.AUGUST;
		}
		if (month.equalsIgnoreCase("september")) {
			monthInt = Calendar.SEPTEMBER;
		}
		if (month.equalsIgnoreCase("october")) {
			monthInt = Calendar.OCTOBER;
		}

		if (month.equalsIgnoreCase("november")) {
			monthInt = Calendar.NOVEMBER;
		}
		if (month.equalsIgnoreCase("december")) {
			monthInt = Calendar.DECEMBER;
		}

		return monthInt + 1;
	}

}
