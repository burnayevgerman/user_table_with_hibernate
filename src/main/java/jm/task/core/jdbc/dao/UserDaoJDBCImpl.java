package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDaoJDBCImpl implements UserDao {
    private static final String TABLE_NAME = "users";

    @Override
    public void createUsersTable() {
        execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NULL, \n" +
                "lastname VARCHAR(64) NULL,\n" +
                "age INT(3) NULL,\n" +
                "PRIMARY KEY (id),\n" +
                "UNIQUE KEY id_UNIQUE (id ASC) \n" +
                "VISIBLE)");
    }

    @Override
    public void dropUsersTable() {
        execute("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        execute("INSERT INTO %s (name, lastname, age) VALUES ('%s', '%s', '%d')",
                TABLE_NAME, name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        execute("DELETE FROM %s WHERE id=%d", TABLE_NAME, id);
    }

    @Override
    public List<User> getAllUsers() {
        try (UtilDB utilDB = new UtilDB();
                Statement statement = utilDB.getStatement()) {
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
        execute("DELETE FROM " + TABLE_NAME);
    }

    private void execute(String statementStr, Object ... values) {
        try (UtilDB utilDB = new UtilDB();
                Statement statement = utilDB.getStatement()) {
            statement.executeUpdate(String.format(statementStr, values));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
