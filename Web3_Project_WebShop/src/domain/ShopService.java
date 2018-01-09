package domain;

import java.util.List;
import java.util.Properties;

import db.JDBCConnection;
import db.PersonDb;
import db.PersonDbInMemory;
import db.PersonDbJDBC;
import db.ProductDb;
import db.ProductDbInMemory;
import db.ProductDbJDBC;

public class ShopService {
	private final PersonDb personDb;
	private final ProductDb productDb;

	public ShopService(Properties properties){
		JDBCConnection connection = new JDBCConnection(properties);
		personDb = new PersonDbJDBC(connection);
		productDb = new ProductDbJDBC(connection);
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

	private PersonDb getPersonDb() {
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

	private ProductDb getProductDb() {
		return productDb;
	}
}
