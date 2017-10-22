package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UpdateProductTest extends SeleniumTest {
	
    //
	@Before
	public void setUpTest() {
		driver.get(baseUrl + "/Controller?action=addProduct");
	}
	
	@Test
	public void testAddProductCorrect() {
		String randomName = generateRandomNrOrderToRunTestMoreThanOnce("TestName");
		submitProductForm(randomName, "TestDescription", "0.05");
		
		driver.get(baseUrl + "/Controller?action=overviewProducts");
		
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
		
		ArrayList<WebElement> deleteLinks=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size()-1).getAttribute("href");
		driver.get(lastDeleteLink);
		
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
	}
}
