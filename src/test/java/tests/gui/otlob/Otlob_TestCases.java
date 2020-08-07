package tests.gui.otlob;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.otlob.common.Home;
import com.otlob.common.Orders;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.AssertionComparisonType;
import com.shaft.validation.Assertions.AssertionType;

import io.qameta.allure.Description;

public class Otlob_TestCases {
	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private ThreadLocal<ExcelFileManager> Otlob = new ThreadLocal<>();
	ArrayList<String> orderSpecifications = new ArrayList<String>();
	String[] login;
	String[] Order;
	String[] OrderDetails;
	String[] searchForOrder;

	// Test cases
	@Test(description = "Order - Page", groups = { "gui" }, priority = 1)
	@Description("When I enter Home page, then I can search for a region and restaurant and can add order to cart and checkout")
	public void addNewOrder() {
		new Home(driver).navigate();
		new Orders(driver).searchForRestaurantAndAddItemToCart(Order);
		new Home(driver).login(login);
		String RestaurantStatus = new Orders(driver).getRestaurantStatus();
		new Orders(driver).placeOrder(OrderDetails);
		if (RestaurantStatus.equalsIgnoreCase("busy")) {
			String busyRestaurantMessage = new Orders(driver).getBusyRestaurantMessage();
			Assertions.assertEquals(
					"KFC is currently busy and is not accepting orders at this time. Would you like to view more restaurants near you?",
					busyRestaurantMessage, AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);
		} else {
			String OrderStatus = new Orders(driver).getOrderStatus();
			Assertions.assertEquals("Your order has been placed successfully", OrderStatus,
					AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);
		}
	}

	@Test(description = "Home - Verify", groups = { "gui" }, priority = 2)
	@Description("When I enter Home page, then I can search for a region and restaurant and can add order to cart and checkout")
	public void addItemToCartAndVerifyIt() {
		new Home(driver).navigate();
		orderSpecifications = new Orders(driver).searchForRestaurantAndAddItemToCart(Order);
		new Home(driver).login(login).navigateToHomeScreen().clickOnMyOrders();
		String ActualOrderPrice = new Home(driver).getOrderPrice();
		String ActualOrderQuantity = new Home(driver).getOrderQuantity();
		String ActualOrderAddress = new Home(driver).getOrderAddress();
		String ActualOrderName = new Home(driver).getOrderName();
		Assertions.assertEquals(orderSpecifications.get(0), ActualOrderAddress, AssertionComparisonType.CONTAINS,
				AssertionType.POSITIVE);
		Assertions.assertEquals(orderSpecifications.get(1), ActualOrderName, AssertionComparisonType.CONTAINS,
				AssertionType.POSITIVE);
		Assertions.assertEquals(orderSpecifications.get(2), ActualOrderPrice, AssertionComparisonType.CONTAINS,
				AssertionType.POSITIVE);
		Assertions.assertEquals(orderSpecifications.get(3), ActualOrderQuantity, AssertionComparisonType.CONTAINS,
				AssertionType.POSITIVE);

	}

	@Test(description = "Home - Page", groups = { "gui" }, priority = 3)
	@Description("When I enter Home page, then I can search for a region and restaurant and can add order to cart and then remove it")
	public void removeItemFromCart() {
		new Home(driver).navigate();
		new Orders(driver).searchForRestaurantAndAddItemToCart(Order);
		new Home(driver).login(login);
		new Orders(driver).navigateToHomeScreen().clickOnMyOrders().removeItemFromMyOrders();
		String CartMessage = new Orders(driver).searchForRestaurant(searchForOrder).getCartMessage();
		Assertions.assertEquals("There are no items in your cart", CartMessage, AssertionComparisonType.EQUALS,
				AssertionType.POSITIVE);
	}

	// read from excel
	private String[] readLoginTestData() {
		ArrayList<String> login = new ArrayList<String>();
		String colName = "Data1";
		String sheetName = "login";
		login.add(Otlob.get().getCellData(sheetName, "Email", colName));
		login.add(Otlob.get().getCellData(sheetName, "Password", colName));
		return login.toArray(new String[0]);
	}

	private String[] readOrderTestData() {
		ArrayList<String> Orders = new ArrayList<String>();
		String colName = "Data1";
		String sheetName = "Orders";
		Orders.add(Otlob.get().getCellData(sheetName, "city", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "search result", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "restaurant name", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "meal", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "number of items", colName));
		return Orders.toArray(new String[0]);
	}

	private String[] readOrderDetailsTestData() {
		ArrayList<String> OrderDetails = new ArrayList<String>();
		String colName = "Data1";
		String sheetName = "Orders";
		OrderDetails.add(Otlob.get().getCellData(sheetName, "phone number", colName));
		OrderDetails.add(Otlob.get().getCellData(sheetName, "addressFloor", colName));
		OrderDetails.add(Otlob.get().getCellData(sheetName, "addressBuilding", colName));
		OrderDetails.add(Otlob.get().getCellData(sheetName, "addressFlat", colName));
		return OrderDetails.toArray(new String[0]);

	}

	private String[] readSearchForOrderTestData() {
		ArrayList<String> Orders = new ArrayList<String>();
		String colName = "Data1";
		String sheetName = "Orders";
		Orders.add(Otlob.get().getCellData(sheetName, "city", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "search result", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "restaurant name", colName));
		Orders.add(Otlob.get().getCellData(sheetName, "meal", colName));
		return Orders.toArray(new String[0]);
	}

	@BeforeClass
	public void beforeClass() {
		Otlob.set(new ExcelFileManager(System.getProperty("testDataFolderPath") + "Otlob.xlsx"));

		// read test data
		login = readLoginTestData();
		Order = readOrderTestData();
		OrderDetails = readOrderDetailsTestData();
		searchForOrder = readSearchForOrderTestData();
	}

	@BeforeMethod(onlyForGroups = { "gui" })
	public void beforeMethod() {
		driver.set(BrowserFactory.getBrowser());
	}

	@AfterMethod(onlyForGroups = { "gui" })
	public void afterMethod() {
		BrowserActions.closeCurrentWindow(driver.get());
	}
}
