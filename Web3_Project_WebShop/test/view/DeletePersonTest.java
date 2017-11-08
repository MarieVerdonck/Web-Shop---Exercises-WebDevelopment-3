package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DeletePersonTest extends SeleniumTest {
	
	@Before
	public void setUpTest() {
		driver.get(baseUrl + "/Controller?action=signUp");
	}	

	@Test
	public void testDeletePerson() {
		String useridRandom = generateRandomNrOrderToRunTestMoreThanOnce("jakke");
		submitPersonForm(useridRandom, "Test", "Test", useridRandom + "@hotmail.com" , "1234");
		
		driver.get(baseUrl + "/Controller?action=overviewUsers");
		
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
				if (listItem.getText().contains(useridRandom + "@hotmail.com")) {
				found=true;
			}
		}
		assertEquals(false, found);
	}
	
	@Test
	public void testDeletePerson_Cancelled() {
		String useridRandom = generateRandomNrOrderToRunTestMoreThanOnce("jakke");
		submitPersonForm(useridRandom, "Test", "Test", useridRandom + "@hotmail.com" , "1234");
		
		driver.get(baseUrl + "/Controller?action=overviewUsers");
		
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
				if (listItem.getText().contains(useridRandom + "@hotmail.com")) {
				found=true;
			}
		}
		assertEquals(true, found);
		
		driver.get(lastDeleteLink);
		WebElement deletebutton=driver.findElement(By.id("delete"));
		deletebutton.click();	
	}
}
