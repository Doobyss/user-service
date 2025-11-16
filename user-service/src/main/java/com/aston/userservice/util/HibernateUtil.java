package com.aston.userservice.util;

import com.aston.userservice.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory buildSessionFactory(String configFile) {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration();
                cfg.configure(configFile);

                cfg.addAnnotatedClass(User.class);

                sessionFactory = cfg.buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка создания SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            return buildSessionFactory("hibernate.cfg.xml");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}




