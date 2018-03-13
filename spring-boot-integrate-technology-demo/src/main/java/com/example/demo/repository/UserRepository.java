package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsernameAndPasswordAllIgnoreCase(String username, String password);

    /**
     * spring-data-jpa 首先会按照从右到左的将 addressCountry 分解为 country 和 country, user 中存在 address, address 中
     * 存在 country 属性, 则会应用这种匹配, 参看:
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
     * @param country
     * @return
     */
    Optional<User> findByAddressCountry(String country);

    Optional<User> findByAddress_Country(String country);

    /**
     * 根据 username 或者 email 分页查询用户信息
     * @param username
     * @param email
     * @param pageable
     * @return
     */
    Page<User> findByUsernameOrEmail(@Nullable String username, @Nullable String email, Pageable pageable);

    /**
     * 根据 username 或者 gender 分页查询用户信息, 返回一个查询结果集的迭代器。
     * @param username
     * @param gender
     * @param pageable
     * @return
     */
    Slice<User> findByUsernameOrGender(@Nullable String username, @Nullable String gender, Pageable pageable);

    /**
     * 根据用户名查询用户信息, 可以传递查询结果的排序条件
     * @param username
     * @param sort
     * @return
     */
    List<User> findByUsernameIsLike(String username, Sort sort);
}
