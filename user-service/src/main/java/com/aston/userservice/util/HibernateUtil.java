package com.aston.userservice.util;

import com.aston.userservice.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // Единственный экземпляр SessionFactory
    private static SessionFactory sessionFactory;

    /**
     * Создаёт SessionFactory на основе указанного конфигурационного файла.
     * Используется для тестов с Testcontainers и для продакшена.
     *
     * @param configFile путь к hibernate.cfg.xml
     * @return готовый SessionFactory
     */
    public static SessionFactory buildSessionFactory(String configFile) {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration();
                cfg.configure(configFile);  // подставит твой hibernate-test.cfg.xml

                // Явно добавляем entity
                cfg.addAnnotatedClass(User.class);

                sessionFactory = cfg.buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка создания SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    /**
     * Возвращает текущий SessionFactory. Если он ещё не создан, создаёт с дефолтным файлом.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            return buildSessionFactory("hibernate.cfg.xml"); // default для main-приложения
        }
        return sessionFactory;
    }

    /**
     * Закрывает фабрику сессий при завершении работы приложения.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}




