package com.aston.userservice.service;

import com.aston.userservice.dao.UserDao;
import com.aston.userservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);

        userService = new UserService();

        try {
            var field = UserService.class.getDeclaredField("userDao");
            field.setAccessible(true);
            field.set(userService, userDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateUser() {
        userService.createUser("John", "john@test.com", 22);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).save(captor.capture());

        User saved = captor.getValue();
        assertEquals("John", saved.getName());
        assertEquals("john@test.com", saved.getEmail());
        assertEquals(22, saved.getAge());
    }

    @Test
    void testGetAllUsers() {
        List<User> mockList = List.of(
                new User("A", "a@mail", 20),
                new User("B", "b@mail", 25)
        );

        when(userDao.getAll()).thenReturn(mockList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDao, times(1)).getAll();
    }

    @Test
    void testGetUserById() {
        User user = new User("Test", "t@mail", 30);
        when(userDao.getById(10L)).thenReturn(user);

        User result = userService.getUserById(10L);

        assertNotNull(result);
        assertEquals("Test", result.getName());
        verify(userDao).getById(10L);
    }

    @Test
    void testUpdateUser_UserExists() {
        User existing = new User("Old", "old@mail", 50);
        when(userDao.getById(1L)).thenReturn(existing);

        userService.updateUser(1L, "New", "new@mail", 18);

        assertEquals("New", existing.getName());
        assertEquals("new@mail", existing.getEmail());
        assertEquals(18, existing.getAge());

        verify(userDao).update(existing);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userDao.getById(99L)).thenReturn(null);

        userService.updateUser(99L, "X", "x@mail", 99);

        verify(userDao, never()).update(any());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(5L);

        verify(userDao).delete(5L);
    }
}
