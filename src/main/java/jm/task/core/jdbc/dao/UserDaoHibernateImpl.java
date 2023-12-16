package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactoryDB = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactoryDB.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeMutationQuery("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                                                 "id INT NOT NULL AUTO_INCREMENT, " +
                                                 "name VARCHAR(45) NULL, " +
                                                 "lastname VARCHAR(64) NULL, " +
                                                 "age INT(3) NULL, " +
                                                 "PRIMARY KEY (id), " +
                                                 "UNIQUE KEY id_UNIQUE (id ASC) " +
                                                 "VISIBLE)"
            ).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactoryDB.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeMutationQuery("DROP TABLE IF EXISTS " + TABLE_NAME)
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactoryDB.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactoryDB.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery(
                    "DELETE FROM User WHERE id = :id"
            ).setParameter("id", id)
             .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return sessionFactoryDB.openSession().createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactoryDB.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User")
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
