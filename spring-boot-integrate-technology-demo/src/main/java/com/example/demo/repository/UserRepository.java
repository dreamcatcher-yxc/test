package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends GenericRepository<User, Long>{


    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名和密码查询用户信息, 忽略大小写
     * @param username
     * @param password
     * @return
     */
    Optional<User> findByUsernameAndPasswordAllIgnoreCase(String username, String password);

    /**
     * spring-data-jpa 首先会按照从右到左的将 addressCountry 分解为 country 和 country, user 中存在 address, address 中
     * 存在 country 属性, 则会应用这种匹配, 参看:
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
     * @param country
     * @return
     */
    Optional<User> findByAddressCountry(String country);

    /**
     * 和 Optional<User> findByAddressCountry(String country) 函数代表的意义相同
     * @param country
     * @return
     */
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

    /**
     * 根据用户名模糊查询所有用户信息, 取第一个结果
     * @param username
     * @return
     */
    Optional<User> findFirstByUsernameIsLike(String username);

    /**
     * 根据用户名模糊查询所有用户信息, 按照邮箱号倒序排列, 取第一个值
     * @param username
     * @return
     */
    Optional<User> findTopByUsernameIsLikeOrderByEmailDesc(String username);

    /**
     * 根据用户名模糊分页查询所有用用户信息, 去除查询结果的前 5 条结果.
     * @param username
     * @param pageable
     * @return
     */
    Page<User> findFirst5ByUsernameIsLike(String username, Pageable pageable);

    /**
     * 根据用户名模糊分页查询所有用用户信息, 去除查询结果的前 3 条结果.
     * @param username
     * @param pageable
     * @return
     */
    Page<User> findFirst3ByUsernameIsLike(String username, Pageable pageable);

    /**
     * 根据用户名模糊分页查询所有用用户信息, 去除查询结果的前 2 条结果.
     * @param username
     * @param pageable
     * @return
     */
    List<User> findFirst2ByUsernameIsLike(String username, Pageable pageable);

    /**
     * 根据用户名模糊分页查询用户信息, 返回流对象(必须加上 read-only 的事务支持)。
     * @param username
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    Stream<User> findByUsernameIsLike(String username, Pageable pageable);
}
