package com.example.demo.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class JPAConfig {

    @Bean
    public JPAQueryFactory JPAQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
