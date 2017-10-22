package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DeleteProductTest extends SeleniumTest {
	
	@Before
	public void setUpTest() {
		driver.get(baseUrl + "/Controller?action=addProduct");
	}	
	
	@Test
	public void testDeleteProduct() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitProductForm(randomName, "TestDescription", "0.05");
		
		driver.get(baseUrl + "/Controller?action=overviewProducts");
		
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
		submitProductForm(randomName, "TestDescription", "0.05");
		
		driver.get(baseUrl + "/Controller?action=overviewProducts");
		
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
