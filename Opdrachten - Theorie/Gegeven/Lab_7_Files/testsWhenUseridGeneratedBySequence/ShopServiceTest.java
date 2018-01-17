package ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Person;
import domain.ShopService;

public class ShopServiceTest {
	private static ShopService service = new ShopService();
	private Person person;
	private String email;
	private String PASSWORD = "1234";
	private String EMAIL = "klaas@klaas.be";

	@Before
	public void setUp() {
		email = generateRandomEmailInOrderToRunTestMoreThanOnce(EMAIL);
		person = new Person("1234", email , PASSWORD, "Klaas", "Claesens");
		person.setPasswordHashed(PASSWORD);
	}

	@Test
	public void getPerson_should_return_the_person_if_registered() {
		service.addPerson(person);
		
		Person personRetrieved = service.getPersonWithEmail(email);

		assertNotNull(personRetrieved);
		assertTrue(personRetrieved.isCorrectPassword(PASSWORD));
		assertEquals(person.getEmail(), personRetrieved.getEmail());
		assertEquals(person.getFirstName(), personRetrieved.getFirstName());
		assertEquals(person.getLastName(),personRetrieved.getLastName());
	}

	@Test
	public void getPerson_should_return_null_if_person_not_registered() {
		service.addPerson(person);
		
		Person personRetrieved = service.getPersonWithEmail("Unknown");

		assertNull(personRetrieved);
	}

	@Test
	public void getUserIfAuthenticated_should_return_the_person_if_registered_and_correct_password() {
		service.addPerson(person);
		
		String userid = service.getPersonWithEmail(email).getUserid();
		Person personRetrieved = service.getUserIfAuthenticated(userid, PASSWORD);

		assertNotNull(personRetrieved);
		assertTrue(personRetrieved.isCorrectPassword(PASSWORD));
		assertEquals(userid, personRetrieved.getUserid());
	}

	@Test
	public void getUserIfAuthenticated_should_return_null_if_person_not_registered() {
		service.addPerson(person);
		
		Person personRetrieved = service.getUserIfAuthenticated("1234", PASSWORD);

		assertNull(personRetrieved);
	}

	@Test
	public void getUserIfAuthenticated_should_return_null_if_person_is_registered_but_incorrect_password() {
		service.addPerson(person);
		
		Person personRetrieved = service.getUserIfAuthenticated(service.getPersonWithEmail(email).getUserid(), "WrongPassword");

		assertNull(personRetrieved);
	}

	private String generateRandomEmailInOrderToRunTestMoreThanOnce(String component) {
		int random = (int) (Math.random() * 1000 + 1);
		return random+component;
	}

}
