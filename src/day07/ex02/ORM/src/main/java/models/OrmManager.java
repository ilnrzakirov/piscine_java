package models;

import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    public static final String CONNECTION_ERROR = "Connection error";
    Connection connection;

    public OrmManager(){
        HikariDataSource dataSource = new HikariDataSource();
        Connection connection = null;
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            System.err.println(CONNECTION_ERROR);
        }
    }

    public void init() throws ClassNotFoundException {
        Reflections reflections;
        reflections = new Reflections("models");
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(OrmEntity.class);

        List<String> classes = set.stream()
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());

        for (String aClass : classes) {
            Class clazz = Class.forName(aClass);
            System.out.println(clazz.getSimpleName());
        }
    }

    public void save(Object entity){

    }

    public void update(Object entity){

    }

    public <T> T findById(Long id, Class<T> aClass){
        return null;
    }
}
