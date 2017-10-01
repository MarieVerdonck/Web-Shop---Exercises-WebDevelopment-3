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

public class DeletePersonTest {
	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			// windows: gebruik dubbele \\ om pad aan te geven
			// hint: zoek een werkende test op van web 2 ...
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=signUp");
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
	
	private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
		int random = (int)(Math.random() * 1000 + 1);
		return random+component;
	}
	
	private void submitForm(String userid, String firstName,String lastName, String email, String password) {
		fillOutField("userid", userid);
		fillOutField("firstName", firstName);
		fillOutField("lastName",lastName);
		fillOutField("email", email);
		fillOutField("password", password);
		
		WebElement button=driver.findElement(By.id("signUp"));
		button.click();		
	}	

	@Test
	public void testDeleteProduct() {
		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
		submitForm(useridRandom, "Test", "Test", "Test@hotmail.com" , "1234");
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewUsers");
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		String title=driver.getTitle();
		assertEquals("Delete Person",title);
		
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains("Test@hotmail.com")) {
				found=true;
			}
		}
		assertEquals(false, found);
	}
	
	@Test
	public void testDeleteProduct_Cancelled() {
		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
		submitForm(useridRandom, "Test", "Test", "Test@hotmail.com" , "1234");
		
		driver.get("http://localhost:8080/Web3_Lab1/Controller?action=overviewUsers");
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		String title=driver.getTitle();
		assertEquals("Delete Person",title);
		
		WebElement cancelbutton=driver.findElement(By.id("cancel"));
		cancelbutton.click();
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains("Test@hotmail.com")) {
				found=true;
			}
		}
		assertEquals(true, found);
		
		driver.get(lastDeleteLink);
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
	}
}
