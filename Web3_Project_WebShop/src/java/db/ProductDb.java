package db;

import java.util.List;

import domain.Product;

public interface ProductDb {

	Product get(int id);

	List<Product> getAll();

	void add(Product product);

	void update(Product product);

	void delete(int id);

}