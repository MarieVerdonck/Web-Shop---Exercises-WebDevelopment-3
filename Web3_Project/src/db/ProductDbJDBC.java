package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Product;

public class ProductDbJDBC implements ProductDb{

	@Override
	public Product get(int id) {
		if(id < 0){
			throw new DbException("No valid id given");
		}
		try {
			Connection connection = JDBCConnection.getConnectionObject().getConnection();;
			String sql = "SELECT * FROM product WHERE \"productId\"='" + id + "'";
	        Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( sql );
			System.out.println(result);
			if (!result.next()){
				return null;
			}
			Product product = this.putRowInProduct(result);
			statement.close();
			connection.close();
			return product;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public List<Product> getAll() {
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		List<Product> products = new ArrayList<Product>();
        String sql = "SELECT * FROM product";
		try {
	        Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( sql );
			while(result.next()) {
				products.add(this.putRowInProduct(result));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		} 
		return products;
	}

	@Override
	public void add(Product product) {
		if(product == null){
			throw new DbException("No product given");
		}
		if (this.get(product.getProductId())!=null) {
			throw new DbException("Product already exists");
		}
        String name = product.getName();
        String description = product.getDescription();
        Double price = product.getPrice();
		String sql = "INSERT INTO product (name, description, price) "
	            + "VALUES ('" + name + "','" + description +"'," + price + ");";
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Product product) {
		if(product == null){
			throw new DbException("No product given");
		}
		if (this.get(product.getProductId())==null) {
			throw new DbException("No product found");
		}
		int productId = product.getProductId();
        String name = product.getName();
        String description = product.getDescription();
        Double price = product.getPrice();
		String sql = "UPDATE product SET name = '" + name + "', description = '" + description + "', price = " + price + " WHERE \"productId\" = " + productId;
        Connection connection = JDBCConnection.getConnectionObject().getConnection();
        Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) {
		if(id < 0){
			throw new DbException("No valid id given");
		}
		if (this.get(id)==null) {
			throw new DbException("No product found with this id");
		}
		String sql = "DELETE FROM product WHERE \"productId\"='" + id + "'";
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	private Product putRowInProduct(ResultSet result) {
		try {
			String productId = result.getString("productid");
			String name = result.getString("name");
			String description = result.getString("description");
			String price = result.getString("price");
			
			Product product = new Product(Integer.parseInt(productId), name, description, Double.parseDouble(price));
			return product;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		
	}

}
