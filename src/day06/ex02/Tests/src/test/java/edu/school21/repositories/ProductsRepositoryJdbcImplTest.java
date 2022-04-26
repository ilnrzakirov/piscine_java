package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.ResultSet;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {

    final ProductsReposutoryJdbcImpl productsReposutoryJdbc = new ProductsReposutoryJdbcImpl();
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ProductsReposutoryJdbcImpl().findAll();
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new ProductsReposutoryJdbcImpl().findById(1L).get();
    final Product EXPECTED_UPDATED_PRODUCT = productsReposutoryJdbc.findById(0L).get();


    @Test
    void findAllTest() {
        int size = 5;
        int count = 0;
        for (Product expected_find_all_product : EXPECTED_FIND_ALL_PRODUCTS) {
            count++;
        }
        Assertions.assertEquals(count, size);
    }

    @ParameterizedTest
    @ValueSource (ints = 1)
    void findByIDTest(int id){
        Assertions.assertEquals(1, EXPECTED_FIND_BY_ID_PRODUCT.getId());
    }

    @Test
    void updateTest(){
        Product product = productsReposutoryJdbc.findById(0L).get();
        product.setName("Tools");
        product.setPrice(400);
        productsReposutoryJdbc.update(product);
        Product inspectProduct = productsReposutoryJdbc.findById(0L).get();
        Assertions.assertEquals(400, inspectProduct.getPrice());
        Assertions.assertEquals("Tools", inspectProduct.getName());
        Assertions.assertNotEquals(EXPECTED_UPDATED_PRODUCT.getPrice(), inspectProduct.getPrice());
        Assertions.assertNotEquals(EXPECTED_UPDATED_PRODUCT.getName(), inspectProduct.getName());
    }

    @Test
    void saveTest(){
        ProductsReposutoryJdbcImpl productsReposutoryJdbc = new ProductsReposutoryJdbcImpl();
        List<Product> allLineBD = productsReposutoryJdbc.findAll();
        Product inspectedProduct = new Product(null ,"Tools", 500);
        productsReposutoryJdbc.save(inspectedProduct);
        List<Product> inspectAllLineBD = productsReposutoryJdbc.findAll();
        Integer sizePre = 0;
        for (Product product : allLineBD) {
            sizePre++;
        }
        Integer sizePost = 0;
        for (Product product : inspectAllLineBD) {
            sizePost++;
        }
        Assertions.assertNotEquals(sizePre, sizePost);
        Assertions.assertEquals(sizePre, sizePost - 1);
        Assertions.assertEquals("Tools", inspectAllLineBD.get(sizePost -1).getName());
        Assertions.assertEquals(500, inspectAllLineBD.get(sizePost - 1).getPrice());
    }

    @Test
    void deleteTest(){
        ProductsReposutoryJdbcImpl productsReposutoryJdbc = new ProductsReposutoryJdbcImpl();
        List<Product> allLineBD = productsReposutoryJdbc.findAll();
        productsReposutoryJdbc.delete(0L);
        List<Product> inspectList = productsReposutoryJdbc.findAll();
        Integer sizePre = 0;
        Integer sizePost = 0;
        for (Product product : allLineBD) {
            sizePre++;
        }
        for (Product product : inspectList) {
            sizePost++;
        }
        Assertions.assertEquals(sizePre, sizePost + 1);
    }
}
