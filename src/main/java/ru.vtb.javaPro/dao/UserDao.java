package ru.vtb.javaPro.dao;

import ru.vtb.javaPro.dto.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public void insertData(Users users) {
        try (Statement insertStatement = connection.createStatement()) {
            if (users.getId() == null) {
                ResultSet readResultSet = insertStatement.executeQuery("SELECT * FROM users WHERE username = '" + users.getUsername() + "'");
                if (!readResultSet.next()) {
                    insertStatement.executeUpdate("INSERT INTO users (username) VALUES ('" + users.getUsername() + "')", Statement.RETURN_GENERATED_KEYS);
                    ResultSet genResSet = insertStatement.getGeneratedKeys();
                    genResSet.next();
                    users.setId((long) genResSet.getInt("id"));
                } else {
                    users.setId((long) readResultSet.getInt("id"));
                }
                readResultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Users getUserById(Long id) {
        try (Statement getStatement = connection.createStatement()) {
            ResultSet resultSet = getStatement.executeQuery("SELECT * FROM users WHERE id = " + id);
            if (!resultSet.next()) {
                return null;
            }
            Users users = new Users();
            users.setId(resultSet.getLong("id"));
            users.setUsername(resultSet.getString("username"));
            resultSet.close();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Users getUserByUsername(String username) {
        try (Statement getStatement = connection.createStatement()) {
            ResultSet resultSet = getStatement.executeQuery("SELECT * FROM users WHERE username = " + username);
            if (!resultSet.next()) {
                return null;
            }
            Users users = new Users();
            users.setId(resultSet.getLong("id"));
            users.setUsername(resultSet.getString("username"));
            resultSet.close();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Users updateData(Users users) {
        if (users == null) return null;
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE users SET username = ? WHERE id = ?;");

            updateStatement.setString(1, users.getUsername());

            updateStatement.setLong(2, users.getId());
            updateStatement.executeUpdate();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean deleteData(Users users) {
        if (users == null) return null;
        try {
            if (users.getId() == null) {
                PreparedStatement deleteStatement = connection
                        .prepareStatement("DELETE FROM users WHERE username = ?;");

                deleteStatement.setString(1, users.getUsername());
                deleteStatement.executeUpdate();
                deleteStatement.close();
                if (getUserByUsername(users.getUsername()) != null) return false;
            } else {
                PreparedStatement deleteStatement = connection
                        .prepareStatement("DELETE FROM users WHERE id = ?;");

                deleteStatement.setLong(1, users.getId());
                deleteStatement.executeUpdate();
                deleteStatement.close();
                if (getUserById(users.getId()) != null) return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Users> readData() {
        try {
            List<Users> usersList = new ArrayList<>();
            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = readStatement.executeQuery();
            while (resultSet.next()) {
                Users users = new Users();
                users.setId(resultSet.getLong("id"));
                users.setUsername(resultSet.getString("username"));
                usersList.add(users);
            }
            return usersList;
        } catch (SQLException e) {
            return null;
        }
    }

}
