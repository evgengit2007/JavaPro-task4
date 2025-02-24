package ru.vtb.javaPro.connect;

import com.zaxxer.hikari.HikariDataSource;
import ru.vtb.javaPro.Main;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class DButil {
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER_CLASS = "driver.class.name";
    private static Properties properties = null;
    private static HikariDataSource datasource;

    public DButil() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));

            datasource = new HikariDataSource();
            datasource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));
            datasource.setJdbcUrl(properties.getProperty(DB_URL));
            datasource.setUsername(properties.getProperty(DB_USERNAME));
            datasource.setPassword(properties.getProperty(DB_PASSWORD));
            datasource.setMinimumIdle(100);
            datasource.setMaximumPoolSize(1000000000);
            datasource.setAutoCommit(true);
            datasource.setLoginTimeout(3);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection DButil(Connection connection) throws SQLException {
        connection = datasource.getConnection();
        return connection;
    }

    /*
        static {
            try {
                properties = new Properties();
                properties.load(new FileInputStream("src/main/resources/application.properties"));

                datasource = new HikariDataSource();
                datasource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));
                datasource.setJdbcUrl(properties.getProperty(DB_URL));
                datasource.setUsername(properties.getProperty(DB_USERNAME));
                datasource.setPassword(properties.getProperty(DB_PASSWORD));
                datasource.setMinimumIdle(100);
                datasource.setMaximumPoolSize(1000000000);
                datasource.setAutoCommit(true);
                datasource.setLoginTimeout(3);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    */
    public DataSource getDataSource() {
        return datasource;
    }

    public static void dbMigration(Connection connection) {
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("migration/init.sql"));
        try {
            Statement statement = connection.createStatement();
            while (scanner.hasNextLine()) {
                statement.execute(scanner.nextLine());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
