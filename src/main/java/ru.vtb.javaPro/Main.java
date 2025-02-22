package ru.vtb.javaPro;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.javaPro.config.ApplicationConfig;
import ru.vtb.javaPro.connect.DButil;
import ru.vtb.javaPro.dto.Users;
import ru.vtb.javaPro.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = context.getBean(UserService.class);
        run(userService);
    }
    private static final Logger log;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log = Logger.getLogger(Main.class.getName());
    }


    public static void run(UserService userService) throws SQLException {
        log.info("Connecting to the database");
        Connection connection = DButil.getDataSource().getConnection();
        log.info("Database connection: " + connection.getCatalog());
        log.info("Start init database");
        DButil.dbMigration(connection);
        log.info("insert data for 1 row");
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
        log.info("read all data after update row");
        log.info(userService.readAll().toString());
        // delete data
        log.info("delete data");
        log.info("delete row from table users: " + userService.deleteUser(new Users(1L, "EvgenUpdate")).toString());
        log.info("read all data after delete row");
        log.info(userService.readAll().toString());

        log.info("Database closed");
        connection.close();

    }
}
