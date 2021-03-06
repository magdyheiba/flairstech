package com.otlob.common;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

public class Orders {
	private WebDriver driver;
	private static String url = System.getProperty("gui.baseURL");

	private By searchBox_input = By.xpath("//input[@name='mapFirstSearch']");
	private By searchedPlace_number;
	private By deliverHere_button = By.xpath("//span[text()='Deliver Here']");
	private By address_text = By.xpath("//span[contains(@class,'active-crumb')]");
	private By searchRestaurant_input = By.xpath("//input[@id='restSearchBox']");
	private By searchResult_input = By.xpath("//div[@class='media-body']/h4");
	private By searchForMeal_input = By.xpath("//input[@id='restSearchBox']");
	private By addItemToCart_button = By.xpath("//div[contains(@class,'addItemSection')]/button");
	private By addNumberOfItems_button = By.xpath("//button[contains(@ng-click,'plus')]");
	private By large_radioButton = By.xpath("//label[contains(.,'Large ')]/input");
	private By largePrice_text = By.xpath("//label[contains(.,'Large')]//span[@class='ng-binding']");
	private By addToCart_button = By.xpath("//button[@type='submit']");
	private By proceedToCheckout_button = By.xpath("//button[text()='Proceed to Checkout']");
	private By confirmAddress_button = By.xpath("//span[text()='Confirm']");
	private By phoneNumber_input = By.xpath("//input[@id='addressMobile']");
	private By addressFloor_input = By.xpath("//input[@id='addressFloor']");
	private By addressBuilding_input = By.xpath("//input[@id='addressBuildingNo']");
	private By addressAppartment_input = By.xpath("//input[@id='addressApartment']");
	private By saveAddress_button = By.xpath("//button[text()='Save Address']");
	private By cash_radioButton = By.xpath("//input[@id='cash-pay']");
	private By placeOrder_button = By.xpath("//button[text()='Place Order']");
	private By orderStatus_text = By.xpath("//h1[@class='f-28 bold f-23-m']");
	private By home_button = By.xpath("//a[@class='nav-link']/*[@class='nav-logo-img']");
	private By numberOfItems_text = By.xpath("//span/b[@class='ng-binding']");
	private By orderName_text = By.xpath("//p[@class='f-15 m-b-0 ng-binding']");
	private By restaurantStatus_text = By.xpath("//b[contains(@ng-class,'red-text')]");
	private By continueOrdering_button = By.xpath("//button[text()='Continue']");
	private By cartMessage_text = By.xpath("//p[@class='p-t-md f-14']");
	private By busyModelHeader_text = By.xpath("//p[@class='text-center f-16 m-y']");

	

	ArrayList<String> OrderDetails = new ArrayList<String>();

	// constructor
	public Orders(ThreadLocal<WebDriver> driver) {
		this.driver = driver.get();
	}

	// constructor
	public Orders(WebDriver driver) {
		this.driver = driver;
	}

	// actions
	public static String getUrl() {
		return url;
	}

	// search for a place and add item to the cart method
	public ArrayList<String> searchForRestaurantAndAddItemToCart(String[] Order) {
		ReportManager.logDiscrete("Searching for a place and add item to the cart " + Order);
		ElementActions.type(driver, searchBox_input, Order[0]);
		ElementActions.click(driver, getSearchResultNumber(convertStringToInteger(Order[1])));
		ElementActions.click(driver, deliverHere_button);
		OrderDetails.add(ElementActions.getText(driver, address_text));
		ElementActions.type(driver, searchRestaurant_input, Order[2]);
		ElementActions.click(driver, searchResult_input);
		ElementActions.type(driver, searchForMeal_input, Order[3]);
		OrderDetails.add(ElementActions.getText(driver, orderName_text));
		ElementActions.click(driver, addItemToCart_button);
		if (getRestaurantStatus().equalsIgnoreCase("busy")) {
			ElementActions.click(driver, continueOrdering_button);
		}
		setNumberOfItems(convertStringToInteger(Order[4]));
		ElementActions.click(driver, large_radioButton);
		String pricePerItem = ElementActions.getText(driver, largePrice_text);
		ElementActions.click(driver, addToCart_button);
		String FinalAmount = calculatePrice(Order[4], pricePerItem.substring(1, pricePerItem.length() - 1));
		OrderDetails.add(FinalAmount);
		String NumberOfItems = ElementActions.getText(driver, numberOfItems_text);
		OrderDetails.add(NumberOfItems);
		return OrderDetails;
	}

	// get Order Details method
	public ArrayList<String> getOrderDetails() {
		return OrderDetails;
	}

	// place order method
	public Orders placeOrder(String[] OrderDetails) {
		ReportManager.logDiscrete("place order details " + OrderDetails);
		ElementActions.click(driver, proceedToCheckout_button);
		ElementActions.click(driver, confirmAddress_button);
		ElementActions.type(driver, phoneNumber_input, OrderDetails[0]);
		ElementActions.type(driver, addressFloor_input, OrderDetails[1]);
		ElementActions.type(driver, addressBuilding_input, OrderDetails[2]);
		ElementActions.type(driver, addressAppartment_input, OrderDetails[3]);
		ElementActions.click(driver, saveAddress_button);
		ElementActions.click(driver, cash_radioButton);
		ElementActions.click(driver, placeOrder_button);
		return this;

	}

	// navigate to home screen method

	public Home navigateToHomeScreen() {
		ElementActions.click(driver, home_button);
		return new Home(driver);
	}

	// get Order Status method
	public String getOrderStatus() {
		String OrderStatus = ElementActions.getText(driver, orderStatus_text);
		return OrderStatus;
	}

	// get Restaurant Status method
	public String getRestaurantStatus() {
		String RestaurantStatus = ElementActions.getText(driver, restaurantStatus_text);
		return RestaurantStatus;
	}
	// get Restaurant Status method
		public String getBusyRestaurantMessage() {
			String RestaurantStatus = ElementActions.getText(driver, busyModelHeader_text);
			return RestaurantStatus;
		}
	
	
	

	// set number of items method
	public void setNumberOfItems(int Items) {
		for (int i = 0; i < Items - 1; i++) {
			ElementActions.click(driver, addNumberOfItems_button);
		}
	}

	// get search result number method
	public By getSearchResultNumber(int Result) {
		searchedPlace_number = By.xpath("//ul[contains(@class,'dropdown-menu')]/li[" + Result + "]");
		return searchedPlace_number;
	}

	// convert string to integer method
	public int convertStringToInteger(String string) {
		int Number = Integer.parseInt(string);
		return Number;
	}

	// convert string to integer method
	public double convertStringToDouble(String string) {
		double Number = Double.parseDouble(string);
		return Number;
	}

	// calculate the total amount method
	public String calculatePrice(String NumberOfItems, String PricePerItem) {
		double Result = (convertStringToInteger(NumberOfItems) * convertStringToDouble(PricePerItem));
		return "" + Result;
	}

	// Search For Restaurant method
	public Orders searchForRestaurant(String[] Order) {
		ElementActions.type(driver, searchBox_input, Order[0]);
		ElementActions.click(driver, getSearchResultNumber(convertStringToInteger(Order[1])));
		ElementActions.click(driver, deliverHere_button);
		ElementActions.type(driver, searchRestaurant_input, Order[2]);
		ElementActions.click(driver, searchResult_input);
		ElementActions.type(driver, searchForMeal_input, Order[3]);
		return this;
	}

	// get Cart Message text method
	public String getCartMessage() {
		String CartMessage = ElementActions.getText(driver, cartMessage_text);
		return CartMessage;
	}

}
