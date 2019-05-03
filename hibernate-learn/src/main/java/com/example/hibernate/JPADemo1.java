package com.example.hibernate;

import com.example.entity2.Event;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class JPADemo1 extends JPATestBase {

    @Override
    String getPersistenceUnitName() {
        return "demo1";
    }

    /**
     * 使用 JPA 的 API
     */
    public void test01() {
        // create a couple of events...
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist( new Event( "Our very first event!", new Date() ) );
        entityManager.persist( new Event( "A follow up event", new Date() ) );
        entityManager.getTransaction().commit();
        entityManager.close();

        // now lets pull events from the database and list them
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Event> result = entityManager.createQuery( "from Event", Event.class ).getResultList();

        for ( Event event : result ) {
            System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 使用 hibernate 的 API
     */
    public void test02() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Event( "Our very first event!", new Date() ) );
        session.save( new Event( "A follow up event", new Date() ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Event" ).list();

        for ( Event event : (List<Event>) result ) {
            System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
        }

        session.getTransaction().commit();
        session.close();
    }

}
