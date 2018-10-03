package westpacTest;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class CurrencyConverterTests {

	private static WebDriver openBrowser(String initialURL) {

		// Define location of chromedriver executable
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

		// Add browser option to maximise Chrome
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");

		// Create WebDriver instance
		WebDriver driver = new ChromeDriver(chromeOptions);

		// Launch browser with initialURL and return driver instance
		driver.get(initialURL);
		return driver;

	}

	private static void closeBrowser(WebDriver driver) {
		// Close browser
		driver.close();
	}

	private static WebDriver navigateToConverter(WebDriver driver) {

		// Define actions
		Actions action = new Actions(driver);

		// Move mouse over the 'FX, travel & migrant' link to display submenu
		action.moveToElement(driver.findElement(By.xpath("//*[@id='ubermenu-section-link-fx-travel-and-migrant-ps']")));
		action.build().perform();

		// Click on 'Currency Converter' link in submenu
		action.moveToElement(driver.findElement(By.xpath("//*[@id='ubermenu-item-cta-currency-converter-ps']")));
		action.click().build().perform();

		// Wait for the Currency Converter page to load
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Switch driver to iframe to access elements
		driver.switchTo().frame("westpac-iframe");

		// Return driver instance
		return driver;
	}

	private static void logPassFail(String message) {
		// Write test result out to console
		System.out.println(message);
	}

	private static boolean checkErrorMessage(WebDriver driver, String responseTxt) {

		// Define action and errMessage
		Actions action = new Actions(driver);
		String errMessage;

		// call navigateToConverter to browse website to the correct location
		navigateToConverter(driver);

		// User Story 1: Verify user gets correct error message if the Convert
		// button is clicked with no value entered in Amount field

		// Click on Convert button
		action.moveToElement(driver.findElement(By.xpath("//*[@id='convert']")));
		action.click().build().perform();

		// Get the error message text
		errMessage = driver.findElement(By.xpath("//*[@id='errordiv']")).getText();

		// Check that the error message displayed matches the one provided to
		// the method and return boolean
		return errMessage.contentEquals(responseTxt);

	}

	private static boolean convertCurrency(WebDriver driver, String currFromTxt, String currToTxt, String Amount,
			String responseTxt) {

		// Define action and errMessage
		Actions action = new Actions(driver);
		String conversionMessage;

		// Define ConvertFrom dropdown as a Select to enable choosing currency
		Select currFrom = new Select(driver.findElement(By.xpath("//*[@id='ConvertFrom']")));
		currFrom.selectByVisibleText(currFromTxt);

		// Define ConvertTo dropdown as a Select to enable choosing currency
		Select currTo = new Select(driver.findElement(By.xpath("//*[@id='ConvertTo']")));
		currTo.selectByVisibleText(currToTxt);

		// Clear the Amount field before entering required amount
		driver.findElement(By.xpath("//*[@id='Amount']")).clear();
		driver.findElement(By.xpath("//*[@id='Amount']")).sendKeys(Amount);

		// Click on Convert button
		action.moveToElement(driver.findElement(By.xpath("//*[@id='convert']")));
		action.click().build().perform();

		// Get the conversion response text
		conversionMessage = driver.findElement(By.xpath("//*[@id='resultsdiv']")).getText();

		// Check the conversion response text contains both from and to currency
		// names in correct order and return boolean
		Pattern pattern = Pattern.compile(responseTxt);
		return pattern.matcher(conversionMessage).find();

	}

	public static void executeTest1(WebDriver driver) {

		// call checkErrorMessage with driver instance and correctly worded
		// error message
		if (checkErrorMessage(driver, "Please enter the amount you want to convert.")) {
			logPassFail("User Story 1 - PASS: Error message displayed is an exact match.");
		} else {
			logPassFail("User Story 1 - FAIL: Error message displayed does not match");
		}
	}

	public static void executeTest2a(WebDriver driver) {

		// call convertCurrency with driver instance and currency from, currency
		// to, the amount and regex to match response against
		if (convertCurrency(driver, "New Zealand Dollar", "United States Dollar", "1",
				"1 New Zealand Dollar @\\s\\d.\\d{4}\\s=\\s\\.\\d{2}\\sUnited States Dollar")) {
			logPassFail("User Story 2a - PASS: Conversion response shows correct transaction details.");
		} else {
			logPassFail("User Story 2a - FAIL: Conversion response does not show correct transaction details.");
		}
	}

	public static void executeTest2b(WebDriver driver) {

		// call convertCurrency with driver instance and currency from, currency
		// to, the amount and regex to match response against
		if (convertCurrency(driver, "United States Dollar", "New Zealand Dollar", "1",
				"1 United States Dollar =\\s\\d\\.\\d{2}\\sNew Zealand Dollar")) {
			logPassFail("User Story 2b - PASS: Conversion response shows correct transaction details.");
		} else {
			logPassFail("User Story 2b - FAIL: Conversion response does not show correct transaction details.");
		}
	}

	public static void executeTest2c(WebDriver driver) {

		// call convertCurrency with driver instance and currency from, currency
		// to, the amount and regex to match response against
		if (convertCurrency(driver, "Pound Sterling", "New Zealand Dollar", "1",
				"1 Pound Sterling =\\s\\d\\.\\d{2}\\sNew Zealand Dollar")) {
			logPassFail("User Story 2c - PASS: Conversion response shows correct transaction details.");
		} else {
			logPassFail("User Story 2c - FAIL: Conversion response does not show correct transaction details.");
		}
	}

	public static void executeTest2d(WebDriver driver) {

		// call convertCurrency with driver instance and currency from, currency
		// to, the amount and regex to match response against
		if (convertCurrency(driver, "Swiss Franc", "Euro", "1", "1 Swiss Franc =\\s\\.\\d{2}\\sEuro")) {
			logPassFail("User Story 2d - PASS: Conversion response shows correct transaction details.");
		} else {
			logPassFail("User Story 2d - FAIL: Conversion response does not show correct transaction details.");
		}
	}

	public static void main(String[] args) {

		// open browser:
		WebDriver driver = openBrowser("https://westpac.co.nz/");

		// User Story 1 - Verify that clicking on Convert with no Amount entered
		// displays correct error message
		executeTest1(driver);

		// User Story 2a - Verify you can convert 1 NZD to 1 USD
		executeTest2a(driver);

		// User Story 2b - Verify you can convert 1 USD to 1 NZD
		executeTest2b(driver);

		// User Story 2c - Verify you can convert 1 GBP to 1 NZD
		executeTest2c(driver);

		// User Story 2d - Verify you can convert 1 CHF to 1 EUR
		executeTest2d(driver);

		// Close browser
		closeBrowser(driver);
	}
}
