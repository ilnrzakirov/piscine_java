package school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    private static final String PATH_SC = "src/main/resources/schema.sql";
    private static final String PATH_DB = "src/main/resources/data.sql";

    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        Connection connection = null;
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getData(connection);

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        System.out.println();

        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(dataSource);
        User user = (User) usersRepositoryJdbc.findById(1L);
        System.out.println(user);
        System.out.println();
        List <User> userList = usersRepositoryJdbc.findAll();

        for (User user1 : userList) {
            System.out.println(user1);
        }

        User user2 = new User();
        user2.setEmail("sdfsdf");
        usersRepositoryJdbc.save(user2);
        User newUser = new User();
        newUser.setEmail("email");
        newUser.setId(1L);
        usersRepositoryJdbc.update(newUser);
        usersRepositoryJdbc.delete(2L);
        User findUser;
        System.out.println();
        findUser = usersRepositoryJdbc.findByEmail("email").get();
        System.out.println(findUser);
    }

    private static void getData(Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<String> allLineSchema = null;
        List<String> allLineData = null;

        try {
            allLineSchema = Files.readAllLines(Paths.get(PATH_SC));
            allLineData = Files.readAllLines(Paths.get(PATH_DB));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (allLineData  == null || allLineSchema == null){
            System.err.println("Empty file");
            System.exit(-1);
        }

        for (String line : allLineSchema) {

            try {
                statement.execute(line);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.exit(-1);
            }
        }

        for (String line : allLineData) {

            try {
                statement.execute(line);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.exit(-1);
            }
        }

    }
}
