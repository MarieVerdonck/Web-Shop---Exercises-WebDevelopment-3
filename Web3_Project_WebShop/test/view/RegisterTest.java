package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterTest extends SeleniumTest {

	@Before
	public void setUpTest() {
		driver.get(baseUrl + "/Controller?action=signUp");
	}

	@Test
	public void testRegisterCorrect() {
		String useridRandom = generateRandomNrOrderToRunTestMoreThanOnce("jakke");
		submitPersonForm(useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

		super.assertTitle("Home");

		driver.get(baseUrl + "Controller?action=overviewUsers");

		ArrayList<WebElement> listItems = (ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found = false;
		for (WebElement listItem : listItems) {
			if (listItem.getText().contains("jan.janssens@hotmail.com")
					&& listItem.getText().contains(" Jan Janssens")) {
				found = true;
			}
		}
		assertEquals(true, found);

		ArrayList<WebElement> deleteLinks = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//*[contains(@id, 'delete')]"));
		String lastDeleteLink = deleteLinks.get(deleteLinks.size() - 1).getAttribute("href");
		driver.get(lastDeleteLink);

		WebElement deletebutton = driver.findElement(By.id("delete"));
		deletebutton.click();
	}

	@Test
	public void testRegisterUseridEmpty() {
		submitPersonForm("", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

		super.assertTitle("Sign Up");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No userid given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterFirstNameEmpty() {
		submitPersonForm("jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");

		super.assertTitle("Sign Up");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No firstname given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterLastNameEmpty() {
		submitPersonForm("jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");

		super.assertTitle("Sign Up");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No last name given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterEmailEmpty() {
		submitPersonForm("jakke", "Jan", "Janssens", "", "1234");

		super.assertTitle("Sign Up");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No email given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterPasswordEmpty() {
		submitPersonForm("jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");

		super.assertTitle("Sign Up");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No password given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterUserAlreadyExists() {
		String useridRandom = generateRandomNrOrderToRunTestMoreThanOnce("pierke");
		submitPersonForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

		driver.get(baseUrl + "Controller?action=signUp");

		submitPersonForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

		super.assertTitle("Sign Up");

		// Error coming from postgres DB, catch?
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("User already exists", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals(useridRandom, fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals("Pieter", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals("Pieters", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("pieter.pieters@hotmail.com", fieldEmail.getAttribute("value"));

		driver.get(baseUrl + "Controller?action=overviewUsers");

		WebElement deleteLink = (WebElement) driver.findElement(By.id("delete" + useridRandom));
		String lastDeleteLink = deleteLink.getAttribute("href");
		driver.get(lastDeleteLink);

		WebElement deletebutton = driver.findElement(By.id("delete"));
		deletebutton.click();

	}
	
	String scriptInjection = "&#034;/ &gt;&lt;script&gt;alert(&#034;lol&#034;);&lt;/script&gt;";

	@Test
	public void testRegister_XSSPrevention_userId() {
		// XSS userId
		submitPersonForm(scriptInjection, "", "", "", "");
		super.assertTitle("Sign Up");
		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals(scriptInjection, fieldUserid.getAttribute("value"));
		super.assertUserNotAdded(scriptInjection);
	}
	
	@Test
	public void testRegister_XSSPrevention_firstName() {
		// XSS firstName
		submitPersonForm("XSSTest", scriptInjection, "", "", "");
		super.assertTitle("Sign Up");
		WebElement fieldFirstName = driver.findElement(By.id("firstName"));
		assertEquals(scriptInjection, fieldFirstName.getAttribute("value"));
		super.assertUserNotAdded("XSSTest");
	}
	
	@Test
	public void testRegister_XSSPrevention_lastName() {
		// XSS lastName
		submitPersonForm("XSSTest", "", scriptInjection, "", "");
		super.assertTitle("Sign Up");
		WebElement fieldLastName = driver.findElement(By.id("lastName"));
		assertEquals(scriptInjection, fieldLastName.getAttribute("value"));
		super.assertUserNotAdded("XSSTest");
	}
}
