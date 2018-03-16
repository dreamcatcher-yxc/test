package com.example.demo.service.impl;

import com.example.demo.entity.QUser;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    //JPA查询工厂
    private final JPAQueryFactory queryFactory;

    public UserServiceImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<User> findAll() {
        QUser user = QUser.user;
        List<User> users = queryFactory
                .selectFrom(user)
                .orderBy(user.id.desc())
                .fetch();
        return users;
    }
}
