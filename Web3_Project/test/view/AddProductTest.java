package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AddProductTest extends SeleniumTest {
	
	@Before
	public void setUpTest() {
		driver.get(baseUrl + "/Controller?action=addProduct");
	}
	
	@Test
	public void testAddProductCorrect() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitProductForm(randomName, "TestDescription", "0.05");
		
		String title=driver.getTitle();
		assertEquals("Product Overview",title);
		
		driver.get(baseUrl + "/Controller?action=overviewProducts");
		
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
		submitProductForm("", "TestDescription", "0.01");
		
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
		submitProductForm("Test", "", "0.05");
		
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
		submitProductForm("Test", "TestDescription", "-0.01");
		
		String title=driver.getTitle();
		assertEquals("Add Product",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("Give a valid price", errorMsg.getText());

		WebElement fieldProductName=driver.findElement(By.id("name"));
		assertEquals("Test",fieldProductName.getAttribute("value"));
		
		WebElement fieldProductDescription=driver.findElement(By.id("description"));
		assertEquals("TestDescription",fieldProductDescription.getAttribute("value"));
		
		WebElement fieldPrice=driver.findElement(By.id("price"));
		assertEquals("0.0",fieldPrice.getAttribute("value"));
		
	}
}
