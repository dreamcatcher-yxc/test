package com.example.hibernate;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class JPADemo2 extends JPATestBase {

    @Override
    String getPersistenceUnitName() {
        return "demo2";
    }

    @Data
    @Entity(name = "Person")
    public static class Person {

        @Id
        private Long id;

        private String name;

        @OneToMany(mappedBy = "author")
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
        private Person author;
    }


    public void test01() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        entityManager.persist(person);
    }

    public void test02() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");

        Book book = new Book();
        person.getBooks().add(book);
        book.setAuthor(person);

        entityManager.persist(person);
    }
}
