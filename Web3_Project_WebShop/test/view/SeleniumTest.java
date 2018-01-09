package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

	protected static WebDriver driver;
	protected static final String baseUrl = "http://localhost:8080/r0298778_VerdonckMaria/";
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public SeleniumTest() {
		super();
	}

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		// windows: gebruik dubbele \\ om pad aan te geve
		driver = new ChromeDriver();
	}

	@AfterClass
	public static void clean() {
		driver.quit();
	}

	public void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	protected String generateRandomNrOrderToRunTestMoreThanOnce(String component) {
		int random = (int) (Math.random() * 1000 + 1);
		return random + component;
	}

	protected void submitProductForm(String name, String description, String price) {
		fillOutField("name", name);
		fillOutField("description", description);
		fillOutField("price", price);

		WebElement button = driver.findElement(By.id("addProduct"));
		button.click();
	}

	protected void submitPersonForm(String userid, String firstName, String lastName, String email, String password) {
		driver.get(baseUrl + "Controller?action=signUp");
		fillOutField("userid", userid);
		fillOutField("firstName", firstName);
		fillOutField("lastName", lastName);
		fillOutField("email", email);
		fillOutField("password", password);

		WebElement button = driver.findElement(By.id("signUp"));
		button.click();
	}
	
	protected void assertTitle(String titleExpected) {
		String title=driver.getTitle();
		assertEquals(titleExpected,title);
	}

	protected void assertUserNotAdded(String userId) {
		driver.get(baseUrl + "Controller?action=overviewUsers");
		
		exception.expect(NoSuchElementException.class);
		WebElement deleteLink=(WebElement) driver.findElement(By.id("delete"+userId));
		assertNull(deleteLink);	
	}

}