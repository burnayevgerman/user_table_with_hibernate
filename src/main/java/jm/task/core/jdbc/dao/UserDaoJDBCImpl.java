package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String TABLE_NAME = "users";
    private Connection connectionDB = Util.getConnectionDB();

    @Override
    public void createUsersTable() {
        try (Statement statement = connectionDB.createStatement()) {
            statement.execute(  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                                    "id INT NOT NULL AUTO_INCREMENT, " +
                                    "name VARCHAR(45) NULL, " +
                                    "lastname VARCHAR(64) NULL, " +
                                    "age INT(3) NULL, " +
                                    "PRIMARY KEY (id), " +
                                    "UNIQUE KEY id_UNIQUE (id ASC) " +
                                    "VISIBLE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connectionDB.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connectionDB.prepareStatement(
                "INSERT INTO " + TABLE_NAME + " (name, lastname, age) VALUES (?, ?, ?)"
        )) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connectionDB.prepareStatement(
                "DELETE FROM " + TABLE_NAME + " WHERE id=?"
        )) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Statement statement = connectionDB.createStatement()) {
            ResultSet resultData = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            List<User> userList = new ArrayList<>();

            while (resultData.next()) {
                userList.add(new User(
                    resultData.getInt("id"),
                    resultData.getString("name"),
                    resultData.getString("lastname"),
                    resultData.getByte("age")
                ));
            }

            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connectionDB.createStatement()) {
            statement.executeUpdate("DELETE FROM " + TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
