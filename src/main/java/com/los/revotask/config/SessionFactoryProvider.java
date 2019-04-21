package com.los.revotask.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

public class SessionFactoryProvider {

    private final SessionFactory sessionFactory;

    private SessionFactoryProvider() {
        this.sessionFactory = createSessionFactory();
    }

    private SessionFactory createSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting(AvailableSettings.JDBC_TIME_ZONE, "UTC")
                .build();
        try {
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactoryProvider getInstance() {
        return InstanceKeeper.INSTANCE;
    }

    private static class InstanceKeeper {
        private static final SessionFactoryProvider INSTANCE = new SessionFactoryProvider();
    }
}
