package ru.vtb.javaPro;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.javaPro.config.ApplicationConfig;
import ru.vtb.javaPro.connect.DButil;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.dto.Users;
import ru.vtb.javaPro.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

//@ComponentScan
public class Main {
    private static final Logger log;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log = Logger.getLogger(Main.class.getName());
    }

//    @SneakyThrows
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
//        ApplicationConfig applicationConfig = context.getBean(ApplicationConfig.class);
//        applicationConfig.run();
//        Connection connection = context.getBean(Connection.class);
//        DButil DButil = context.getBean(DButil.class);
//        ApplicationConfig applicationConfig = new ApplicationConfig("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
        log.info("Connecting to the database");
        Connection connection = DButil.getDataSource().getConnection();
        log.info("Database connection: " + connection.getCatalog());
        log.info("Start init database");
        DButil.dbMigration(connection);
        log.info("insert data for 1 row");
        UserService userService = new UserService(new UserDao(DButil.getDataSource().getConnection()));
        userService.insertRow(new Users("Evgen"));
        userService.insertRow(new Users("Vasia"));
        userService.insertRow(new Users("Sergey"));
        userService.insertRow(new Users("Ivan"));
        userService.insertRow(new Users("Katya"));
        // read all data
        log.info("read all data");
        log.info(userService.readAll().toString());
        // find data by id
        log.info("find data by id");
        log.info(userService.getUser(1L).toString());
        // update data
        log.info("update data by id");
        log.info(userService.updateUser(new Users(1L, "EvgenUpdate")).toString());
        // delete data
        log.info("delete data");
        log.info("delete row from table users: " + userService.deleteUser(new Users(1L, "EvgenUpdate")).toString());

        log.info("Database closed");
        connection.close();
    }
}
