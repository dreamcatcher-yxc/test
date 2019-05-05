package com.example.hibernate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JPADemo3 extends JPATestBase {

    @Data
    @Entity(name = "Person")
    public static class Person {

        @Id
        private Long id;

        private String name;

        @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Book> books = new ArrayList<>();
    }

    @Data
    @Entity(name = "Book")
    public static class Book {

        @Id
        private Long id;

        private String title;

        @NaturalId
        private String isbn;

        @ManyToOne
        @JoinColumn(name = "author_id",
                foreignKey = @ForeignKey(name = "PERSON_ID_FK")
        )
        private Person author;
    }

    @Override
    String getPersistenceUnitName() {
        return "demo3";
    }


    public void test01() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("活着");
        book.setIsbn("111");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("傲慢与偏见");
        book2.setIsbn("222");

        person.getBooks().add(book);
        person.getBooks().add(book2);

        entityManager.persist(person);
        transaction.commit();
    }

    public void test02() {
//        test01();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Person> criteria = builder.createQuery( Person.class );
        Root<Person> personRoot = criteria.from( Person.class );
        Root<Book> bookRoot = criteria.from( Book.class );
        criteria.select( personRoot );
        criteria.where( builder.equal( bookRoot.get( "title"), "活着" ) );

        List<Person> persons = entityManager.createQuery( criteria ).getResultList();
        log.info(persons.toString());
    }
}
