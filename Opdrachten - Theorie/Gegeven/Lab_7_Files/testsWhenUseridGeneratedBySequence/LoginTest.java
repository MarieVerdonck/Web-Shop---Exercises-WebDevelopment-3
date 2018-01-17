package view;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import domain.Person;
import domain.ShopService;


public class LoginTest {
	ShopService shopservice = new ShopService();
	WebDriver driver;
	String emailRandom;
	String PASSWORD = "1234";
	String FIRSTNAME = "Jan";
	String url = "http://localhost:8080/1718_WebShop_Week8/Controller";

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Users/grjon/Google Drive/web3pers/chromedriver");
		driver = new ChromeDriver();
		registerUser();
		driver.get(url);
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private String generateRandomEmailInOrderToRunTestMoreThanOnce(String component) {
		int random = (int) (Math.random() * 1000 + 1);
		return random + component;
	}

	private void registerUser() {
		// register new user for the test
		driver.get(url+"?action=signUp");

		emailRandom = generateRandomEmailInOrderToRunTestMoreThanOnce("jan@jan.be");

		fillOutField("userid", "1234");  // only necessary for form - database generates userid itself
		fillOutField("firstName", FIRSTNAME);
		fillOutField("lastName", "Janssens");
		fillOutField("email", emailRandom);
		fillOutField("password", PASSWORD);

		WebElement button = driver.findElement(By.id("signUp"));
		button.click();
		
	}

	private void deleteUser(String userid) {
		// delete test user
		Person person = shopservice.getPersonWithEmail(emailRandom);
		userid = person.getUserid();
		driver.get(url+"?action=overview");
		String cssSelector = "a[href*='deletePerson&userid=" + userid + "']";
		WebElement link = driver.findElement(By.cssSelector(cssSelector));
		link.click();

		WebElement deleteButtonConfirm = driver.findElement(By.cssSelector("input[type='submit'"));
		deleteButtonConfirm.click();
	}

	private void logIn(String email, String password) {
		fillOutField("email", email);
		fillOutField("password", password);

		WebElement button = driver.findElement(By.id("login"));
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
		deleteUser(emailRandom);
		driver.quit();
	}

	@Test
	public void testCorrectLoginHomepageWelcomeTextLogoutButton() {
		
		driver.get(url);
		logIn(emailRandom, PASSWORD);
		assertEquals("Home", driver.getTitle());

		assertTrue(findTekstWelcome());

		// logoutknop aanwezig
		assertNotNull(driver.findElement(By.id("logout")));

	}

	@Test
	public void testKeepedLoggedInWhenVisitedOtherPage() {
		driver.get(url);
		logIn(emailRandom, PASSWORD);

		driver.get(url+"?action=overview");
		driver.get(url+"?action=addProduct");
		driver.get(url);

		assertTrue(findTekstWelcome());
		assertNotNull(driver.findElement(By.id("logout")));

	}

}
