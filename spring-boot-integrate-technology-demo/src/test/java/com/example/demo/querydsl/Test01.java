package com.example.demo.querydsl;

import com.example.demo.entity.QUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(
        basePackages = "com.example.demo",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, RestController.class})})
public class Test01 {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JPAQueryFactory factory;

    /**
     * 查询所有的用户信息
     */
    @Test
    public void testFindAll() {
        QUser user = QUser.user;
        List<User> users = factory
                .selectFrom(user)
                .orderBy(user.id.desc())
                .fetch();
        users.forEach(t -> {
            System.out.println(t);
        });
    }

    /**
     * 根据条件查询用户信息
     */
    @Test
    public void testFindOne() {
        QUser qUser = QUser.user;
        Optional<User> user = userRepository.findOne(qUser.id.eq(1L));
        System.out.println(user);
        System.out.println("******************************************************************");

        Iterable<User> users = userRepository.findAll(qUser.username.like("%username%"));
        users.forEach(t -> {
            System.out.println(t);
        });
    }

    @Test
    public void testUpdate() {
        QUser qUser = QUser.user;
        long result = factory.update(qUser)
                .where(qUser.username.eq("yangxiuchu"))
                .set(qUser.username, "杨秀初")
                .execute();
        System.out.println(result > 0 ? "更新成功" : "更新失败");
    }

    @Test
    public void testDelete() {
        QUser qUser = QUser.user;
        long result = factory.delete(qUser)
                .where(qUser.username.eq("yangxiuchu"))
                .execute();
        System.out.println(result > 0 ? "删除成功" : "删除失败");
    }

}
