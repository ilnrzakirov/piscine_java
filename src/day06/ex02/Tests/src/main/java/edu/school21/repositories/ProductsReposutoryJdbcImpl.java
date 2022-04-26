package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImpl implements ProductsRepository {

    private DataSource dataSource;
    public static final String UPDATE_NAME = "UPDATE product SET name=? WHERE id=?";
    public static final String UPDATE_PRICE = "UPDATE product SET price=? WHERE id=?";

    public ProductsReposutoryJdbcImpl(){
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        dataSource = databaseBuilder.addScript("schema.sql").addScript("data.sql").build();
        try {
            dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        ResultSet allSet = null;
        Statement statement = null;
        List<Product> productList = new ArrayList<Product>();
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.createStatement();
            allSet = statement.executeQuery("SELECT * FROM PRODUCT");
            while (allSet.next()){
                Product product = new Product(
                        allSet.getLong("id"),
                        allSet.getString("name"),
                        allSet.getInt("price")
                );
                productList.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE id="+ id + ";");
            resultSet.next();
            Product product = new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price")
            );
            return Optional.of(product);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        PreparedStatement preparedStatement;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement = connection
                    .prepareStatement(UPDATE_NAME);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(UPDATE_PRICE);
            preparedStatement.setInt(1, product.getPrice());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        PreparedStatement preparedStatement;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM PRODUCT WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
