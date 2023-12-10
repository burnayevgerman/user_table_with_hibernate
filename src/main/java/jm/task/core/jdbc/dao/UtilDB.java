package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilDB implements AutoCloseable {
    private static final String HOST = "jdbc:mysql://localhost:3306/users_table_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public UtilDB() throws SQLException {
        connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
