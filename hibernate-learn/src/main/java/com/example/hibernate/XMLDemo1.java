package com.example.hibernate;

import com.example.entity.Event2;
import org.hibernate.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class XMLDemo1 extends XMLConfigTestBase {

    private final Logger logger = LoggerFactory.getLogger(XMLDemo1.class.getName());

    public void test01() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Event2( "Our very first event!", new Date() ) );
        session.save( new Event2( "A follow up event", new Date() ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Event2" ).list();

        for ( Event2 event : (List<Event2>) result ) {
            System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
        }

        session.getTransaction().commit();
        session.close();
        logger.info("hello world!");
    }
}
