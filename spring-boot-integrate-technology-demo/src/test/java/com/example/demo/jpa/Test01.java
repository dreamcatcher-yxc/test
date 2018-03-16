package com.example.demo.jpa;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
// 使用真实数据库测试
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class Test01 {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    /**
     * 初始化测试数据
     */
    @Test
    public void init() {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("username-" + i);
            user.setPassword("password-" + i);
            user.setPhone("phone-" + i);
            user.setEmail("email-" + i);
            user.setGender(i % 2 == 0 ? "m" : "f");

            Address address = new Address();
            address.setCountry("country-" + i);
            address.setProvince("province-" + i);
            address.setCity("city-" + i);
            user.setAddress(address);

            users.add(user);
        }

        repository.saveAll(users);
    }

    /**
     * 保存测试
     * @throws Exception
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("yangxiuchu");
        user.setPassword("xxx");
        user.setGender("male");
        user.setEmail("1905719625@qq.com");
        user.setPhone("18487304527");

        Address address = new Address();
        address.setCountry("china");
        address.setProvince("yunnan");
        address.setCity("kunming");
        user.setAddress(address);

        repository.save(user);
    }

    @Test
    public void testFindById() {
        Optional<User> userOpt = repository.findById(1L);
        userOpt.ifPresent(user -> {
            assertEquals(user.getUsername(), "yangxiuchu");
        });
    }

    @Test
    public void testFindByUsernameAndPassword() {
        Optional<User> userOpt = repository.findByUsernameAndPassword("yangxiuchu", "aaa");
        if(userOpt.isPresent()) {
            System.out.println("exists!!!");
        } else {
            System.out.println("not exists!!!");
        }
        userOpt = repository.findByUsernameAndPasswordAllIgnoreCase("YANGXIUCHU", "xxx");
        if(userOpt.isPresent()) {
            System.out.println("exists!!!");
        } else {
            System.out.println("not exists!!!");
        }
    }

    @Test
    public void testFindByAddressCountry() {
//        Optional<User> userOpt = repository.findByAddressCountry("china");
        Optional<User> userOpt = repository.findByAddress_Country("china");
        userOpt.ifPresent(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testFindByUsernameOrEmail() {
        Pageable pageInfo = PageRequest.of(0, 10);
        Page<User> page = repository.findByUsernameOrEmail("yangxiuchu", null, pageInfo);
        System.out.println("total pages is " + page.getTotalPages());
    }

    @Test
    public void testFindByUsernameOrGender() {
        Pageable pi = PageRequest.of(0, 10);
        Slice<User> slice = repository.findByUsernameOrGender("yangxiuchu", null, pi);
        slice.forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testFindByUsername() {
        Sort sort = Sort.by(Sort.Order.asc("username"));
        // 此种查询存在 n + 1 查询问题
        List<User> users = repository.findByUsernameIsLike("%%", sort);
        users.forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testComplexQuery01() {
        Optional<User> userOpt = repository.findFirstByUsernameIsLike("%username%");
        userOpt.ifPresent(user -> {
            System.out.println(user);
        });

        userOpt = repository.findTopByUsernameIsLikeOrderByEmailDesc("%username%");
        userOpt.ifPresent(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testComplexQuery02() {
        Pageable pi = PageRequest.of(3, 3);
        Page<User> page = repository.findFirst3ByUsernameIsLike("%username%", pi);
        System.out.println(page.getContent().size());
    }

    @Test
    public void testComplexQuery03() {
        Pageable pi = PageRequest.of(0, 5);
        Stream<User> users = repository.findByUsernameIsLike("%username%", pi);
        users.forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testGetId() {
        User user = repository.getOne(1L);
        System.out.println(user);
    }
}
