package com.aston.userservice;

import com.aston.userservice.entity.User;
import com.aston.userservice.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
                System.out.println("==== USER SERVICE ====");
                System.out.println("1. Создать пользователя");
                System.out.println("2. Показать всех");
                System.out.println("3. Найти по ID");
                System.out.println("4. Обновить пользователя");
                System.out.println("5. Удалить пользователя");
                System.out.println("0. Выход");

            System.out.print("Выберите действие: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createUser();
                case 2 -> listUsers();
                case 3 -> getUserById();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    System.out.println("Выход...");
                    System.exit(0);
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void createUser() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        userService.createUser(name, email, age);
        System.out.println("Пользователь создан!");
    }

    private static void listUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }

    private static void getUserById() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = userService.getUserById(id);
        System.out.println(user != null ? user : "Не найден.");
    }

    private static void updateUser() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Новый email: ");
        String email = scanner.nextLine();
        System.out.print("Новый возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        userService.updateUser(id, name, email, age);
    }

    private static void deleteUser() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        userService.deleteUser(id);
        System.out.println("Пользователь удалён!");
    }
}
