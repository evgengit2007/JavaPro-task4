package ru.vtb.javaPro.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.vtb.javaPro.Main;
import ru.vtb.javaPro.connect.DButil;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.dto.Users;
import ru.vtb.javaPro.service.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Configuration
public class ApplicationConfig {
//    UserService userService;
/*
    private final String dataSourceUrl;
    private final String username;
    private final String password;

    public ApplicationConfig(
            @Value("${spring.datasource.datasourceurl}") String dataSourceUrl,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password)
    {
        this.dataSourceUrl = dataSourceUrl;
        this.username = username;
        this.password = password;
        System.out.println(username);
    }
*/

    @Bean
    public UserDao userDao() throws SQLException {
        return new UserDao(getConnection());
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

    private static final Logger log;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log = Logger.getLogger(Main.class.getName());
    }

    @Bean
    public Connection getConnection() throws SQLException {
        Connection connection = DButil.getDataSource().getConnection();
        return connection;
    }

/*
    public void run() throws SQLException {
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
*/
//    @Bean
/*
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(dataSource())
                .locations("classpath:migration")
                .load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }
*/
}
