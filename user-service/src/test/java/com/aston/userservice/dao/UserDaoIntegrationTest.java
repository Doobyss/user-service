package com.aston.userservice.dao;

import com.aston.userservice.entity.User;
import com.aston.userservice.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private static SessionFactory sessionFactory;
    private static UserDao userDao;

    @BeforeAll
    static void setup() {
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg.xml");
        userDao = new UserDao(sessionFactory);
    }

    @Test
    @Order(1)
    void testCreate() {
        User user = new User("John Doe", "john@test.com", 25);
        userDao.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void testGetAll() {
        List<User> users = userDao.getAll();
        Assertions.assertTrue(users.size() > 0);
    }

    @Test
    @Order(3)
    void testGetById() {
        User user = userDao.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    @Order(4)
    void testUpdate() {
        User user = userDao.getById(1L);
        user.setName("Updated");
        userDao.update(user);

        User updated = userDao.getById(1L);
        Assertions.assertEquals("Updated", updated.getName());
    }

    @Test
    @Order(5)
    void testDelete() {
        userDao.delete(1L);
        Assertions.assertNull(userDao.getById(1L));
    }
}
