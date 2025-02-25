package ru.vtb.javaPro.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.service.UserService;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Component
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public ApplicationConfig(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password,
            @Value("${driver.class.name}") String driverClassName)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName.getClass().getName());
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMinimumIdle(100);
        hikariConfig.setMaximumPoolSize(1000000000);
        hikariConfig.setAutoCommit(true);
        return new HikariDataSource(hikariConfig);
    }

    public UserDao userDao(DataSource dataSource) throws SQLException {
        return new UserDao(dataSource);
    }

    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

}
