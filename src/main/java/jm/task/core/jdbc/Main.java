package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
        us.createUsersTable();

        List.of(
                new User("German", "Burnaev", (byte) 20),
                new User("Alex", "Friman", (byte) 31),
                new User("John", "Rokfeller", (byte) 95),
                new User("Ilon", "Mask", (byte) 35)
        ).stream().forEach(x -> {
            us.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.printf("User с именем - %s %s добавлен в базу данных\n", x.getName(), x.getLastName());
        });

        System.out.println(us.getAllUsers());
        us.cleanUsersTable();
        System.out.println(us.getAllUsers());
        us.dropUsersTable();
    }
}
