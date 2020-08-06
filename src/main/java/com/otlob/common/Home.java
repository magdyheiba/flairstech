package com.otlob.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.validation.Verifications;
import com.shaft.validation.Verifications.VerificationType;

public class Home {
	private WebDriver driver;
	private static String url = System.getProperty("gui.baseURL");

	// locators
	private By login_button = By.xpath("//a[contains(@class,'nav-link')and contains(@class,'login')]");
	private By myOrders_button = By.xpath("//a[contains(@class,'nav-link')and contains(@class,'webcart')]");
	private By orderName_text = By.xpath("//table/tbody//td/b[@class='ng-binding']");
	private By orderPrice_text = By
			.xpath("//table/tbody//td[contains(@class,'price-td')]/label[contains(@class,'ng-binding')]");
	private By removeOrder_button = By.xpath("//label[@ng-click='removeItem(item)']");
	private By orderQuantity_text = By.xpath("//span[@class='f-11']/b");
	private By email_input = By.xpath("//input[@type='email']");
	private By password_input = By.xpath("//input[@type='password']");
	private By submitLogin_button = By.xpath("//button[@type='submit']");

	// login method From Nav Bar
	public Orders login(String[] LoginData) {
		ElementActions.click(driver, login_button);
		ElementActions.type(driver, email_input, LoginData[0]);
		ElementActions.typeSecure(driver, password_input, LoginData[1]);
		ElementActions.click(driver, submitLogin_button);
		return new Orders(driver);
	}

	// constructor
	public Home(ThreadLocal<WebDriver> driver) {
		this.driver = driver.get();
	}

	// constructor
	public Home(WebDriver driver) {
		this.driver = driver;
	}

	// actions
	public static String getUrl() {
		return url;
	}

	// Navigate to the URL method
	public Home navigate() {
		BrowserActions.navigateToURL(driver, url);
		return this;
	}

	// click on order Details Method
	public Home clickOnMyOrders() {
		ElementActions.click(driver, myOrders_button);
		return this;
	}

	// get Order Name method
	public String getOrderName() {
		String OrderName = ElementActions.getText(driver, orderName_text);
		return OrderName;
	}

	// get Order price method
	public String getOrderPrice() {
		String OrderPrice = ElementActions.getText(driver, orderPrice_text);
		return OrderPrice;
	}

	// get Order price method
	public String getOrderQuantity() {
		String OrderQuantity = ElementActions.getText(driver, orderQuantity_text);
		return OrderQuantity;
	}

	// verify My Orders disappears after removing an order
	public void verifyElementDisappeared() {
		Verifications.verifyElementExists(driver, myOrders_button, VerificationType.NEGATIVE,
				"the element dissappeared which means the test passed");
	}

	// click on order Details Method
	public Home removeItemFromMyOrders() {
		ElementActions.click(driver, removeOrder_button);
		return this;
	}
}
