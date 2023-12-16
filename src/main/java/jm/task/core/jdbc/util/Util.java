package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST = "jdbc:mysql://localhost:3306/users_table_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final boolean SHOW_SQL = true;
    private static final String HBM2DDL_AUTO = "update";

    public static Connection getConnectionDB() {
        try {
            return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", DRIVER);
        configuration.setProperty("hibernate.connection.url", HOST);
        configuration.setProperty("hibernate.connection.username", USERNAME);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("hibernate.show_sql", String.valueOf(SHOW_SQL));
        configuration.setProperty("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        Configuration configuration = getConfiguration();
        builder.applySettings(configuration.getProperties());

        try {
            return configuration.buildSessionFactory(builder.build());
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }
}
