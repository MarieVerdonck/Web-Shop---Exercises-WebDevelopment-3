package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Product;

public class ProductDbInMemory {
	private Map<Integer, Product> records = new HashMap<Integer, Product>();
	
	public ProductDbInMemory () {
		Product rose = new Product("Rose", "The modest Rose puts forth a thorn", 2.25);
		add(rose);
		Product lily = new Product("Lily", "While the Lily white shall in love delight", 5.35);
		add(lily);
		Product poppy = new Product("Poppy", "In Flanders fields the poppies blow; Between the crosses, row on row", 1.26);
		add(poppy);
	}
	
	public Product get(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}
		return records.get(id);
	}
	
	public List<Product> getAll(){
		return new ArrayList<Product>(records.values());	
	}

	public void add(Product product){
		if(product == null){
			throw new DbException("No product given");
		}
		int id = records.size() + 1;
		product.setProductId(id);
		if (records.containsKey(product.getProductId())) {
			throw new DbException("Product already exists");
		}
		records.put(product.getProductId(), product);
	}
	
	public void update(Product product){
		if(product == null){
			throw new DbException("No product given");
		}
		if(!records.containsKey(product.getProductId())){
			throw new DbException("No product found");
		}
		records.put(product.getProductId(), product);
	}
	
	public void delete(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}
		records.remove(id);
	}
}
