package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UpdateProductTest {
	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			// windows: gebruik dubbele \\ om pad aan te geven
			// hint: zoek een werkende test op van web 2 ...
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=addProduct");
	}
	
	@After
	public void clean() {
		driver.quit();
	}
		
	private void fillOutField(String name,String value) {
		WebElement field=driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}
	
	private String generateRandomNrOrderToRunTestMoreThanOnce(String component) {
		int random = (int)(Math.random() * 1000 + 1);
		return random+component;
	}
	
	private void submitForm(String name, String description, String price) {
		fillOutField("name", name);
		fillOutField("description", description);
		fillOutField("price", price);
		
		WebElement button=driver.findElement(By.id("addProduct"));
		button.click();		
	}
	

	@Test
	public void testAddProductCorrect() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitForm(randomName, "TestDescription", "0.05");
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewProducts");
		
		ArrayList<WebElement> updateLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'update')]"));
		String lastUpdateLink = updateLinks.get(updateLinks.size()-1).getAttribute("href");
		driver.get(lastUpdateLink);
		
		String title=driver.getTitle();
		assertEquals("Update Product",title);
		
		fillOutField("name", "ChangeNameTest");
		WebElement button=driver.findElement(By.id("addProduct"));
		button.click();	
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains("ChangeNameTest")) {
				found=true;
			}
		}
		assertEquals(true, found);
	}
}
