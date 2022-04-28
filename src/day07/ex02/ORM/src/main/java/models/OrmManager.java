package models;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
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
    public static final String INSERT_INTO_ = "INSERT INTO ";
    public static final String STR = " (";
    public static final String STR1 = ", ";
    public static final String VALUES = ") VALUES (";
    public static final String STRING = "string";
    public static final String STR2 = "'";
    public static final String STR3 = ");";
    public static final String UPDATE_ = "UPDATE ";
    public static final String SET = " SET ";
    public static final String STR4 = "=";
    public static final String WHERE_ID = " WHERE id=";
    public static final String STR5 = ";";
    public static final String SELECT_FROM = "SELECT * FROM ";
    public static final String WHERE_ID1 = " WHERE id=";
    public static final String ID = "id";
    public static final String MODELS = "models";
    public static final String CREATE_TABLE_IF_NOT_EXISTS_ = "CREATE TABLE IF NOT EXISTS ";
    public static final String STR6 = " (";
    public static final String SERIAL_PRIMARY_KEY = " SERIAL PRIMARY KEY";
    public static final String VARCHAR = " varchar(";
    public static final String INTEGER = " INTEGER";
    public static final String BIGINT = " BIGINT";
    public static final String BOOLEAN = " boolean";
    public static final String BOOLEAN1 = "boolean";
    public static final String LONG = "long";
    public static final String INTEGER1 = "integer";
    public static final String STRING1 = "string";
    public static final String STR7 = ")";
    public static final String STR8 = ");";


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
        reflections = new Reflections(MODELS);
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
                stringBuilder.append(CREATE_TABLE_IF_NOT_EXISTS_);
                stringBuilder.append(tableName);
                stringBuilder.append(STR6);

                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(OrmColumnId.class)){
                        stringBuilder.append(fields[i].getName());
                        stringBuilder.append(SERIAL_PRIMARY_KEY);
                    }
                    if (fields[i].isAnnotationPresent(OrmColumn.class)){
                        OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                        stringBuilder.append(ormColumn.name());
                        if (fields[i].getType().getSimpleName().equalsIgnoreCase(STRING1)){
                            stringBuilder.append(VARCHAR).append(ormColumn.length()).append(STR7);
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase(INTEGER1)){
                            stringBuilder.append(INTEGER);
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase(LONG)){
                            stringBuilder.append(BIGINT);
                        } else if (fields[i].getType().getSimpleName().equalsIgnoreCase(BOOLEAN1)){
                            stringBuilder.append(BOOLEAN);
                        }
                    }
                    if (i != fields.length - 1){
                        stringBuilder.append(STR1);
                    }
                }
                stringBuilder.append(STR8);
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
        stringBuilder.append(INSERT_INTO_);
        stringBuilder.append(this.tableName);
        stringBuilder.append(STR);

        for (int i = 1; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumn.class)){
                OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                stringBuilder.append(ormColumn.name());
            }

            if (i != fields.length - 1){
                stringBuilder.append(STR1);
            }
        }
        stringBuilder.append(VALUES);

        for (int i = 1; i < fields.length; i++){

            try {
                fields[i].setAccessible(true);
                Object object = fields[i].get(entity);

                if (fields[i].getType().getSimpleName().equalsIgnoreCase(STRING)){
                    stringBuilder.append(STR2);
                }

                stringBuilder.append(object);

                if (fields[i].getType().getSimpleName().equalsIgnoreCase(STRING)){
                    stringBuilder.append(STR2);
                }

                if (i != fields.length - 1){
                    stringBuilder.append(STR1);
                }
                fields[i].setAccessible(false);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append(STR3);
        System.out.println(stringBuilder.toString());

        try {
            Statement statement = connection.createStatement();
            statement.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(Object entity){
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        if (!clazz.isAnnotationPresent(OrmEntity.class)){
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE_).append(tableName).append(SET);
        for (int i = 1; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                stringBuilder.append(ormColumn.name());
                stringBuilder.append(STR4);
            }
            try {
                fields[i].setAccessible(true);
                Object object = fields[i].get(entity);
                if (fields[i].getType().getSimpleName().equalsIgnoreCase(STRING)){
                    stringBuilder.append(STR2);
                }

                stringBuilder.append(object);

                if (fields[i].getType().getSimpleName().equalsIgnoreCase("string")){
                    stringBuilder.append(STR2);
                }

                if (i != fields.length - 1){
                    stringBuilder.append(STR1);
                }
                fields[i].setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try {
            fields[0].setAccessible(true);
            Object object = fields[0].get(entity);
            stringBuilder.append(WHERE_ID);
            stringBuilder.append(object).append(STR5);
            fields[0].setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(stringBuilder.toString());
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public <T> T findById(Long id, Class<T> aClass){
        if (!aClass.isAnnotationPresent(OrmEntity.class)){
            return null;
        }

        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM);
        stringBuilder.append(ormEntity.table());
        stringBuilder.append(WHERE_ID1);
        stringBuilder.append(id);
        stringBuilder.append(STR5);
        ResultSet tableSet = null;
        T object = null;

        try {
            Statement statement = connection.createStatement();
            System.out.println(stringBuilder.toString());
            tableSet = statement.executeQuery(stringBuilder.toString());
            object = aClass.newInstance();
            tableSet.next();

            if (tableSet == null){
                return null;
            }

            Field[] fields = aClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)){
                    field.set(object, tableSet.getLong(ID));
                } else if (field.isAnnotationPresent(OrmColumn.class)){
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    field.set(object, tableSet.getObject(ormColumn.name()));
                }

            }
        } catch (SQLException | InstantiationException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return object;
    }
}
