package com.example.demo.jpa;

import com.example.demo.entity.User;
import com.example.demo.repository.SpecialRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
// 使用真实数据库测试
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"com.example.demo.repository"})
public class Test02 {

    @Autowired
    private SpecialRepository<User> userSpecialRepository;

    @Test
    public void test01() {
        List<User> users = userSpecialRepository.runQuerySql("select * from T_USER where username like ?", "%username%");
        users.stream().forEach(user -> {
            System.out.println(user);
        });

    }
}
