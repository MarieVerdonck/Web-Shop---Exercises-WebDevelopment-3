package domain;

import java.util.List;

import db.PersonDbInMemory;
import db.ProductDbInMemory;

public class ShopService {
	private PersonDbInMemory personDb = new PersonDbInMemory();
	private ProductDbInMemory productDb = new ProductDbInMemory();

	public ShopService(){
	}
	
	public Person getPerson(String personId) {
		return getPersonDb().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonDb().getAll();
	}

	public void addPerson(Person person) {
		getPersonDb().add(person);
	}

	public void updatePersons(Person person) {
		getPersonDb().update(person);
	}

	public void deletePerson(String id) {
		getPersonDb().delete(id);
	}

	private PersonDbInMemory getPersonDb() {
		return personDb;
	}
	
	public Product getProduct(String productId) {
		return getProductDb().get(Integer.valueOf(productId));
	}

	public List<Product> getProducts() {
		return getProductDb().getAll();
	}

	public void addProduct(Product product) {
		getProductDb().add(product);
	}

	public void updateProducts(Product product) {
		getProductDb().update(product);
	}

	public void deleteProduct(String id) {
		getProductDb().delete(Integer.valueOf(id));
	}

	private ProductDbInMemory getProductDb() {
		return productDb;
	}
}