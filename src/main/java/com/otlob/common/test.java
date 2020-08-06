package com.otlob.common;

public class test {
	// convert string to integer method
	public static int convertStringToInteger(String string) {
		int Number = Integer.parseInt(string);
		return Number;
	}

	public static double convertStringToFloat(String string) {
		float Number = Float.parseFloat(string);
		return Number;
	}

	public static String calculatePrice(String NumberOfItems, String PricePerItem) {
		double Result = (convertStringToFloat(NumberOfItems) * convertStringToFloat(PricePerItem));
		return "" + Result;

	}

	public static void main(String[] args) {

		String test1 = "3";
		String test2 = "(19.00)";
		
		System.out.println("Result is : " + test2.substring(1, test2.length() - 1));
		System.out.println("Result is : " + calculatePrice(test1, test2));
	}

}
