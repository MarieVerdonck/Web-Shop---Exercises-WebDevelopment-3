package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import domain.Person;

public class LoginTest {
	WebDriver driver;
	String useridRandom;
	String PASSWORD = "1234";
	String FIRSTNAME = "Jan";
	String url = "http://localhost:8080/labo5Web/Controller";

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", ".../chromedriver");
		driver = new ChromeDriver();
		registerUser();
		driver.get(url);
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
		int random = (int) (Math.random() * 1000 + 1);
		return random + component;
	}

	private void registerUser() {
		// register new user for the test
		driver.get(url+"?action=signUp");

		useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");

		fillOutField("userid", useridRandom);
		fillOutField("firstName", FIRSTNAME);
		fillOutField("lastName", "Janssens");
		fillOutField("email", "jan.janssens@hotmail.com");
		fillOutField("password", PASSWORD);

		WebElement button = driver.findElement(By.id("signUp"));
		button.click();
	}

	private void deleteUser(String userid) {
		// delete test user
		driver.get(url+"?action=overview");
		String cssSelector = "a[href*='deletePerson&userId=" + userid + "']";
		WebElement link = driver.findElement(By.cssSelector(cssSelector));
		link.click();

		WebElement deleteButtonConfirm = driver.findElement(By.cssSelector("input[type='submit'"));
		deleteButtonConfirm.click();
	}

	private void logIn(String userid, String password) {
		fillOutField("userid", userid);
		fillOutField("password", password);

		WebElement button = driver.findElement(By.id("logIn"));
		button.click();
	}

	private boolean findTekstWelcome() {
		ArrayList<WebElement> paragraphs = (ArrayList<WebElement>) driver.findElements(By.tagName("p"));
		Boolean found = false;
		for (WebElement webElement : paragraphs) {
			if (webElement.getText().equals("Welcome, " + FIRSTNAME + ".")) {
				found = true;
			}
		}
		return found;
	}

	@After
	public void clean() {
		deleteUser(useridRandom);
		driver.quit();
	}

	@Test
	public void testCorrectLoginHomepageWelcomeTextLogoutButton() {
		Boolean found = null;
		
		driver.get(url);
		logIn(useridRandom, PASSWORD);
		assertEquals("Home", driver.getTitle());

		assertTrue(findTekstWelcome());

		// loginknop aanwezig
		assertNotNull(driver.findElement(By.id("logOut")));

	}

	@Test
	public void testKeepedLoggedInWhenVisitedOtherPage() {
		driver.get(url);
		logIn(useridRandom, PASSWORD);

		driver.get(url+"?action=overview");
		driver.get(url+"?action=addProduct");
		driver.get(url);

		assertTrue(findTekstWelcome());
		assertNotNull(driver.findElement(By.id("logOut")));

	}

}
