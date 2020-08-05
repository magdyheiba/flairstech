package com.otlob.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.browser.BrowserActions;

public class Home {
	private WebDriver driver;
	private static String url = System.getProperty("gui.baseURL");

	// locators  Pa$$w0rd!
	private By login_button = By.xpath("//a[contains(@class,'nav-link')and contains(@class,'login')]");
	private By searchBox_input = By.xpath("//input[@name='mapFirstSearch']");
	private By findRestaurants_button = By.xpath("//button[text()='Find Restaurants']");
	private By searchedPlace_number = By.xpath("//ul[contains(@class,'dropdown-menu')]/li[1]");
	private By deliverHere_button = By.xpath("//button[@id='getAddress']");
	private By address_text = By.xpath("//span[contains(@class,'active-crumb')]");
	private By searchRestaurant_input = By.xpath("//input[@id='restSearchBox']");
	private By searchResult_text = By.xpath("//div[@class='media-body']/h4");

	// Navigate to the URL method
	public Home navigate() {
		BrowserActions.navigateToURL(driver, url);
		return this;
	}

	// constructor
	public Home(ThreadLocal<WebDriver> driver) {
		this.driver = driver.get();
	}

	// actions
	public static String getUrl() {
		return url;
	}

}
