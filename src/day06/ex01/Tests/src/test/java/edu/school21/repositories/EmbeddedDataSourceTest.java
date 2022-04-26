package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class EmbeddedDataSourceTest {

    private DataSource dataSource;

    @BeforeEach
    public void init(){
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        dataSource = databaseBuilder.addScript("schema.sql").addScript("data.sql").build();
    }

    @Test
    void connectionTest() throws SQLException{
        assertNotNull(dataSource.getConnection());
    }
}
