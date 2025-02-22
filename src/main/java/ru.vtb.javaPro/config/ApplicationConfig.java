package ru.vtb.javaPro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.vtb.javaPro.connect.DButil;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan("ru.vtb.javaPro")
public class ApplicationConfig {
    @Bean
    public DButil dButil() {
        return new DButil();
    }

    @Bean
    public UserDao userDao() throws SQLException {
        return new UserDao(getConnection());
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

    @Bean
    public Connection getConnection() throws SQLException {
        Connection connection = DButil.getDataSource().getConnection();
        return connection;
    }

}
