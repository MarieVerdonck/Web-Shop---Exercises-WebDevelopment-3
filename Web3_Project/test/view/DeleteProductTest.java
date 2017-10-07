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

public class DeleteProductTest {
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
	public void testDeleteProduct() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitForm(randomName, "TestDescription", "0.05");
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewProducts");
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		String title=driver.getTitle();
		assertEquals("Delete Product",title);
		
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains(randomName)) {
				found=true;
			}
		}
		assertEquals(false, found);
	}
	
	@Test
	public void testDeleteProduct_Cancelled() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitForm(randomName, "TestDescription", "0.05");
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewProducts");
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		String title=driver.getTitle();
		assertEquals("Delete Product",title);
		
		WebElement cancelbutton=driver.findElement(By.id("cancel"));
		cancelbutton.click();	
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains(randomName)) {
				found=true;
			}
		}
		assertEquals(true, found);
		
		driver.get(lastDeleteLink);
		
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
	}
}