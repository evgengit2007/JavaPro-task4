package ru.vtb.javaPro.connect;

import ru.vtb.javaPro.Main;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DButil {
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
