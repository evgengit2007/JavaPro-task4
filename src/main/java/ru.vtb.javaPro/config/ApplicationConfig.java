package ru.vtb.javaPro.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.vtb.javaPro.connect.DButil;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.service.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Component
public class ApplicationConfig {
/*
    @Bean
    public Connection getConnection() throws SQLException {
//        Connection connection = DButil.getDataSource().getConnection();
        return new Connection();
    }
*/
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public ApplicationConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
//            @Value("${spring.datasource.driverClassName}") String driverClassName)
            @Value("${driver.class.name}") String driverClassName)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        System.out.println(driverClassName);
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
/*
        hikariConfig.setDriverClassName(driverClassName.getClass().getName());
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
*/
        hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/javaPro");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("postgres");
        hikariConfig.setMinimumIdle(100);
        hikariConfig.setMaximumPoolSize(1000000000);
        hikariConfig.setAutoCommit(true);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public UserDao userDao(DataSource dataSource) throws SQLException {
        return new UserDao(dataSource);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

}
