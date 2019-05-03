package com.example.hibernate;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class JPATestBase extends TestCase {

    protected EntityManagerFactory entityManagerFactory;

    protected SessionFactory sessionFactory;

    abstract String getPersistenceUnitName();

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 可以使用一下的方法将 JPA 的 entityManager 转换为 Hibernate 的 sessionFactory,
        // 其实 JPA 的 EntityManagerFactory 和 Hibernate 的 SessionFactory 是一个东西
        // Session session = entityManager.unwrap( Session.class );
        // SessionImplementor sessionImplementor = entityManager.unwrap( SessionImplementor.class );
        sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
    }

    public void tearDown() throws Exception {
        entityManagerFactory.close();
    }

}
