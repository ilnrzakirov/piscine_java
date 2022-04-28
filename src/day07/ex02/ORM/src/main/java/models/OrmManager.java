package models;

import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    public static final String CONNECTION_ERROR = "Connection error";
    Connection connection;
    private String tableName;

    public OrmManager(){
        HikariDataSource dataSource = new HikariDataSource();
        Connection connection = null;
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        try {
            this.connection = dataSource.getConnection();
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
            Class<?> clazz = Class.forName(aClass);
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            this.tableName = tableName;
            try {
                Statement statement = connection.createStatement();
                String request = ("drop table if exists " + tableName + " cascade;");
                statement.execute(request);
                System.out.println(request);
                Field[] fields = clazz.getDeclaredFields();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("CREATE TABLE IF NOT EXISTS ");
                stringBuilder.append(tableName);
                stringBuilder.append(" (");

                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(OrmColumnId.class)){
                        stringBuilder.append(fields[i].getName());
                        stringBuilder.append(" SERIAL PRIMARY KEY");
                    }
                    if (fields[i].isAnnotationPresent(OrmColumn.class)){
                        OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                        stringBuilder.append(ormColumn.name());
                        if (fields[i].getType().getSimpleName().equalsIgnoreCase("string")){
                            stringBuilder.append(" varchar(").append(ormColumn.length()).append(")");
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase("integer")){
                            stringBuilder.append(" INTEGER");
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase("long")){
                            stringBuilder.append(" BIGINT");
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase("boolean")){
                            stringBuilder.append(" boolean");
                        }
                    }
                    if (i != fields.length - 1){
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append(");");
                statement.execute(stringBuilder.toString());
                System.out.println(stringBuilder.toString());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void save(Object entity){
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (!clazz.isAnnotationPresent(OrmEntity.class)){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(this.tableName);
        stringBuilder.append(" (");

        for (int i = 1; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumn.class)){
                OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                stringBuilder.append(ormColumn.name());
            }

            if (i != fields.length - 1){
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") VALUES (");

        for (int i = 1; i < fields.length; i++){

            try {
                fields[i].setAccessible(true);
                Object object = fields[i].get(entity);

                if (fields[i].getType().getSimpleName().equalsIgnoreCase("string")){
                    stringBuilder.append("'");
                }

                stringBuilder.append(object);

                if (fields[i].getType().getSimpleName().equalsIgnoreCase("string")){
                    stringBuilder.append("'");
                }

                if (i != fields.length - 1){
                    stringBuilder.append(", ");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append(");");
        System.out.println(stringBuilder.toString());

        try {
            Statement statement = connection.createStatement();
            statement.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(Object entity){

    }

    public <T> T findById(Long id, Class<T> aClass){
        if (!aClass.isAnnotationPresent(OrmEntity.class)){
            return null;
        }

        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(ormEntity.table());
        stringBuilder.append(" WHERE id=");
        stringBuilder.append(id);
        stringBuilder.append(";");
        ResultSet tableSet = null;

        try {
            Statement statement = connection.createStatement();
            System.out.println(stringBuilder.toString());
            tableSet = statement.executeQuery(stringBuilder.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
