package view;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

	protected static WebDriver driver;
	protected static final String baseUrl = "http://localhost:8080/Web3_Project/";

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
		WebElement field=driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	protected String generateRandomNrOrderToRunTestMoreThanOnce(String component) {
		int random = (int)(Math.random() * 1000 + 1);
		return random+component;
	}

	protected void submitProductForm(String name, String description, String price) {
		fillOutField("name", name);
		fillOutField("description", description);
		fillOutField("price", price);
		
		WebElement button=driver.findElement(By.id("addProduct"));
		button.click();		
	}
	
	protected void submitPersonForm(String userid, String firstName,String lastName, String email, String password) {
		fillOutField("userid", userid);
		fillOutField("firstName", firstName);
		fillOutField("lastName",lastName);
		fillOutField("email", email);
		fillOutField("password", password);
		
		WebElement button=driver.findElement(By.id("signUp"));
		button.click();		
	}

}