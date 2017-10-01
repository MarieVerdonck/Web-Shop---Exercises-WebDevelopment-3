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

public class AddProductTest {
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
		
		String title=driver.getTitle();
		assertEquals("Product Overview",title);
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewProducts");
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains(randomName) &&  listItem.getText().contains("TestDescription")) {
				found=true;
			}
		}
		assertEquals(true, found);
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
		
	}
	
	@Test
	public void testAddProductNameEmpty(){
		submitForm("", "TestDescription", "0.01");
		
		String title=driver.getTitle();
		assertEquals("Add Product",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No name given", errorMsg.getText());

		WebElement fieldProductName=driver.findElement(By.id("name"));
		assertEquals("",fieldProductName.getAttribute("value"));
		
		WebElement fieldProductDescription=driver.findElement(By.id("description"));
		assertEquals("TestDescription",fieldProductDescription.getAttribute("value"));
		
		WebElement fieldPrice=driver.findElement(By.id("price"));
		assertEquals("0.01",fieldPrice.getAttribute("value"));

	}
	
	@Test
	public void testAddProductDescriptionEmpty(){
		submitForm("Test", "", "0.05");
		
		String title=driver.getTitle();
		assertEquals("Add Product",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No description given", errorMsg.getText());

		WebElement fieldProductName=driver.findElement(By.id("name"));
		assertEquals("Test",fieldProductName.getAttribute("value"));
		
		WebElement fieldProductDescription=driver.findElement(By.id("description"));
		assertEquals("",fieldProductDescription.getAttribute("value"));
		
		WebElement fieldPrice=driver.findElement(By.id("price"));
		assertEquals("0.05",fieldPrice.getAttribute("value"));

	}

	@Test
	public void testAddProductPriceNegative(){
		submitForm("Test", "TestDescription", "-0.01");
		
		String title=driver.getTitle();
		assertEquals("Add Product",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		String msg = errorMsg.getText();
		assertEquals("Give a valid price", errorMsg.getText());

		WebElement fieldProductName=driver.findElement(By.id("name"));
		assertEquals("Test",fieldProductName.getAttribute("value"));
		
		WebElement fieldProductDescription=driver.findElement(By.id("description"));
		assertEquals("TestDescription",fieldProductDescription.getAttribute("value"));
		
		WebElement fieldPrice=driver.findElement(By.id("price"));
		assertEquals("0.0",fieldPrice.getAttribute("value"));
		
	}
}
