package com.aston.userservice.service;

import com.aston.userservice.dao.UserDao;
import com.aston.userservice.entity.User;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void createUser(String name, String email, int age) {
        userDao.create(new User(name, email, age));
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    public void updateUser(Long id, String name, String email, int age) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
