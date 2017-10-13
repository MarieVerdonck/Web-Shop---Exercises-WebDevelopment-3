package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import domain.Person;
import domain.Product;

public class ProductDbJDBC implements ProductDb{

	@Override
	public Product get(int id) {
		if(id < 0){
			throw new DbException("No valid id given");
		}
		try {
			Connection connection = JDBCConnection.getConnectionObject().getConnection();;
			String sql = "SELECT * FROM product WHERE userid='" + id + "'";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
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
